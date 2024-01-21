package com.egoriku.grodnoroads.settings.faq.di

import com.egoriku.grodnoroads.settings.faq.data.repository.FaqRepositoryImpl
import com.egoriku.grodnoroads.settings.faq.domain.repository.FaqRepository
import com.egoriku.grodnoroads.settings.faq.domain.store.FaqStoreFactory
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val faqModule = module {
    factoryOf(::FaqRepositoryImpl) { bind<FaqRepository>() }

    factory {
        FaqStoreFactory(
            storeFactory = get(),
            faqRepository = get(),
            crashlyticsTracker = get()
        ).create()
    }
}