package com.egoriku.grodnoroads.guidance.data.dto.areas

import kotlinx.serialization.Serializable

typealias MultiPolygon = List<List<List<List<Double>>>>

@Serializable
data class AreasDTO(
    val type: String,
    val features: List<Feature>
)

@Serializable
data class Feature(
    val type: String,
    val geometry: MultiPolygonGeometry,
)

@Serializable
data class MultiPolygonGeometry(
    val type: String,
    val coordinates: MultiPolygon
)
