package com.egoriku.grodnoroads.shared.persistent.map.location

import com.egoriku.grodnoroads.location.LatLng

enum class City(val cityName: String, val latLng: LatLng) {
    Grodno(cityName = "grodno", latLng = LatLng(53.6687765, 23.8212226)),
    Skidel(cityName = "skidel", latLng = LatLng(53.579644, 24.237978)),
    Volkovysk(cityName = "volkovysk", latLng = LatLng(53.152847, 24.444242)),
    Ozery(cityName = "ozery", latLng = LatLng(53.722526, 24.178165)),
    Porechye(cityName = "porechye", latLng = LatLng(53.885623, 24.137678)),
    Berestovitca(cityName = "berestovitca", latLng = LatLng(53.191924, 24.018453)),
    Shuchin(cityName = "shuchin", latLng = LatLng(53.604412, 24.742567)),
    Mosty(cityName = "mosty", latLng = LatLng(53.413523, 24.542953)),
    Slonim(cityName = "slonim", latLng = LatLng(53.092705, 25.319268)),
    Lida(cityName = "lida", latLng = LatLng(53.891667, 25.302254)),
    Indura(cityName = "indura", latLng = LatLng(53.459580, 23.883849)),
    Dyatlovo(cityName = "dyatlovo", latLng = LatLng(53.465115, 25.407569)),
    Novogrudok(cityName = "novogrudok", latLng = LatLng(53.598095, 25.825635)),
    Korelichi(cityName = "korelichi", latLng = LatLng(53.570510, 26.139959));

    companion object {
        fun toCity(value: String) = checkNotNull(entries.find { it.cityName == value })
    }
}