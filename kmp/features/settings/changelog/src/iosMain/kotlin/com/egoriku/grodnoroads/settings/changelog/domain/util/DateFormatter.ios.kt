package com.egoriku.grodnoroads.settings.changelog.domain.util

import platform.Foundation.*

actual class DateFormatter {
    actual fun formatTime(timeLong: Long): String {
        val date = NSDate.dateWithTimeIntervalSince1970(timeLong.toDouble())
        val dateFormatter = NSDateFormatter().apply {
            dateFormat = DATE_FORMAT_PATTERN
            locale = NSLocale.localeWithLocaleIdentifier(NSLocale.currentLocale.localeIdentifier)
        }
        return dateFormatter.stringFromDate(date)
    }
}