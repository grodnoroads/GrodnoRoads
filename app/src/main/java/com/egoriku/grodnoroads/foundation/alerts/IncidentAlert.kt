package com.egoriku.grodnoroads.foundation.alerts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.R
import com.egoriku.grodnoroads.foundation.HSpacer

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun IncidentAlert(
    title: String,
    distance: Int,
    message: String
) {
    Card(modifier = Modifier.fillMaxWidth(), elevation = 5.dp) {
        Column(modifier = Modifier.padding(8.dp)) {
            Column(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    text = title,
                    style = MaterialTheme.typography.body1,
                )
                HSpacer(dp = 4.dp)
                Text(
                    text = pluralStringResource(
                        R.plurals.camera_alerts_plurals_distance,
                        distance,
                        distance
                    ),
                    style = MaterialTheme.typography.body2
                )
            }

            HSpacer(dp = 8.dp)

            Text(
                text = message,
                style = MaterialTheme.typography.caption
            )
        }
    }
}

@Preview
@Preview(locale = "ru")
@Composable
fun PreviewIncidentAlert() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        IncidentAlert(
            title = stringResource(R.string.alerts_accident),
            distance = 200,
            message = "· (15:30) Старый мост ДТП в правой полосе по направлению от кольца в центр\n· (15:45) Новый мост в левой полосе по направлению"
        )
        IncidentAlert(
            title = stringResource(R.string.alerts_police),
            distance = 350,
            message = "· Славинского беларуснефть на скорость"
        )
    }
}