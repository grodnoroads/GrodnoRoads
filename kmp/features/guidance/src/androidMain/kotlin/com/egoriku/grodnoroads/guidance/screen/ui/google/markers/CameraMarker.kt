package com.egoriku.grodnoroads.guidance.screen.ui.google.markers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.egoriku.grodnoroads.location.LatLng
import com.egoriku.grodnoroads.location.toGmsLatLng
import com.egoriku.grodnoroads.guidance.screen.ui.google.MarkerSize
import com.egoriku.grodnoroads.maps.compose.MapUpdater
import com.egoriku.grodnoroads.maps.compose.inScope
import com.egoriku.grodnoroads.maps.compose.rememberIconMarker
import com.google.android.gms.maps.model.BitmapDescriptor

context(MapUpdater)
@Composable
fun CameraMarker(
    position: LatLng,
    markerSize: MarkerSize,
    icon: () -> BitmapDescriptor,
    onClick: () -> Unit
) {
    val marker = rememberIconMarker(
        position = position.toGmsLatLng(),
        icon = icon,
        onMarkerClick = onClick,
        zIndex = 1f
    )

    LaunchedEffect(markerSize) {
        marker.inScope {
            setIcon(icon())
        }
    }
}