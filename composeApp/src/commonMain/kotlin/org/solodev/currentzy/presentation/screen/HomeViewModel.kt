package org.solodev.currentzy.presentation.screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import org.solodev.currentzy.domain.CurrentzyApiService
import org.solodev.currentzy.domain.PreferenceRepository
import org.solodev.currentzy.domain.model.Currency
import org.solodev.currentzy.domain.model.RateStatus
import org.solodev.currentzy.domain.model.RequestState

class HomeViewModel(
    private val preferences: PreferenceRepository,
    private val apiService: CurrentzyApiService
) : ScreenModel {
    private var _rateStatus: MutableState<RateStatus> = mutableStateOf(RateStatus.Idle)
    val rateStatus: State<RateStatus> = _rateStatus

    private var _sourceCurrency: MutableState<RequestState<Currency>> = mutableStateOf(RequestState.Idle)
    val sourceCurrency: State<RequestState<Currency>> = _sourceCurrency

    private var _targetCurrency: MutableState<RequestState<Currency>> = mutableStateOf(RequestState.Idle)
    val targetCurrency: State<RequestState<Currency>> = _targetCurrency


    init {
        screenModelScope.launch {
            fetchNewRates()
        }
    }

    fun sendEvent(event: HomeUiEvent) {
        when (event) {
            is HomeUiEvent.RefreshRates -> {
                screenModelScope.launch {
                    fetchNewRates()
                }
            }
        }
    }

    private suspend fun fetchNewRates() {
        try {
            apiService.getLatestExchangeRates()
            getRateStatus()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private suspend fun getRateStatus() {
        _rateStatus.value = if (preferences.isDataFresh(
                currentTimeStamp = Clock.System.now().toEpochMilliseconds()
            )
        ) RateStatus.Fresh else RateStatus.Stale
    }
}