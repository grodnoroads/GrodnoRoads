package com.egoriku.grodnoroads.foundation.core

import androidx.activity.ComponentActivity
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf

val LocalActivity = staticCompositionLocalOf<ComponentActivity> {
    error("no any LocalActivity in composition")
}

val LocalWindowSizeClass = compositionLocalOf<WindowSizeClass> { error("SizeClass not present") }