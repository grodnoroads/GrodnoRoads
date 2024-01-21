@file:Suppress("DELEGATED_MEMBER_HIDES_SUPERTYPE_OVERRIDE")

package com.egoriku.grodnoroads.coroutines.flow

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * A wrapper for [StateFlow]. Required for Swift interop.
 */
open class CStateFlow<T : Any>(private val stateFlow: StateFlow<T>) : CFlow<T>(stateFlow),
    StateFlow<T> by stateFlow

fun <T : Any> StateFlow<T>.toCStateFlow() = CStateFlow(this)

fun <T : Any> stateFlowOf(value: () -> T): CStateFlow<T> =
    MutableStateFlow(value = value()).toCStateFlow()