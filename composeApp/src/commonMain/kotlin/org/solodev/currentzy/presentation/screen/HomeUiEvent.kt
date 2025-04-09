package org.solodev.currentzy.presentation.screen

sealed class HomeUiEvent{
    data object RefreshRates: HomeUiEvent()
    data object SwitchCurrencies: HomeUiEvent()
}