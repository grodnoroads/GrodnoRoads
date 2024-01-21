package com.egoriku.grodnoroads.guidance.screen.ui.dialog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsPreview
import com.egoriku.grodnoroads.guidance.domain.model.MapEventType
import com.egoriku.grodnoroads.guidance.domain.model.MapEventType.*
import com.egoriku.grodnoroads.guidance.screen.ui.dialog.common.CommonReportDialog
import com.egoriku.grodnoroads.resources.R
import kotlinx.collections.immutable.toImmutableList

@Composable
fun IncidentDialog(
    onClose: () -> Unit,
    onSend: (
        mapEvent: MapEventType,
        shortMessage: String,
        message: String
    ) -> Unit
) {
    // TODO: Add localization
    val actions by remember {
        mutableStateOf(
            mapOf(
                "ДТП" to CarCrash,
                "Пробка" to TrafficJam,
                "Сломалась машина" to RoadIncident,
                "Дорожные работы" to RoadIncident,
                "Не работают светофоры" to RoadIncident,
                "Дикие животные" to WildAnimals,
                "Другое" to RoadIncident
            )
        )
    }
    CommonReportDialog(
        titleRes = R.string.dialog_incident,
        actions = actions.keys.toImmutableList(),
        onClose = onClose,
        onSelected = { index, inputText ->
            val pair = actions.toList()[index]

            val eventType = pair.second
            val shortMessage = pair.first

            onSend(
                eventType,
                shortMessage,
                inputText.ifEmpty { shortMessage }
            )
        }
    )
}

@GrodnoRoadsPreview
@Composable
fun PreviewIncidentDialog() = GrodnoRoadsM3ThemePreview {
    IncidentDialog(onClose = {}, onSend = { _, _, _ -> })
}