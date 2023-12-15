package com.egoriku.grodnoroads.root.di

import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.egoriku.grodnoroads.mainflow.di.mainFlowModule
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun appModule() = listOf(
    platformDataStoreModule,
    appScopeModule,
    mainFlowModule
)

val appScopeModule = module {
    singleOf(::DefaultStoreFactory) { bind<StoreFactory>() }
    single { Firebase.firestore }
}

internal expect val platformDataStoreModule: Module