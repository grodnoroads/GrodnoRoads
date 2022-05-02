package com.egoriku.grodnoroads.screen.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Forum
import androidx.compose.ui.graphics.vector.ImageVector
import com.egoriku.grodnoroads.R

sealed class Screen {
    abstract val index: Int
    abstract val icon: ImageVector
    abstract val labelId: Int

    data class Map(
        override val index: Int = 0,
        override val icon: ImageVector = Icons.Default.Explore,
        override val labelId: Int = R.string.tab_map
    ) : Screen()

    data class Chat(
        override val index: Int = 1,
        override val icon: ImageVector = Icons.Default.Forum,
        override val labelId: Int = R.string.tab_chat
    ) : Screen()
}