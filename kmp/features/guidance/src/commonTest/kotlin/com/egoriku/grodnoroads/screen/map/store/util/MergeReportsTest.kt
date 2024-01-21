package com.egoriku.grodnoroads.screen.map.store.util

import com.egoriku.grodnoroads.extensions.DateTime
import com.egoriku.grodnoroads.extensions.second
import com.egoriku.grodnoroads.extensions.third
import com.egoriku.grodnoroads.guidance.data.dto.ReportsDTO
import com.egoriku.grodnoroads.guidance.data.mapper.ReportsMapper
import com.egoriku.grodnoroads.guidance.domain.model.MapEventType.RoadIncident
import com.egoriku.grodnoroads.guidance.domain.model.MapEventType.TrafficPolice
import com.egoriku.grodnoroads.guidance.domain.model.Source.*
import com.egoriku.grodnoroads.location.LatLng
import kotlinx.datetime.TimeZone
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

class MergeReportsTest {

    @BeforeTest
    fun init() {
        DateTime.defaultTimeZone = TimeZone.UTC
    }

    @Test
    fun `empty reports`() {
        val reports = ReportsMapper(reportsDTO = emptyList())

        assertEquals(0, reports.size)
    }

    @Test
    fun single() {
        val reports = ReportsMapper(
            reportsDTO = listOf(
                ReportsDTO(
                    timestamp = (8.hours + 6.minutes).inWholeMilliseconds,
                    message = "Long message (policecar)",
                    source = App.source,
                    shortMessage = "Short message",
                    latitude = 53.666199,
                    longitude = 23.784990,
                    type = TrafficPolice.type
                )
            )
        )

        assertEquals(1, reports.size)

        with(reports.first()) {
            assertEquals("(8:06) ${TrafficPolice.emoji} (Short message)", markerMessage)
            assertEquals(TrafficPolice, mapEventType)
            assertEquals("${TrafficPolice.emoji} Short message", dialogTitle)

            with(messages) {
                assertEquals(1, size)
                assertEquals("(8:06) Long message \uD83D\uDE93", first().message)
                assertEquals(App, first().source)
            }
        }
    }

    @Test
    fun `single report`() {
        val reports = ReportsMapper(
            reportsDTO = listOf(
                ReportsDTO(
                    timestamp = (0.hours + 30.minutes).inWholeMilliseconds,
                    message = "Long message (policecar)",
                    source = App.source,
                    shortMessage = "Short message",
                    latitude = 53.666199,
                    longitude = 23.784990,
                    type = TrafficPolice.type
                )
            )
        )

        assertEquals(1, reports.size)

        with(reports.first()) {
            assertEquals("(0:30) ${TrafficPolice.emoji} (Short message)", markerMessage)
            assertEquals(TrafficPolice, mapEventType)
            assertEquals("${TrafficPolice.emoji} Short message", dialogTitle)

            with(messages) {
                assertEquals(1, size)
                assertEquals("(0:30) Long message \uD83D\uDE93", first().message)
                assertEquals(App, first().source)
            }
        }
    }

    @Test
    fun `2 different reports separated distance`() {
        val reports = ReportsMapper(
            reportsDTO = listOf(
                ReportsDTO(
                    timestamp = (12.hours + 30.minutes).inWholeMilliseconds,
                    message = "Long message 1",
                    source = App.source,
                    shortMessage = "Short message 1",
                    latitude = 53.666199,
                    longitude = 23.784990,
                    type = TrafficPolice.type
                ),
                ReportsDTO(
                    timestamp = (12.hours + 51.minutes).inWholeMilliseconds,
                    message = "Long message 2",
                    source = App.source,
                    shortMessage = "Short message 2",
                    latitude = 53.672628,
                    longitude = 23.875794,
                    type = RoadIncident.type
                )
            )
        )

        assertEquals(2, reports.size)

        with(reports.first()) {
            assertEquals(TrafficPolice, mapEventType)
            assertEquals("${TrafficPolice.emoji} Short message 1", dialogTitle)

            with(messages) {
                assertEquals(1, size)
                assertEquals("(12:30) Long message 1", first().message)
            }

            assertEquals("(12:30) ${TrafficPolice.emoji} (Short message 1)", markerMessage)
            assertEquals(LatLng(53.666199, 23.784990), position)
        }

        with(reports.second()) {
            assertEquals(RoadIncident, mapEventType)
            assertEquals("${RoadIncident.emoji} Short message 2", dialogTitle)

            with(messages) {
                assertEquals(1, size)
                assertEquals("(12:51) Long message 2", first().message)
            }

            assertEquals("(12:51) ${RoadIncident.emoji} (Short message 2)", markerMessage)
            assertEquals(LatLng(53.672628, 23.875794), position)
        }
    }

