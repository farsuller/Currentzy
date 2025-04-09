package org.solodev.currentzy.di

import com.russhwolf.settings.Settings
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.solodev.currentzy.data.local.MongoImpl
import org.solodev.currentzy.data.local.PreferencesImpl
import org.solodev.currentzy.data.remote.api.CurrentzyApiServiceImpl
import org.solodev.currentzy.domain.CurrentzyApiService
import org.solodev.currentzy.domain.MongoRepository
import org.solodev.currentzy.domain.PreferenceRepository
import org.solodev.currentzy.presentation.screen.HomeViewModel

val appModule = module {
    single { Settings() }
    single<PreferenceRepository> { PreferencesImpl(settings = get()) }
    single<CurrentzyApiService> { CurrentzyApiServiceImpl(preference = get()) }
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