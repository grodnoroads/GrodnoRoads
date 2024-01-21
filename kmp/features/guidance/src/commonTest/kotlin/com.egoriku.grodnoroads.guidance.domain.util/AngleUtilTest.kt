package com.egoriku.grodnoroads.guidance.domain.util

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class AngleUtilTest {

    @Test
    fun `test possible values`() {
        assertTrue(isAngleInRange(190f, 10f, false))
        assertTrue(isAngleInRange(28f, 197f, false))
        assertFalse(isAngleInRange(217f, 197f, false))
        assertFalse(isAngleInRange(20f, 10f, false))
        assertFalse(isAngleInRange(117f, 120f, false))
        assertFalse(isAngleInRange(120f, 30f, false))
        assertFalse(isAngleInRange(30f, 120f, false))
        assertFalse(isAngleInRange(359f, 320f, false))
        assertTrue(isAngleInRange(359f, 173f, false))
        assertFalse(isAngleInRange(320f, 359f, false))
        assertTrue(isAngleInRange(289f, 120f, false))
        assertTrue(isAngleInRange(106f, 280f, false))

        assertTrue(isAngleInRange(117f, 120f, true))
        assertTrue(isAngleInRange(117f, 298f, true))
    }
}