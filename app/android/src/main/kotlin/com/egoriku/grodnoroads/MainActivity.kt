package com.egoriku.grodnoroads

import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.*
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.arkivanov.decompose.defaultComponentContext
import com.egoriku.grodnoroads.foundation.core.LocalActivity
import com.egoriku.grodnoroads.foundation.core.LocalWindowSizeClass
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsM3Theme
import com.egoriku.grodnoroads.root.domain.buildRootComponent
import com.egoriku.grodnoroads.root.screen.RootContent
import com.egoriku.grodnoroads.shared.persistent.appearance.Theme

// Don't use ComponentActivity, due to it breaks language change
class MainActivity : AppCompatActivity() {

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        val splash = installSplashScreen()
        splash.setKeepOnScreenCondition { true }
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val root = buildRootComponent(defaultComponentContext())
        setContent {
            val theme by root.theme.collectAsState()

            LaunchedEffect(theme) {
                if (theme != null) {
                    splash.setKeepOnScreenCondition { false }
                }
            }

            theme?.let {
                val isDarkTheme = when (it) {
                    Theme.System -> isSystemInDarkTheme()
                    Theme.Dark -> true
                    Theme.Light -> false
                }

                DisposableEffect(isDarkTheme) {
                    enableEdgeToEdge(
                        statusBarStyle = SystemBarStyle.auto(
                            lightScrim = android.graphics.Color.TRANSPARENT,
                            darkScrim = android.graphics.Color.TRANSPARENT,
                        ) { isDarkTheme },
                        navigationBarStyle = SystemBarStyle.auto(
                            lightScrim = android.graphics.Color.TRANSPARENT,
                            darkScrim = android.graphics.Color.TRANSPARENT,
                        ) { isDarkTheme },
                    )
                    onDispose {}
                }

                GrodnoRoadsM3Theme(isDarkTheme) {
                    CompositionLocalProvider(
                        LocalWindowSizeClass provides calculateWindowSizeClass(this),
                        LocalActivity provides this,
                    ) {
                        RootContent(root)
                    }
                }
            }
        }
    }
}