package org.solodev.currentzy.data.remote.api

import org.solodev.currentzy.domain.CurrentzyApiService
import org.solodev.currentzy.domain.model.ApiResponse
import org.solodev.currentzy.domain.model.Currency
import org.solodev.currentzy.domain.model.RequestState
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.solodev.currentzy.domain.PreferenceRepository
import org.solodev.currentzy.domain.model.CurrencyCode
import org.solodev.currentzy.domain.model.toCurrency

class CurrentzyApiServiceImpl(
    private val preference: PreferenceRepository
) : CurrentzyApiService {

    companion object {
        const val ENDPOINT = "https://api.currencyapi.com/v3/latest"
        const val API_KEY = "cur_live_ZUIUCRQyFy3tc7WtxuF1XMN67fSPGAnk1Q90d53R"
    }

    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }

        install(HttpTimeout) {
            requestTimeoutMillis = 15000
            connectTimeoutMillis = 15000
            socketTimeoutMillis = 15000
        }

        install(DefaultRequest) {
            headers {
                append("apikey", API_KEY)
            }
        }
    }

    override suspend fun getLatestExchangeRates(): RequestState<List<Currency>> {
        return try {
            val response = httpClient.get(ENDPOINT)
            if (response.status.value == 200) {
                val apiResponse = Json.decodeFromString<ApiResponse>(response.body())

                val availableCurrencyCodes = apiResponse.data.keys
                    .filter {
                        CurrencyCode.entries
                            .map { code -> code.name }
                            .toSet()
                            .contains(it)
                    }

                val availableCurrencies = apiResponse.data.values
                    .filter { currency ->
                        availableCurrencyCodes.contains(currency.code)
                    }
                    .map { it.toCurrency() }

                //Persist a timestamp
                val lastUpdated = apiResponse.meta.lastUpdatedAt
                preference.saveLastUpdated(lastUpdated)

                RequestState.Success(data = availableCurrencies)
            } else {
                RequestState.Error(message = "HTTP Error Code: ${response.status}")
            }
        } catch (e: Exception) {
            RequestState.Error(message = e.message.toString())
        }
    }
}