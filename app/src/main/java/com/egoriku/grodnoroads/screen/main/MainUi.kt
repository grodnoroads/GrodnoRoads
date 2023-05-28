package com.egoriku.grodnoroads.screen.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.egoriku.grodnoroads.foundation.animation.VerticalSlideAnimatedVisibility
import com.egoriku.grodnoroads.foundation.bottombar.BottomBarVisibilityState
import com.egoriku.grodnoroads.foundation.bottombar.LocalBottomBarVisibilityController
import com.egoriku.grodnoroads.map.MapScreen
import com.egoriku.grodnoroads.screen.main.MainComponent.Child
import com.egoriku.grodnoroads.settings.root.screen.SettingsScreen

@Composable
fun MainUi(component: MainComponent) {
    val bottomNavItems = listOf(Screen.Map, Screen.Settings)

    val childStack by component.childStack.subscribeAsState()

    var bottomBarVisibility by rememberSaveable { mutableStateOf(BottomBarVisibilityState.SHOWN) }
    val onChangeBottomBarVisibility =
        remember { { visibility: BottomBarVisibilityState -> bottomBarVisibility = visibility } }

    Column(modifier = Modifier.fillMaxSize()) {
        CompositionLocalProvider(
            LocalBottomBarVisibilityController provides onChangeBottomBarVisibility
        ) {
            Children(
                modifier = Modifier.weight(1f),
                stack = childStack,
            ) { created ->
                when (val child = created.instance) {
                    is Child.Map -> MapScreen(component = child.component)
                    is Child.Settings -> SettingsScreen(settingsComponent = child.component)
                }
            }
        }

        VerticalSlideAnimatedVisibility(visible = bottomBarVisibility.isShown()) {
            NavigationBar(tonalElevation = 1.dp) {
                bottomNavItems.forEach { screen ->
                    NavigationBarItem(
                        selected = screen.index == childStack.active.instance.index,
                        onClick = { component.onSelectTab(index = screen.index) },
                        icon = {
                            Icon(
                                painter = rememberVectorPainter(image = screen.icon),
                                contentDescription = null
                            )
                        },
                        label = {
                            Text(text = stringResource(id = screen.labelId))
                        }
                    )
                }
            }
        }
    }
}