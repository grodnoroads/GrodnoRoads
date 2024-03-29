package com.egoriku.grodnoroads.map.domain.store.mapevents

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineExecutorFactory
import com.egoriku.grodnoroads.crashlytics.CrashlyticsTracker
import com.egoriku.grodnoroads.extensions.common.ResultOf.Failure
import com.egoriku.grodnoroads.extensions.common.ResultOf.Success
import com.egoriku.grodnoroads.extensions.logD
import com.egoriku.grodnoroads.extensions.reLaunch
import com.egoriku.grodnoroads.map.domain.model.MapEvent.Camera.*
import com.egoriku.grodnoroads.map.domain.model.MapEvent.Reports
import com.egoriku.grodnoroads.map.domain.repository.*
import com.egoriku.grodnoroads.map.domain.store.mapevents.MapEventsStore.*
import com.egoriku.grodnoroads.map.domain.store.mapevents.MapEventsStore.Message.*
import com.egoriku.grodnoroads.shared.appsettings.types.map.filtering.filteringMarkers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.minutes

internal class MapEventsStoreFactory(
    private val storeFactory: StoreFactory,
    private val mobileCameraRepository: MobileCameraRepository,
    private val mediumSpeedCameraRepository: MediumSpeedCameraRepository,
    private val stationaryCameraRepository: StationaryCameraRepository,
    private val userCountRepository: UserCountRepository,
    private val reportsRepository: ReportsRepository,
    private val crashlyticsTracker: CrashlyticsTracker,
    private val dataStore: DataStore<Preferences>
) {

    private val currentTime: Long
        get() = System.currentTimeMillis()

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): MapEventsStore =
        object : MapEventsStore, Store<Intent, State, Nothing> by storeFactory.create(
            initialState = State(),
            executorFactory = coroutineExecutorFactory(Dispatchers.Main) {
                var reports: Job? = null

                onAction<Unit> {
                    dataStore.data
                        .map { it.filteringMarkers.timeInMilliseconds }
                        .distinctUntilChanged()
                        .onEach { time ->
                            dispatch(OnUpdateFilterTime(time))

                            reports = reLaunch(reports) {
                                subscribeForReports(
                                    startAt = time,
                                    onLoaded = { list ->
                                        val filterTime = currentTime - time
                                        dispatch(OnNewReports(data = list.filter { it.timestamp >= filterTime }))
                                    }
                                )
                            }
                        }
                        .launchIn(this)

                    launch {
                        subscribeForStationaryCameras {
                            dispatch(OnStationary(data = it))
                        }
                    }
                    launch {
                        subscribeForMediumSpeedCameras {
                            dispatch(OnMediumSpeed(data = it))
                        }
                    }
                    launch {
                        subscribeForMobileCameras {
                            dispatch(OnMobileCamera(data = it))
                        }
                    }
                    launch {
                        subscribeForUserCount {
                            dispatch(OnUserCount(data = it))
                        }
                    }
                    launch {
                        while (true) {
                            delay(2.minutes)

                            val filterTime = currentTime - state.filterEventsTime
                            dispatch(OnNewReports(state.reports.filter { it.timestamp >= filterTime }))
                        }
                    }
                }
            },
            bootstrapper = SimpleBootstrapper(Unit),
            reducer = { message: Message ->
                when (message) {
                    is OnStationary -> copy(stationaryCameras = message.data)
                    is OnMediumSpeed -> copy(mediumSpeedCameras = message.data)
                    is OnNewReports -> copy(reports = message.data)
                    is OnMobileCamera -> copy(mobileCameras = message.data)
                    is OnUserCount -> copy(userCount = message.data)
                    is OnUpdateFilterTime -> copy(filterEventsTime = message.time)
                }
            }
        ) {}

    private suspend fun subscribeForMobileCameras(onLoaded: (List<MobileCamera>) -> Unit) {
        mobileCameraRepository.loadAsFlow().collect { result ->
            when (result) {
                is Success -> onLoaded(result.value)
                is Failure -> crashlyticsTracker.recordException(result.exception)
            }
        }
    }

    private suspend fun subscribeForMediumSpeedCameras(onLoaded: (List<MediumSpeedCamera>) -> Unit) {
        mediumSpeedCameraRepository.loadAsFlow().collect { result ->
            when (result) {
                is Success -> onLoaded(result.value)
                is Failure -> crashlyticsTracker.recordException(result.exception)
            }
        }
    }

    private suspend fun subscribeForReports(startAt: Long, onLoaded: (List<Reports>) -> Unit) {
        reportsRepository
            .loadAsFlow(startAt = startAt)
            .collect { result ->
                when (result) {
                    is Success -> onLoaded(result.value)
                    is Failure -> crashlyticsTracker.recordException(result.exception)
                }
            }
    }

    private suspend fun subscribeForStationaryCameras(onLoaded: (List<StationaryCamera>) -> Unit) {
        stationaryCameraRepository.loadAsFlow().collect { result ->
            when (result) {
                is Success -> onLoaded(result.value)
                is Failure -> crashlyticsTracker.recordException(result.exception).also {
                    logD("Error loading stationary: ${result.exception.message}")
                }
            }
        }
    }

    private suspend fun subscribeForUserCount(onLoaded: (Int) -> Unit) {
        userCountRepository.loadAsFlow().collect { result ->
            when (result) {
                is Success -> onLoaded(result.value)
                is Failure -> crashlyticsTracker.recordException(result.exception)
            }
        }
    }
}