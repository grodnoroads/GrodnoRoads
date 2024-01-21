package com.egoriku.grodnoroads.specialevent.domain.util

import com.egoriku.grodnoroads.specialevent.domain.model.EventType
import com.egoriku.grodnoroads.specialevent.domain.model.EventType.Autumn
import com.egoriku.grodnoroads.specialevent.domain.model.EventType.Spring
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

internal object SpecialEventDispatcher {

    private const val MAY = 5
    private val MAY_RANGE = 25..31

    private const val JUNE = 6
    private val JUNE_RANGE = 1..5

    private const val AUGUST = 8
    private val AUGUST_RANGE = 25..31

    private const val SEPTEMBER = 9
    private val SEPTEMBER_RANGE = 1..5

    fun calculateType(
        current: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    ): EventType? {
        val day = current.dayOfMonth
        val month = current.monthNumber

        return when {
            month == MAY && day in MAY_RANGE -> Spring
            month == JUNE && day in JUNE_RANGE -> Spring
            month == AUGUST && day in AUGUST_RANGE -> Autumn
            month == SEPTEMBER && day in SEPTEMBER_RANGE -> Autumn
            else -> null
        }
    }
}