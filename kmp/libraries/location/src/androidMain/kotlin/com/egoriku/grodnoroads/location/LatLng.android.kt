package com.egoriku.grodnoroads.location

fun LatLng.toGmsLatLng() = com.google.android.gms.maps.model.LatLng(latitude, longitude)

fun com.google.android.gms.maps.model.LatLng.toLatLng() = LatLng(latitude, longitude)