package com.egoriku.grodnoroads.guidance.screen.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateTimeFormatter {
    private const val PATTERN = "dd.MM.yyyy"
    private val extendedFormatter = SimpleDateFormat(PATTERN, Locale.getDefault())

    fun toDate(timestamp: Long): String {
        val dateTime = Date(timestamp + 60 * 1000)
        return extendedFormatter.format(dateTime)
    }
}