package com.egoriku.grodnoroads.screen.settings.map.domain.component

import com.egoriku.grodnoroads.R
import com.egoriku.grodnoroads.common.DEFAULT_MAP_ZOOM_IN_CITY
import com.egoriku.grodnoroads.common.DEFAULT_MAP_ZOOM_OUT_CITY
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent.MapDialogState.None
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent.MapPref.CarCrash
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent.MapPref.DefaultCity
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent.MapPref.DefaultCity.City.Grodno
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent.MapPref.DefaultCity.City.Ozery
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent.MapPref.DefaultCity.City.Porechye
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent.MapPref.DefaultCity.City.Skidel
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent.MapPref.DefaultCity.City.Volkovysk
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent.MapPref.GoogleMapStyle
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent.MapPref.MapZoomInCity
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent.MapPref.MapZoomOutCity
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent.MapPref.MobileCameras
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent.MapPref.RoadIncident
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent.MapPref.StationaryCameras
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent.MapPref.TrafficJam
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent.MapPref.TrafficJamOnMap
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent.MapPref.TrafficPolice
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent.MapPref.WildAnimals
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow

interface MapSettingsComponent {

    val mapSettingsState: Flow<MapSettingState>
    val isLoading: Flow<Boolean>

    fun modify(preference: MapPref)
    fun openDialog(preference: MapPref)
    fun closeDialog()

    data class MapSettingState(
        val mapSettings: MapSettings = MapSettings(),
        val mapDialogState: MapDialogState = None
    )

    sealed interface MapDialogState {
        data class DefaultLocationDialogState(val defaultCity: DefaultCity) : MapDialogState
        data class MapZoomInCityDialogState(val mapZoomInCity: MapZoomInCity) : MapDialogState
        data class MapZoomOutCityDialogState(val mapZoomOutCity: MapZoomOutCity) : MapDialogState

        object None : MapDialogState
    }

    sealed interface MapPref {
        data class StationaryCameras(val isShow: Boolean = true) : MapPref
        data class MobileCameras(val isShow: Boolean = true) : MapPref
        data class TrafficPolice(val isShow: Boolean = true) : MapPref
        data class RoadIncident(val isShow: Boolean = true) : MapPref
        data class TrafficJam(val isShow: Boolean = true) : MapPref
        data class WildAnimals(val isShow: Boolean = true) : MapPref
        data class CarCrash(val isShow: Boolean = true) : MapPref

        data class TrafficJamOnMap(val isShow: Boolean = false) : MapPref
        data class GoogleMapStyle(val style: Style = Style.Minimal) : MapPref {
            enum class Style(val type: String) {
                Minimal(type = "minimalistic"),
                Detailed(type = "detailed")
            }
        }

        data class MapZoomInCity(
            val current: Float = DEFAULT_MAP_ZOOM_IN_CITY,
            val min: Float = 13f,
            val max: Float = 16.5f,
            val stepSize: Float = 0.5f
        ) : MapPref
        data class MapZoomOutCity(
            val current: Float = DEFAULT_MAP_ZOOM_OUT_CITY,
            val min: Float = 12f,
            val max: Float = 15.0f,
            val stepSize: Float = 0.5f
        ) : MapPref

        data class DefaultCity(
            val current: City = Grodno,
            val values: List<City> = listOf(Grodno, Skidel, Volkovysk, Ozery, Porechye)
        ) : MapPref {
            enum class City(val cityName: String, val latLng: LatLng) {
                Grodno(cityName = "grodno", latLng = LatLng(53.6687765, 23.8212226)),
                Skidel(cityName = "skidel", latLng = LatLng(53.579644, 24.237978)),
                Volkovysk(cityName = "volkovysk", latLng = LatLng(53.152847, 24.444242)),
                Ozery(cityName = "ozery", latLng = LatLng(53.722526, 24.178165)),
                Porechye(cityName = "porechye", latLng = LatLng(53.885623, 24.137678));

                companion object {
                    fun toCity(value: String) =
                        checkNotNull(values().find { it.cityName == value })

                    fun City.toResource(): Int = when (this) {
                        Grodno -> R.string.map_default_location_grodno
                        Skidel -> R.string.map_default_location_skidel
                        Volkovysk -> R.string.map_default_location_volkovysk
                        Ozery -> R.string.map_default_location_ozery
                        Porechye -> R.string.map_default_location_porechye
                    }
                }
            }
        }
    }

    data class MapSettings(
        val locationInfo: LocationInfo = LocationInfo(),
        val driveModeZoom: DriveModeZoom = DriveModeZoom(),
        val mapStyle: MapStyle = MapStyle(),
        val mapInfo: MapInfo = MapInfo(),
    ) {
        data class LocationInfo(
            val defaultCity: DefaultCity = DefaultCity()
        )

        data class DriveModeZoom(
            val mapZoomInCity: MapZoomInCity = MapZoomInCity(),
            val mapZoomOutCity: MapZoomOutCity = MapZoomOutCity(),
        )

        data class MapInfo(
            val stationaryCameras: StationaryCameras = StationaryCameras(),
            val mobileCameras: MobileCameras = MobileCameras(),
            val trafficPolice: TrafficPolice = TrafficPolice(),
            val roadIncident: RoadIncident = RoadIncident(),
            val carCrash: CarCrash = CarCrash(),
            val trafficJam: TrafficJam = TrafficJam(),
            val wildAnimals: WildAnimals = WildAnimals(),
        )

        data class MapStyle(
            val trafficJamOnMap: TrafficJamOnMap = TrafficJamOnMap(),
            val googleMapStyle: GoogleMapStyle = GoogleMapStyle(),
        )
    }
}