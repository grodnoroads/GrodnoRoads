package com.egoriku.grodnoroads.guidance.domain.model

enum class MapEventType(
    val type: String,
    val emoji: String
) {
    TrafficPolice(type = "police", emoji = "👮"),
    RoadIncident(type = "road_incident", emoji = "❗"),
    CarCrash(type = "car_crash", emoji = "💥🚗"),
    TrafficJam(type = "traffic_jam", emoji = "🚗🚕🚛"),
    WildAnimals(type = "wild_animals", emoji = "🦌"),

    Unsupported(type = "unsupported", emoji = "\uD83D\uDE12");

    companion object {
        fun eventFromString(value: String) = entries.find { it.type == value } ?: Unsupported
    }
}