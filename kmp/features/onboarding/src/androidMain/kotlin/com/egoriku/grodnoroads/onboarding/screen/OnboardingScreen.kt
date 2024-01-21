package com.egoriku.grodnoroads.onboarding.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.egoriku.grodnoroads.foundation.uikit.button.PrimaryButton
import com.egoriku.grodnoroads.onboarding.domain.OnboardingComponent

@Composable
fun OnboardingScreen(onboardingComponent: OnboardingComponent) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.align(Alignment.Center)) {
            Text(text = "onboarding")
            PrimaryButton(onClick = onboardingComponent::finishOnboarding) {
                Text("open main flow")
            }
        }
    }
}