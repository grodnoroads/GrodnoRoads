package com.egoriku.grodnoroads.appsettings.screen.ui.section

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ContactSupport
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.appsettings.screen.ui.SocialNetwork
import com.egoriku.grodnoroads.appsettings.screen.util.rememberCustomTabIntent
import com.egoriku.grodnoroads.appsettings.screen.util.rememberShareIntent
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsPreview
import com.egoriku.grodnoroads.resources.R

@Composable
fun SocialNetworkSection() {
    val customTabsIntent = rememberCustomTabIntent()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        val chatUrl = stringResource(R.string.tg_chat_link)
        val channelUrl = stringResource(R.string.tg_channel_link)
        val playStoreUrl = stringResource(R.string.play_store_link)

        SocialNetwork(
            title = stringResource(R.string.social_telegram_chat),
            onClick = { customTabsIntent(chatUrl) }
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Default.ContactSupport,
                contentDescription = null
            )
        }
        SocialNetwork(
            title = stringResource(R.string.social_telegram_channel),
            onClick = { customTabsIntent(channelUrl) }
        ) {
            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .padding(end = 2.dp),
                painter = painterResource(id = R.drawable.ic_telegram_logo_minimal),
                contentDescription = null
            )
        }

        val shareIntent = rememberShareIntent()

        SocialNetwork(
            title = stringResource(R.string.social_share_app),
            onClick = { shareIntent(playStoreUrl) }
        ) {
            Icon(
                modifier = Modifier.padding(end = 2.dp),
                imageVector = Icons.Default.Share,
                contentDescription = null
            )
        }
    }
}

@GrodnoRoadsPreview
@Composable
private fun SocialNetworkSectionPreview() = GrodnoRoadsM3ThemePreview {
    SocialNetworkSection()
}