    @Test
    fun `2 different actions nearest distance`() {
        val reports = ReportsMapper(
            reportsDTO = listOf(
                ReportsDTO(
                    timestamp = (12.hours + 30.minutes).inWholeMilliseconds,
                    message = "Long message 1",
                    source = App.source,
                    shortMessage = "Short message 1",
                    latitude = 53.666199,
                    longitude = 23.784990,
                    type = TrafficPolice.type
                ),
                ReportsDTO(
                    timestamp = (12.hours + 51.minutes).inWholeMilliseconds,
                    message = "Long message 2",
                    source = Telegram.source,
                    shortMessage = "Short message 2",
                    latitude = 53.666216,
                    longitude = 23.785078,
                    type = RoadIncident.type
                )
            )
        )

        assertEquals(2, reports.size)

        with(reports.first()) {
            assertEquals(TrafficPolice, mapEventType)
            assertEquals("(12:30) ${TrafficPolice.emoji} (Short message 1)", markerMessage)
            assertEquals("${TrafficPolice.emoji} Short message 1", dialogTitle)
            assertEquals(LatLng(53.666199, 23.784990), position)

            assertEquals(1, messages.size)
            with(messages.first()) {
                assertEquals("(12:30) Long message 1", message)
                assertEquals(App, source)
            }
        }

        with(reports.second()) {
            assertEquals(RoadIncident, mapEventType)
            assertEquals("(12:51) ${RoadIncident.emoji} (Short message 2)", markerMessage)
            assertEquals("${RoadIncident.emoji} Short message 2", dialogTitle)

            assertEquals(LatLng(53.666216, 23.785078), position)

            assertEquals(1, messages.size)
            with(messages.first()) {
                assertEquals("(12:51) Long message 2", message)
                assertEquals(Telegram, source)
            }
        }
    }

    @Test
    fun `2 identical actions separated location`() {
        val reports = ReportsMapper(
            reportsDTO = listOf(
                ReportsDTO(
                    timestamp = (12.hours + 30.minutes).inWholeMilliseconds,
                    message = "Long message 1",
                    source = App.source,
                    shortMessage = "Short message 1",
                    latitude = 53.666199,
                    longitude = 23.784990,
                    type = TrafficPolice.type
                ),
                ReportsDTO(
                    timestamp = (12.hours + 51.minutes).inWholeMilliseconds,
                    message = "Long message 2",
                    source = Telegram.source,
                    shortMessage = "Short message 2",
                    latitude = 53.666216,
                    longitude = 23.785078,
                    type = RoadIncident.type
                )
            )
        )

        assertEquals(2, reports.size)

        with(reports.first()) {
            assertEquals(TrafficPolice, mapEventType)
            assertEquals("(12:30) ${TrafficPolice.emoji} (Short message 1)", markerMessage)
            assertEquals("${TrafficPolice.emoji} Short message 1", dialogTitle)

            assertEquals(LatLng(53.666199, 23.784990), position)

            assertEquals(1, messages.size)
            with(messages.first()) {
                assertEquals("(12:30) Long message 1", message)
                assertEquals(App, source)
            }
        }

        with(reports.second()) {
            assertEquals(RoadIncident, mapEventType)
            assertEquals("(12:51) ${RoadIncident.emoji} (Short message 2)", markerMessage)
            assertEquals("${RoadIncident.emoji} Short message 2", dialogTitle)
            assertEquals(LatLng(53.666216, 23.785078), position)

            assertEquals(1, messages.size)
            with(messages.first()) {
                assertEquals("(12:51) Long message 2", message)
                assertEquals(Telegram, source)
            }
        }
    }

