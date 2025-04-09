package org.solodev.currentzy.domain

import kotlinx.coroutines.flow.Flow
import org.solodev.currentzy.domain.model.CurrencyCode

interface PreferenceRepository {
    suspend fun saveLastUpdated(lastUpdated: String)
    suspend fun isDataFresh(currentTimeStamp: Long): Boolean
    suspend fun saveSourceCurrencyCode(code:String)
    suspend fun saveTargetCurrencyCode(code:String)
    fun readSourceCurrencyCode(): Flow<CurrencyCode>
    fun readTargetCurrencyCode(): Flow<CurrencyCode>
}