package com.egoriku.grodnoroads.guidance.data.mapper

import com.egoriku.grodnoroads.extensions.DateTime
import com.egoriku.grodnoroads.extensions.appendIfNotEmpty
import com.egoriku.grodnoroads.guidance.data.dto.ReportsDTO
import com.egoriku.grodnoroads.guidance.domain.model.MapEvent.Reports
import com.egoriku.grodnoroads.guidance.domain.model.MapEventType
import com.egoriku.grodnoroads.guidance.domain.model.MessageItem
import com.egoriku.grodnoroads.guidance.domain.model.Source
import com.egoriku.grodnoroads.location.LatLng
import com.egoriku.grodnoroads.location.roundDistanceTo
import com.egoriku.grodnoroads.uuid.Uuid
import kotlinx.collections.immutable.mutate
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

private const val MERGE_ALERT_DISTANCE = 200

internal object ReportsMapper : (List<ReportsDTO>) -> List<Reports> {

    override fun invoke(reportsDTO: List<ReportsDTO>): List<Reports> {
        return reportsDTO.groupBy { it.type }
            .mapValues { it.value.mergeReports() }
            .values
            .flatten()
    }

    private fun List<ReportsDTO>.mergeReports(): List<Reports> {
        val mergedReports = mutableListOf<Reports>()

        forEach { data ->
            val index = mergedReports.indexOfFirst { calcAction ->
                val distance =
                    calcAction.position roundDistanceTo LatLng(data.latitude, data.longitude)

                distance < MERGE_ALERT_DISTANCE
            }

            if (index != -1) {
                val item: Reports = mergedReports[index]

                mergedReports[index] = item.copy(
                    messages = item.messages.toPersistentList()
                        .mutate {
                            it += MessageItem(
                                message = "(${DateTime.formatToTime(data.timestamp)}) ${data.message.emojiFix()}",
                                source = Source.sourceFromString(data.source)
                            )
                        },
                    position = LatLng(data.latitude, data.longitude),
                    dialogTitle = buildDialogTitle(data),
                    markerMessage = buildMarkerShortMessage(data)
                )
            } else {
                val action = Reports(
                    id = Uuid.randomUUID(),
                    messages = persistentListOf(
                        MessageItem(
                            message = "(${DateTime.formatToTime(data.timestamp)}) ${data.message.emojiFix()}",
                            source = Source.sourceFromString(
                                data.source
                            )
                        )
                    ),
                    markerMessage = buildMarkerShortMessage(data),
                    dialogTitle = buildDialogTitle(data),
                    position = LatLng(data.latitude, data.longitude),
                    mapEventType = MapEventType.eventFromString(
                        data.type
                    )
                )

                mergedReports.add(action)
            }
        }
        return mergedReports
    }

    private fun buildMarkerShortMessage(data: ReportsDTO) =
        when (MapEventType.eventFromString(data.type)) {
            MapEventType.TrafficPolice -> buildString {
                append("(${DateTime.formatToTime(data.timestamp)}) ")
                append(MapEventType.TrafficPolice.emoji)
                appendIfNotEmpty(data.shortMessage, " (${data.shortMessage})")
            }
            MapEventType.RoadIncident -> buildString {
                append("(${DateTime.formatToTime(data.timestamp)}) ")
                append(MapEventType.RoadIncident.emoji)
                appendIfNotEmpty(data.shortMessage, " (${data.shortMessage})")
            }
            MapEventType.WildAnimals -> buildString {
                append("(${DateTime.formatToTime(data.timestamp)}) ")
                append(MapEventType.WildAnimals.emoji)
                appendIfNotEmpty(data.shortMessage, " (${data.shortMessage})")
            }
            MapEventType.CarCrash -> buildString {
                append("(${DateTime.formatToTime(data.timestamp)}) ")
                append(MapEventType.CarCrash.emoji)
                appendIfNotEmpty(data.shortMessage, " (${data.shortMessage})")
            }
            MapEventType.TrafficJam -> buildString {
                append("(${DateTime.formatToTime(data.timestamp)}) ")
                append(MapEventType.TrafficJam.emoji)
                appendIfNotEmpty(data.shortMessage, " (${data.shortMessage})")
            }
            else -> data.shortMessage
        }

    private fun buildDialogTitle(data: ReportsDTO) =
        when (MapEventType.eventFromString(data.type)) {
            MapEventType.TrafficPolice -> buildString {
                append(MapEventType.TrafficPolice.emoji)
                appendIfNotEmpty(data.shortMessage, " ${data.shortMessage}")
            }
            MapEventType.RoadIncident -> buildString {
                append(MapEventType.RoadIncident.emoji)
                appendIfNotEmpty(data.shortMessage, " ${data.shortMessage}")
            }
            MapEventType.WildAnimals -> buildString {
                append(MapEventType.WildAnimals.emoji)
                appendIfNotEmpty(data.shortMessage, " ${data.shortMessage}")
            }
            MapEventType.CarCrash -> buildString {
                append(MapEventType.CarCrash.emoji)
                appendIfNotEmpty(data.shortMessage, " ${data.shortMessage}")
            }
            MapEventType.TrafficJam -> buildString {
                append(MapEventType.TrafficJam.emoji)
                appendIfNotEmpty(data.shortMessage, " ${data.shortMessage}")
            }
            else -> data.shortMessage
        }

    private fun String.emojiFix() = replace("(policecar)", "\uD83D\uDE93")
}