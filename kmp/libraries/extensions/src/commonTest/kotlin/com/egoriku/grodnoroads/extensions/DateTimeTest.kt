package com.egoriku.grodnoroads.extensions

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

class DateTimeTest {

    @BeforeTest
    fun init() {
        DateTime.defaultTimeZone = TimeZone.UTC
    }

    @Test
    fun formatTest() {
        assertEquals(
            "8:06",
            DateTime.formatToTime((8.hours + 6.minutes).inWholeMilliseconds)
        )
        assertEquals(
            "17:09",
            DateTime.formatToTime((17.hours + 9.minutes).inWholeMilliseconds)
        )

        val date = LocalDateTime(
            year = 2024,
            monthNumber = 1,
            dayOfMonth = 17,
            hour = 17,
            minute = 9,
            second = 12
        ).toInstant(TimeZone.UTC).toEpochMilliseconds()
        assertEquals(
            "17:09",
            DateTime.formatToTime(date)
        )
    }
}