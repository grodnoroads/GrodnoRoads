package com.egoriku.grodnoroads.coroutines.flow.nullable

import kotlinx.coroutines.flow.MutableStateFlow

/**
 * A wrapper for [MutableStateFlow] with nullable value. Required for Swift interop.
 */
@Suppress("DELEGATED_MEMBER_HIDES_SUPERTYPE_OVERRIDE")
class CNullableMutableStateFlow<T : Any>(private val mutableStateFlow: MutableStateFlow<T?>) :
    CNullableStateFlow<T>(mutableStateFlow), MutableStateFlow<T?> by mutableStateFlow {
    constructor(initialValue: T?) : this(MutableStateFlow(initialValue))
}