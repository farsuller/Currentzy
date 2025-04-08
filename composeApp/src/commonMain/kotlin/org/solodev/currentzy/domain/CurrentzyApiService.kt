package org.solodev.currentzy.domain

import org.solodev.currentzy.domain.model.Currency
import org.solodev.currentzy.domain.model.RequestState

interface CurrentzyApiService {
    suspend fun getLatestExchangeRates() : RequestState<List<Currency>>
}