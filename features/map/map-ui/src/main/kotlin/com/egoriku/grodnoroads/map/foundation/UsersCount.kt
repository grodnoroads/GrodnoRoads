package com.egoriku.grodnoroads.map.foundation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsPreview
import com.egoriku.grodnoroads.foundation.theme.isLight
import com.egoriku.grodnoroads.foundation.theme.tonalElevation
import com.egoriku.grodnoroads.map.R
import com.skydoves.balloon.ArrowPositionRules
import com.skydoves.balloon.compose.Balloon
import com.skydoves.balloon.compose.rememberBalloonBuilder
import com.skydoves.balloon.compose.setBackgroundColor
import com.skydoves.balloon.compose.setTextColor
import com.egoriku.grodnoroads.resources.R as R_resources

@Composable
fun UsersCount(modifier: Modifier = Modifier, count: Int) {
    val bgColor = MaterialTheme.colorScheme.inversePrimary
    val textColor = MaterialTheme.colorScheme.onSurface
    val isLight = MaterialTheme.colorScheme.isLight

    val builder = rememberBalloonBuilder(isLight) {
        setArrowSize(7)
        setArrowPositionRules(ArrowPositionRules.ALIGN_ANCHOR)
        setPaddingTop(4)
        setPaddingBottom(4)
        setPaddingLeft(8)
        setPaddingRight(8)
        setMarginHorizontal(12)
        setCornerRadius(10f)
        setAlpha(0.9f)
        setBackgroundColor(bgColor)
        setTextColor(textColor)
    }

    Balloon(
        modifier = modifier,
        key = isLight,
        builder = builder,
        balloonContent = {
            Text(
                text = stringResource(id = R_resources.string.map_user_count_hint),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
    ) { balloonWindow ->
        UsersCountBadge(
            onClick = {
                balloonWindow.showAlignTop()
            },
            count = count
        )
    }
}

@Composable
private fun UsersCountBadge(count: Int, onClick: () -> Unit) {
    val shadowColor = when {
        MaterialTheme.colorScheme.isLight -> MaterialTheme.colorScheme.outline
        else -> Color.Black
    }
    Surface(
        tonalElevation = MaterialTheme.tonalElevation,
        shadowElevation = 0.dp,
        modifier = Modifier
            .shadow(
                elevation = 12.dp,
                shape = RoundedCornerShape(10.dp),
                ambientColor = shadowColor,
                spotColor = shadowColor
            )
            .clip(RoundedCornerShape(10.dp))
            .clickable { onClick() }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            modifier = Modifier.padding(end = 3.dp)
        ) {
            Image(
                modifier = Modifier.size(16.dp),
                painter = painterResource(id = R.drawable.ic_info),
                contentDescription = null
            )
            Text(
                text = stringResource(id = R_resources.string.map_user_count, count),
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}

@GrodnoRoadsPreview
@Composable
private fun UsersCountBadgePreview() = GrodnoRoadsM3ThemePreview {
    Box(modifier = Modifier.padding(16.dp)) {
        UsersCountBadge(count = 10, onClick = {})
    }
}