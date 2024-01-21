package com.egoriku.grodnoroads.coroutines.flow.nullable

import kotlinx.coroutines.flow.StateFlow

/**
 * A wrapper for [StateFlow] with nullable value. Required for Swift interop.
 */
open class CNullableStateFlow<T : Any>(private val stateFlow: StateFlow<T?>) :
    StateFlow<T?> by stateFlow

fun <T : Any> StateFlow<T?>.toCNullableStateFlow() = CNullableStateFlow(this)