package com.egoriku.grodnoroads.mainflow.screen

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.router.stack.ChildStack
import com.egoriku.grodnoroads.appsettings.screen.AppSettingsScreen
import com.egoriku.grodnoroads.foundation.core.LocalWindowSizeClass
import com.egoriku.grodnoroads.foundation.core.animation.HorizontalSlideAnimatedVisibility
import com.egoriku.grodnoroads.foundation.core.animation.VerticalSlideAnimatedVisibility
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.uikit.NavigationBar
import com.egoriku.grodnoroads.foundation.uikit.NavigationBarItem
import com.egoriku.grodnoroads.foundation.uikit.NavigationRail
import com.egoriku.grodnoroads.foundation.uikit.NavigationRailItem
import com.egoriku.grodnoroads.guidance.screen.GuidanceScreen
import com.egoriku.grodnoroads.mainflow.TabsComponent
import com.egoriku.grodnoroads.mainflow.TabsComponent.Child
import kotlinx.collections.immutable.persistentListOf

private val NavigationBarHeight: Dp = 80.dp

@Composable
fun TabsScreen(tabsComponent: TabsComponent) {
    val windowSizeClass = LocalWindowSizeClass.current

    val childStack by tabsComponent.childStack.collectAsState()

    when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Expanded -> {
            HorizontalOrientationLayout(
                childStack = { childStack },
                onSelectTab = tabsComponent::onSelectTab,
                activeIndex = { childStack.active.instance.index }
            )
        }
        else -> {
            VerticalOrientationLayout(
                childStack = { childStack },
                onSelectTab = tabsComponent::onSelectTab,
                activeIndex = { childStack.active.instance.index }
            )
        }
    }
}

@Composable
private fun VerticalOrientationLayout(
    childStack: () -> ChildStack<*, Child>,
    activeIndex: () -> Int,
    onSelectTab: (Int) -> Unit,
) {
    val bottomNavItems = remember { persistentListOf(Screen.Map, Screen.AppSettings) }
    var isShowBottomBar by rememberMutableState { true }

    val bottomPadding by animateDpAsState(
        targetValue = if (isShowBottomBar) NavigationBarHeight else 0.dp,
        label = "bottomPadding"
    )

    val contentPaddingValues = WindowInsets
        .navigationBars
        .add(WindowInsets(bottom = bottomPadding))
        .asPaddingValues()

    Box(modifier = Modifier.fillMaxSize()) {
        Children(
            modifier = Modifier.fillMaxSize(),
            stack = childStack(),
        ) { created ->
            when (val child = created.instance) {
                is Child.Guidance -> {
                    GuidanceScreen(
                        contentPadding = contentPaddingValues,
                        component = child.component,
                        onBottomNavigationVisibilityChange = { isShowBottomBar = it }
                    )
                }
                is Child.AppSettings -> {
                    AppSettingsScreen(
                        contentPadding = contentPaddingValues,
                        settingsComponent = child.component
                    )
                }
            }
        }

        VerticalSlideAnimatedVisibility(
            visible = isShowBottomBar,
            modifier = Modifier.align(Alignment.BottomStart)
        ) {
            NavigationBar {
                bottomNavItems.forEach { screen ->
                    NavigationBarItem(
                        selected = screen.index == activeIndex(),
                        onClick = { onSelectTab(screen.index) },
                        icon = {
                            Icon(
                                painter = painterResource(id = screen.iconRes),
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

@Composable
private fun HorizontalOrientationLayout(
    childStack: () -> ChildStack<*, Child>,
    activeIndex: () -> Int,
    onSelectTab: (Int) -> Unit,
) {
    val bottomNavItems = remember { persistentListOf(Screen.Map, Screen.AppSettings) }
    var isHideBottomBar by rememberMutableState { true }

    val leftPadding by animateDpAsState(
        targetValue = if (isHideBottomBar) NavigationBarHeight else 0.dp,
        label = "leftPadding"
    )
    val contentPaddingValues = WindowInsets(left = leftPadding)
        .add(WindowInsets.systemBars.only(WindowInsetsSides.Vertical))
        .asPaddingValues()

    Box(modifier = Modifier.fillMaxSize()) {
        Children(
            modifier = Modifier.fillMaxSize(),
            stack = childStack(),
        ) { created ->
            when (val child = created.instance) {
                is Child.Guidance ->
                    GuidanceScreen(
                        contentPadding = contentPaddingValues,
                        component = child.component,
                        onBottomNavigationVisibilityChange = { isHideBottomBar = it }
                    )
                is Child.AppSettings -> {
                    AppSettingsScreen(
                        contentPadding = contentPaddingValues,
                        settingsComponent = child.component
                    )
                }
            }
        }

        HorizontalSlideAnimatedVisibility(isHideBottomBar) {
            NavigationRail {
                bottomNavItems.forEach { screen ->
                    NavigationRailItem(
                        selected = screen.index == activeIndex(),
                        onClick = { onSelectTab(screen.index) },
                        icon = {
                            Icon(
                                painter = painterResource(id = screen.iconRes),
                                contentDescription = null
                            )
                        }
                    )
                }
            }
        }
    }
}