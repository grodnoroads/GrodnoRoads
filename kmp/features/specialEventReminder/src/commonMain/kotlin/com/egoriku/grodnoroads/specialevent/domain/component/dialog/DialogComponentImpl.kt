package com.egoriku.grodnoroads.specialevent.domain.component.dialog

import com.arkivanov.decompose.ComponentContext
import com.egoriku.grodnoroads.specialevent.domain.model.EventType

internal fun buildDialogComponent(
    componentContext: ComponentContext,
    eventType: EventType,
    onDismissed: () -> Unit
): DialogComponent = DialogComponentImpl(
    componentContext = componentContext,
    eventType = eventType,
    onDismissed = onDismissed
)

internal class DialogComponentImpl(
    componentContext: ComponentContext,
    override val eventType: EventType,
    private val onDismissed: () -> Unit
) : DialogComponent, ComponentContext by componentContext {

    override fun dismiss() = onDismissed()
}