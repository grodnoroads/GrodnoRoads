package com.egoriku.grodnoroads.settings.alerts.di

import com.egoriku.grodnoroads.settings.alerts.domain.store.AlertsStoreFactory
import org.koin.dsl.module

val alertsModule = module {
    factory {
        AlertsStoreFactory(
            storeFactory = get(),
            dataStore = get()
        ).create()
    }
}