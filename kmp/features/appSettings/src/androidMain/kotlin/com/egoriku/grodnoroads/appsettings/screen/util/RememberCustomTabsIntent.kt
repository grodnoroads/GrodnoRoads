package com.egoriku.grodnoroads.appsettings.screen.util

import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri

@Composable
fun rememberCustomTabIntent(): (String) -> Unit {
    val context = LocalContext.current

    return remember {
        { url: String ->
            CustomTabsIntent.Builder()
                .setShareState(CustomTabsIntent.SHARE_STATE_OFF)
                .build()
                .launchUrl(context, url.toUri())
        }
    }
}