    @Test
    fun `2 identical actions nearest distance`() {
        val reports = ReportsMapper(
            reportsDTO = listOf(
                ReportsDTO(
                    timestamp = (12.hours + 30.minutes).inWholeMilliseconds,
                    message = "Long message 1",
                    source = App.source,
                    shortMessage = "Short message 1",
                    latitude = 53.666199,
                    longitude = 23.784990,
                    type = RoadIncident.type
                ),
                ReportsDTO(
                    timestamp = (12.hours + 51.minutes).inWholeMilliseconds,
                    message = "Long message 2",
                    source = Telegram.source,
                    shortMessage = "Short message 2",
                    latitude = 53.666216,
                    longitude = 23.785078,
                    type = RoadIncident.type
                )
            )
        )

        assertEquals(1, reports.size)

        with(reports.first()) {
            assertEquals(RoadIncident, mapEventType)
            assertEquals("(12:51) ${RoadIncident.emoji} (Short message 2)", markerMessage)
            assertEquals("${RoadIncident.emoji} Short message 2", dialogTitle)
            assertEquals(LatLng(53.666216, 23.785078), position)

            assertEquals(2, messages.size)
            with(messages.first()) {
                assertEquals("(12:30) Long message 1", message)
                assertEquals(App, source)
            }

            with(messages.second()) {
                assertEquals("(12:51) Long message 2", message)
                assertEquals(Telegram, source)
            }
        }
    }

    @Test
    fun `2 nearest and 1 away identical`() {
        val reports = ReportsMapper(
            reportsDTO = listOf(
                ReportsDTO(
                    timestamp = (12.hours + 30.minutes).inWholeMilliseconds,
                    message = "Long message 1",
                    source = App.source,
                    shortMessage = "Short message 1",
                    latitude = 53.666199,
                    longitude = 23.784990,
                    type = TrafficPolice.type
                ),
                ReportsDTO(
                    timestamp = (12.hours + 51.minutes).inWholeMilliseconds,
                    message = "Long message 2",
                    source = Telegram.source,
                    shortMessage = "Short message 2",
                    latitude = 53.666216,
                    longitude = 23.785078,
                    type = TrafficPolice.type
                ),
                ReportsDTO(
                    timestamp = (12.hours + 52.minutes).inWholeMilliseconds,
                    message = "Long message 3",
                    source = Viber.source,
                    shortMessage = "Short message 3",
                    latitude = 53.672628,
                    longitude = 23.875794,
                    type = TrafficPolice.type
                )
            )
        )

        assertEquals(2, reports.size)

        with(reports.first()) {
            assertEquals(TrafficPolice, mapEventType)
            assertEquals("(12:51) ${TrafficPolice.emoji} (Short message 2)", markerMessage)
            assertEquals("${TrafficPolice.emoji} Short message 2", dialogTitle)
            assertEquals(LatLng(53.666216, 23.785078), position)

            assertEquals(2, messages.size)
            with(messages.first()) {
                assertEquals("(12:30) Long message 1", message)
                assertEquals(App, source)
            }

            with(messages.second()) {
                assertEquals("(12:51) Long message 2", message)
                assertEquals(Telegram, source)
            }
        }

        with(reports.second()) {
            assertEquals(TrafficPolice, mapEventType)
            assertEquals("(12:52) ${TrafficPolice.emoji} (Short message 3)", markerMessage)
            assertEquals("${TrafficPolice.emoji} Short message 3", dialogTitle)
            assertEquals(LatLng(53.672628, 23.875794), position)

            assertEquals(1, messages.size)
            with(messages.first()) {
                assertEquals("(12:52) Long message 3", message)
                assertEquals(Viber, source)
            }
        }
    }

