package org.solodev.currentzy.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import org.solodev.currentzy.presentation.component.HomeHeader
import surfaceColor

class HomeScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = koinScreenModel<HomeViewModel>()
        val rateStatus by viewModel.rateStatus
        val sourceCurrency by viewModel.sourceCurrency
        val targetCurrency by viewModel.targetCurrency

        var amount by rememberSaveable { mutableStateOf(0.0) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(surfaceColor)
        ) {
            HomeHeader(
                status = rateStatus,
                source = sourceCurrency,
                target = targetCurrency,
                amount = amount,
                onAmountChange = { amount = it },
                onSwitchClick = {},
                onRatesRefresh = {
                    viewModel.sendEvent(HomeUiEvent.RefreshRates)
                }
            )

        }
    }
}