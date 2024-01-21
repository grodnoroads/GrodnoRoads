package com.egoriku.grodnoroads.shared.persistent

import com.egoriku.grodnoroads.resources.R
import com.egoriku.grodnoroads.shared.persistent.appearance.Language
import com.egoriku.grodnoroads.shared.persistent.appearance.Theme
import com.egoriku.grodnoroads.shared.persistent.map.location.City

fun Language.toStringResource() = when (this) {
    Language.Russian -> R.string.appearance_app_language_ru
    Language.English -> R.string.appearance_app_language_en
    Language.Belarusian -> R.string.appearance_app_language_be
    Language.System -> R.string.appearance_app_language_system
}

fun Theme.toStringResource() = when (this) {
    Theme.System -> R.string.appearance_app_theme_system
    Theme.Dark -> R.string.appearance_app_theme_dark
    Theme.Light -> R.string.appearance_app_theme_light
}

fun City.toStringResource(): Int = when (this) {
    City.Grodno -> R.string.map_default_location_grodno
    City.Skidel -> R.string.map_default_location_skidel
    City.Volkovysk -> R.string.map_default_location_volkovysk
    City.Ozery -> R.string.map_default_location_ozery
    City.Porechye -> R.string.map_default_location_porechye
    City.Berestovitca -> R.string.map_default_location_berestovitca
    City.Shuchin -> R.string.map_default_location_shuchin
    City.Mosty -> R.string.map_default_location_mosty
    City.Slonim -> R.string.map_default_location_slonim
    City.Lida -> R.string.map_default_location_lida
    City.Indura -> R.string.map_default_location_indura
    City.Dyatlovo -> R.string.map_default_location_dyatlovo
    City.Novogrudok -> R.string.map_default_location_novogrudok
    City.Korelichi -> R.string.map_default_location_korelichi
}