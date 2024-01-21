package com.egoriku.grodnoroads.onboarding.domain

import com.arkivanov.decompose.ComponentContext

fun buildOnboardingComponent(
    componentContext: ComponentContext,
    onFinishOnboarding: () -> Unit,
): OnboardingComponent = OnboardingComponentImpl(
    componentContext = componentContext,
    onFinishOnboarding = onFinishOnboarding
)

internal class OnboardingComponentImpl(
    componentContext: ComponentContext,
    private val onFinishOnboarding: () -> Unit
) : OnboardingComponent, ComponentContext by componentContext {

    override fun finishOnboarding() = onFinishOnboarding()
}