    @Test
    fun `3 identical action separated location`() {
        val reports = ReportsMapper(
            reportsDTO = listOf(
                ReportsDTO(
                    timestamp = (12.hours + 30.minutes).inWholeMilliseconds,
                    message = "Long message 1",
                    source = App.source,
                    shortMessage = "Short message 1",
                    latitude = 53.666199,
                    longitude = 23.784990,
                    type = TrafficPolice.type
                ),
                ReportsDTO(
                    timestamp = (12.hours + 40.minutes).inWholeMilliseconds,
                    message = "Long message 2",
                    source = Telegram.source,
                    shortMessage = "Short message 2",
                    latitude = 53.673657,
                    longitude = 23.869954,
                    type = TrafficPolice.type
                ),
                ReportsDTO(
                    timestamp = (12.hours + 51.minutes).inWholeMilliseconds,
                    message = "Long message 3",
                    source = Viber.source,
                    shortMessage = "Short message 3",
                    latitude = 53.719067,
                    longitude = 23.850779,
                    type = TrafficPolice.type
                )
            )
        )

        assertEquals(3, reports.size)

        with(reports.first()) {
            assertEquals(TrafficPolice, mapEventType)
            assertEquals("(12:30) ${TrafficPolice.emoji} (Short message 1)", markerMessage)
            assertEquals("${TrafficPolice.emoji} Short message 1", dialogTitle)
            assertEquals(LatLng(53.666199, 23.784990), position)

            assertEquals(1, messages.size)
            with(messages.first()) {
                assertEquals("(12:30) Long message 1", message)
                assertEquals(App, source)
            }
        }

        with(reports.second()) {
            assertEquals(TrafficPolice, mapEventType)
            assertEquals("(12:40) ${TrafficPolice.emoji} (Short message 2)", markerMessage)
            assertEquals("${TrafficPolice.emoji} Short message 2", dialogTitle)
            assertEquals(LatLng(53.673657, 23.869954), position)

            assertEquals(1, messages.size)
            with(messages.first()) {
                assertEquals("(12:40) Long message 2", message)
                assertEquals(Telegram, source)
            }
        }

        with(reports.third()) {
            assertEquals(TrafficPolice, mapEventType)
            assertEquals("(12:51) ${TrafficPolice.emoji} (Short message 3)", markerMessage)
            assertEquals("${TrafficPolice.emoji} Short message 3", dialogTitle)
            assertEquals(LatLng(53.719067, 23.850779), position)

            assertEquals(1, messages.size)
            with(messages.first()) {
                assertEquals("(12:51) Long message 3", message)
                assertEquals(Viber, source)
            }
        }
    }

    @Test
    fun `3 identical action nearest location`() {
        val reports = ReportsMapper(
            reportsDTO = listOf(
                ReportsDTO(
                    timestamp = (12.hours + 30.minutes).inWholeMilliseconds,
                    message = "Long message 1",
                    source = App.source,
                    shortMessage = "Short message 1",
                    latitude = 53.673217,
                    longitude = 23.871806,
                    type = TrafficPolice.type
                ),
                ReportsDTO(
                    timestamp = (12.hours + 40.minutes).inWholeMilliseconds,
                    message = "Long message 2",
                    source = App.source,
                    shortMessage = "Short message 2",
                    latitude = 53.673187,
                    longitude = 23.871366,
                    type = TrafficPolice.type
                ),
                ReportsDTO(
                    timestamp = (12.hours + 51.minutes).inWholeMilliseconds,
                    message = "Long message 3",
                    source = App.source,
                    shortMessage = "Short message 3",
                    latitude = 53.673274,
                    longitude = 23.870778,
                    type = TrafficPolice.type
                )
            )
        )

        assertEquals(1, reports.size)

        with(reports.first()) {
            assertEquals("(12:51) ${TrafficPolice.emoji} (Short message 3)", markerMessage)
            assertEquals("${TrafficPolice.emoji} Short message 3", dialogTitle)
            assertEquals(TrafficPolice, mapEventType)

            assertEquals(3, messages.size)

            with(messages) {
                assertEquals("(12:30) Long message 1", first().message)
                assertEquals("(12:40) Long message 2", second().message)
                assertEquals("(12:51) Long message 3", third().message)
            }
        }
    }
}