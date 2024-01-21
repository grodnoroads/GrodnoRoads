package com.egoriku.grodnoroads.shared.persistent.appearance

enum class Language(val lang: String) {
    System("system"),
    Russian("ru"),
    English("en"),
    Belarusian("be");

    companion object {
        fun localeToLanguage(value: String?) = entries.find { it.lang == value } ?: System
    }
}