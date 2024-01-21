package com.egoriku.grodnoroads.settings.changelog.domain.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

actual class DateFormatter {
    private val dayMonthYearFormatter = SimpleDateFormat(DATE_FORMAT_PATTERN, Locale.getDefault())

    actual fun formatTime(timeLong: Long): String {
        return dayMonthYearFormatter.format(Date(timeLong * 1000))
    }
}