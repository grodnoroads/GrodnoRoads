package com.egoriku.grodnoroads.screen.root

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.FaultyDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.*
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.predictiveback.predictiveBackAnimation
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.arkivanov.decompose.router.slot.ChildSlot
import com.egoriku.grodnoroads.eventreporting.EventReportingScreen
import com.egoriku.grodnoroads.screen.main.MainUi
import com.egoriku.grodnoroads.screen.root.RoadsRootComponent.Child
import com.egoriku.grodnoroads.screen.root.store.headlamp.HeadLampType
import com.egoriku.grodnoroads.screen.root.ui.HeadLampDialog
import com.egoriku.grodnoroads.setting.alerts.AlertsScreen
import com.egoriku.grodnoroads.setting.appearance.screen.AppearanceScreen
import com.egoriku.grodnoroads.setting.changelog.screen.ChangelogScreen
import com.egoriku.grodnoroads.setting.faq.screen.FaqScreen
import com.egoriku.grodnoroads.setting.map.MapSettingsScreen

@OptIn(FaultyDecomposeApi::class, ExperimentalDecomposeApi::class)
@Composable
fun RootContent(rootComponent: RoadsRootComponent) {
    Surface(modifier = Modifier.fillMaxSize()) {
        val dialogState by rootComponent.headlampDialogState.collectAsState(initial = HeadLampType.None)

        if (dialogState != HeadLampType.None) {
            HeadLampDialog(
                headlampType = dialogState,
                onClose = rootComponent::closeHeadlampDialog
            )
        }
        Box {
            Children(
                stack = rootComponent.childStack,
                animation = predictiveBackAnimation(
                    backHandler = rootComponent.backHandler,
                    animation = stackAnimation { _, _, direction ->
                        if (direction.isFront) {
                            slide() + fade()
                        } else {
                            scale(frontFactor = 1F, backFactor = 0.7F) + fade()
                        }
                    },
                    onBack = rootComponent::onBack,
                ),

                ) {
                when (val child = it.instance) {
                    is Child.Main -> MainUi(component = child.component)
                    is Child.Appearance -> AppearanceScreen(
                        appearanceComponent = child.appearanceComponent,
                        onBack = rootComponent::onBack
                    )

                    is Child.Map -> MapSettingsScreen(
                        mapSettingsComponent = child.mapSettingsComponent,
                        onBack = rootComponent::onBack
                    )

                    is Child.Alerts -> AlertsScreen(
                        alertsComponent = child.alertsComponent,
                        onBack = rootComponent::onBack
                    )

                    is Child.Changelog -> ChangelogScreen(
                        changelogComponent = child.changelogComponent,
                        onBack = rootComponent::onBack,
                    )

                    is Child.NextFeatures -> TODO()
                    is Child.BetaFeatures -> TODO()
                    is Child.FAQ -> FaqScreen(
                        faqComponent = child.faqComponent,
                        onBack = rootComponent::onBack
                    )
                }
            }

            val childSlot by rootComponent.childSlot.subscribeAsState()
            childSlot.onChild {
                EventReportingScreen(
                    onClose = rootComponent::closeReporting,
                    onReport = rootComponent::processReporting
                )
            }
        }
    }
}

inline fun <T : Any> ChildSlot<*, T>?.onChild(action: (T) -> Unit) {
    val instance = this?.child?.instance
    if (instance != null) {
        action(instance)
    }
}