package com.egoriku.grodnoroads.specialevent.util

import com.egoriku.grodnoroads.specialevent.domain.model.EventType
import com.egoriku.grodnoroads.specialevent.domain.util.SpecialEventDispatcher
import kotlinx.datetime.LocalDateTime
import kotlin.test.Test
import kotlin.test.assertEquals

class SpecialEventDispatcherTest {

    @Test
    fun testCalculateType() {
        val springDate = LocalDateTime(2023, 5, 30, 0, 0)
        val autumnDate = LocalDateTime(2024, 8, 30, 0, 0)
        val noneDate = LocalDateTime(2025, 11, 30, 0, 0)

        val springType = SpecialEventDispatcher.calculateType(springDate)
        val autumnType = SpecialEventDispatcher.calculateType(autumnDate)
        val noneType = SpecialEventDispatcher.calculateType(noneDate)

        assertEquals(EventType.Spring, springType)
        assertEquals(EventType.Autumn, autumnType)
        assertEquals(EventType.None, noneType)
    }
}