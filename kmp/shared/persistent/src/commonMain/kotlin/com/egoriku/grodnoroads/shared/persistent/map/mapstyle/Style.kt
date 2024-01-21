package com.egoriku.grodnoroads.shared.persistent.map.mapstyle

enum class Style(val type: String) {
    Minimal(type = "minimalistic"),
    Detailed(type = "detailed"),
    Unknown(type = "unknown")
}