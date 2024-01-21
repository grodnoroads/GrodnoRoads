package com.egoriku.grodnoroads.appsettings.screen.util

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

@Composable
fun rememberShareIntent(): (String) -> Unit {
    val context = LocalContext.current

    return remember {
        { url: String ->
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, url)
                type = "text/plain"
            }
            context.startActivity(Intent.createChooser(sendIntent, null))
        }
    }
}
