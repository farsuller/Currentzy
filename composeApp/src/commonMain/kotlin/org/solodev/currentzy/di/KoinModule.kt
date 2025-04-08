package org.solodev.currentzy.di

import com.russhwolf.settings.Settings
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.solodev.currentzy.data.local.PreferencesImpl
import org.solodev.currentzy.data.remote.api.CurrentzyApiServiceImpl
import org.solodev.currentzy.domain.CurrentzyApiService
import org.solodev.currentzy.domain.PreferenceRepository

val appModule = module {
    single { Settings() }
    single<PreferenceRepository> { PreferencesImpl(settings = get()) }
    single<CurrentzyApiService> { CurrentzyApiServiceImpl(preference = get()) }
}

fun initializeKoin(){
    startKoin {
        modules(appModule)
    }
}