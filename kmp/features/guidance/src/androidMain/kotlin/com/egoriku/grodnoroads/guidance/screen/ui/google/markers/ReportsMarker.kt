package com.egoriku.grodnoroads.guidance.screen.ui.google.markers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import com.egoriku.grodnoroads.location.LatLng
import com.egoriku.grodnoroads.location.toGmsLatLng
import com.egoriku.grodnoroads.guidance.screen.ui.google.MarkerSize
import com.egoriku.grodnoroads.guidance.screen.ui.google.MarkerSize.Large
import com.egoriku.grodnoroads.guidance.screen.ui.google.MarkerSize.Small
import com.egoriku.grodnoroads.maps.compose.MapUpdater
import com.egoriku.grodnoroads.maps.compose.inScope
import com.egoriku.grodnoroads.maps.compose.rememberIconMarker
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.maps.android.ui.IconGenerator

context(MapUpdater)
@Composable
fun ReportsMarker(
    position: LatLng,
    markerSize: MarkerSize,
    iconGenerator: () -> IconGenerator,
    iconProvider: () -> BitmapDescriptor?,
    message: String,
    onClick: () -> Unit
) {
    val icon = remember(message, markerSize) {
        {
            when (markerSize) {
                Large -> BitmapDescriptorFactory.fromBitmap(iconGenerator().makeIcon(message))
                Small -> {
                    iconProvider() ?: BitmapDescriptorFactory.fromBitmap(
                        iconGenerator().makeIcon(message)
                    )
                }
            }
        }
    }

    val marker = rememberIconMarker(
        position = position.toGmsLatLng(),
        icon = icon,
        zIndex = 2f,
        title = message,
        onMarkerClick = onClick
    )

    LaunchedEffect(markerSize) {
        marker.inScope {
            setIcon(icon())
        }
    }
}