package com.egoriku.grodnoroads.guidance.domain.store.quickactions

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineExecutorFactory
import com.egoriku.grodnoroads.datastore.edit
import com.egoriku.grodnoroads.guidance.domain.store.quickactions.QuickActionsStore.QuickActionsIntent
import com.egoriku.grodnoroads.guidance.domain.store.quickactions.QuickActionsStore.QuickActionsIntent.Update
import com.egoriku.grodnoroads.guidance.domain.store.quickactions.QuickActionsStore.QuickActionsMessage
import com.egoriku.grodnoroads.guidance.domain.store.quickactions.QuickActionsStore.QuickActionsMessage.NewSettings
import com.egoriku.grodnoroads.guidance.domain.store.quickactions.model.QuickActionsPref.AppTheme
import com.egoriku.grodnoroads.guidance.domain.store.quickactions.model.QuickActionsState
import com.egoriku.grodnoroads.shared.persistent.appearance.appTheme
import com.egoriku.grodnoroads.shared.persistent.appearance.updateAppTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

internal class QuickActionsStoreFactory(
    private val storeFactory: StoreFactory,
    private val dataStore: DataStore<Preferences>
) {

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): QuickActionsStore =
        object : QuickActionsStore,
            Store<QuickActionsIntent, QuickActionsState, Nothing> by storeFactory.create(
                initialState = QuickActionsState(),
                executorFactory = coroutineExecutorFactory(Dispatchers.Main) {
                    onAction<Unit> {
                        dataStore.data
                            .map { preferences ->
                                QuickActionsState(
                                    appTheme = AppTheme(current = preferences.appTheme),
                                )
                            }
                            .distinctUntilChanged()
                            .onEach { dispatch(NewSettings(it)) }
                            .launchIn(this)
                    }
                    onIntent<Update> {
                        when (val pref = it.preference) {
                            is AppTheme -> {
                                launch {
                                    dataStore.edit {
                                        updateAppTheme(pref.current.theme)
                                    }
                                }
                            }
                        }
                    }
                },
                bootstrapper = SimpleBootstrapper(Unit),
                reducer = { message: QuickActionsMessage ->
                    when (message) {
                        is NewSettings -> message.appearanceState
                    }
                }
            ) {}
}
