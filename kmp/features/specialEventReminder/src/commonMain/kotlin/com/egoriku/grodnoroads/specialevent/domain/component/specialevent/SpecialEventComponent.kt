package com.egoriku.grodnoroads.specialevent.domain.component.specialevent

import com.arkivanov.decompose.router.slot.ChildSlot
import com.egoriku.grodnoroads.coroutines.flow.CStateFlow
import com.egoriku.grodnoroads.specialevent.domain.component.dialog.DialogComponent

interface SpecialEventComponent {

    val specialEvents: CStateFlow<ChildSlot<*, DialogComponent>>
}