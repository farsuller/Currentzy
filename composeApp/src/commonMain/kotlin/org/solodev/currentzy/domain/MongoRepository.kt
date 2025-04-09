package org.solodev.currentzy.domain

import kotlinx.coroutines.flow.Flow
import org.solodev.currentzy.domain.model.Currency
import org.solodev.currentzy.domain.model.RequestState

interface MongoRepository {
    fun configureTheRealm()
    suspend fun insertCurrencyData(currency: Currency)
    fun readCurrencyData(): Flow<RequestState<List<Currency>>>
    suspend fun cleanUp()
}