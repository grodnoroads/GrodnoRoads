package com.egoriku.grodnoroads.guidance.di

import com.egoriku.grodnoroads.guidance.data.repository.*
import com.egoriku.grodnoroads.guidance.domain.repository.*
import com.egoriku.grodnoroads.guidance.domain.store.config.MapConfigStoreFactory
import com.egoriku.grodnoroads.guidance.domain.store.dialog.DialogStoreFactory
import com.egoriku.grodnoroads.guidance.domain.store.location.LocationStoreFactory
import com.egoriku.grodnoroads.guidance.domain.store.mapevents.MapEventsStoreFactory
import com.egoriku.grodnoroads.guidance.domain.store.quickactions.QuickActionsStoreFactory
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val guidanceModule = module {
    factoryOf(::UserCountRepositoryImpl) { bind<UserCountRepository>() }
    factoryOf(::StationaryCameraRepositoryImpl) { bind<StationaryCameraRepository>() }
    factoryOf(::MediumSpeedCameraRepositoryImpl) { bind<MediumSpeedCameraRepository>() }
    factoryOf(::MobileCameraRepositoryImpl) { bind<MobileCameraRepository>() }
    factoryOf(::ReportsRepositoryImpl) { bind<ReportsRepository>() }
    factoryOf(::CityAreasRepositoryImpl) { bind<CityAreasRepository>() }

    factory {
        MapEventsStoreFactory(
            storeFactory = get(),
            mobileCameraRepository = get(),
            stationaryCameraRepository = get(),
            mediumSpeedCameraRepository = get(),
            userCountRepository = get(),
            reportsRepository = get(),
            // analyticsTracker = get(),
            crashlyticsTracker = get()
        ).create()
    }

    factory {
        LocationStoreFactory(
            storeFactory = get(),
            locationService = get(),
            dataStore = get()
        ).create()
    }

    factory {
        MapConfigStoreFactory(
            storeFactory = get(),
            dataStore = get(),
            cityAreasRepository = get()
        ).create()
    }

    factory {
        DialogStoreFactory(
            storeFactory = get(),
            // analyticsTracker = get()
        ).create()
    }

    factory {
        QuickActionsStoreFactory(
            storeFactory = get(),
            dataStore = get()
        ).create()
    }
}