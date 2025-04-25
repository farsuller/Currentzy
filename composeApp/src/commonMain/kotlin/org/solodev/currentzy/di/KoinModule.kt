package org.solodev.currentzy.di

import com.russhwolf.settings.Settings
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.headers
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.solodev.currentzy.data.local.MongoImpl
import org.solodev.currentzy.data.local.PreferencesImpl
import org.solodev.currentzy.data.remote.api.CurrentzyApiServiceImpl
import org.solodev.currentzy.data.remote.api.CurrentzyApiServiceImpl.Companion.API_KEY
import org.solodev.currentzy.domain.CurrentzyApiService
import org.solodev.currentzy.domain.MongoRepository
import org.solodev.currentzy.domain.PreferenceRepository
import org.solodev.currentzy.presentation.screen.HomeViewModel

val appModule = module {
    single {
        HttpClient(CIO) {
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
    }
    single { Settings() }
    single<PreferenceRepository> { PreferencesImpl(settings = get()) }
    single<CurrentzyApiService> { CurrentzyApiServiceImpl(preference = get(), httpClient = get()) }
    single<MongoRepository> { MongoImpl() }
    factory {
        HomeViewModel(
            preferences = get(),
            apiService = get(),
            mongoDb = get()
        )
    }
}

fun initializeKoin() {
    startKoin {
        modules(appModule)
    }
}