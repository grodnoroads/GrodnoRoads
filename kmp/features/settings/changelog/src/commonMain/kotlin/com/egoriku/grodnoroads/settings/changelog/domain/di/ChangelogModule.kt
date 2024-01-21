package com.egoriku.grodnoroads.settings.changelog.domain.di

import com.egoriku.grodnoroads.settings.changelog.data.repository.ChangelogRepositoryImpl
import com.egoriku.grodnoroads.settings.changelog.domain.repository.ChangelogRepository
import com.egoriku.grodnoroads.settings.changelog.domain.store.ChangelogStoreFactory
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val changelogModule = module {
    factoryOf(::ChangelogRepositoryImpl) { bind<ChangelogRepository>() }

    factory {
        ChangelogStoreFactory(
            storeFactory = get(),
            changelogRepository = get(),
            crashlyticsTracker = get()
        ).create()
    }
}