package com.egoriku.grodnoroads.settings.changelog.domain.util

internal const val DATE_FORMAT_PATTERN = "dd MMMM, yyyy"

expect class DateFormatter() {

    fun formatTime(timeLong: Long): String
}