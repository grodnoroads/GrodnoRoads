package com.egoriku.grodnoroads.specialevent.domain.component.dialog

import com.egoriku.grodnoroads.specialevent.domain.model.EventType

interface DialogComponent {

    val eventType: EventType

    fun dismiss()
}