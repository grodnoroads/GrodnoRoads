package com.egoriku.grodnoroads.guidance.domain.store.location

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineExecutorFactory
import com.egoriku.grodnoroads.coroutines.reLaunch
import com.egoriku.grodnoroads.guidance.domain.model.LastLocation
import com.egoriku.grodnoroads.guidance.domain.store.location.LocationStore.*
import com.egoriku.grodnoroads.guidance.domain.store.location.LocationStore.Intent.*
import com.egoriku.grodnoroads.shared.geolocation.LocationService
import com.egoriku.grodnoroads.shared.persistent.map.location.defaultCity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

internal class LocationStoreFactory(
    private val storeFactory: StoreFactory,
    private val locationService: LocationService,
    private val dataStore: DataStore<Preferences>
) {

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): LocationStore =
        object : LocationStore, Store<Intent, State, Label> by storeFactory.create(
            initialState = State(),
            executorFactory = coroutineExecutorFactory(Dispatchers.Main) {
                var locationJob: Job? = null

                onAction<Unit> {
                    launch {
                        locationService.getLastKnownLocation()?.run {
                            dispatch(Message.OnInitialLocation(latLng))
                        }
                    }
                    dataStore.data
                        .map { it.defaultCity }
                        .distinctUntilChanged()
                        .onEach { dispatch(Message.OnInitialLocation(it.latLng)) }
                        .launchIn(this)
                }
                onIntent<StartLocationUpdates> {
                    locationService.startLocationUpdates()

                    dispatch(Message.OnNewLocation(LastLocation.None))

                    locationJob = reLaunch(locationJob) {
                        locationService.lastLocationFlow
                            .filterNotNull()
                            .map { LastLocation(it.latLng, it.bearing, it.speed) }
                            .collect {
                                dispatch(Message.OnNewLocation(lastLocation = it))
                                dispatch(Message.OnInitialLocation(it.latLng))

                                publish(Label.NewLocation(it.latLng))
                            }
                    }
                }
                onIntent<StopLocationUpdates> {
                    locationService.stopLocationUpdates()
                }
                onIntent<RequestCurrentLocation> {
                    launch {
                        val location = locationService.requestCurrentLocation()

                        if (location != null) {
                            dispatch(
                                Message.OnNewLocation(
                                    lastLocation = LastLocation(
                                        latLng = location.latLng,
                                        bearing = location.bearing,
                                        speed = location.speed
                                    )
                                )
                            )
                        }
                    }
                }
                onIntent<SetUserLocation> {
                    dispatch(
                        Message.OnUserLocation(
                            lastLocation = LastLocation(
                                latLng = it.latLng,
                                bearing = state.lastLocation.bearing,
                                speed = state.lastLocation.speed
                            )
                        )
                    )
                }
                onIntent<InvalidateLocation> {
                    dispatch(Message.OnNewLocation(lastLocation = state.userLocation))
                }
            },
            bootstrapper = SimpleBootstrapper(Unit),
            reducer = { message: Message ->
                when (message) {
                    is Message.OnNewLocation -> copy(lastLocation = message.lastLocation)
                    is Message.OnUserLocation -> copy(userLocation = message.lastLocation)
                    is Message.OnInitialLocation -> copy(initialLocation = message.latLng)
                }
            }
        ) {}
}
