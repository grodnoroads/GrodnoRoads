package com.egoriku.grodnoroads.coroutines.flow

import kotlinx.coroutines.flow.MutableStateFlow

/**
 * A wrapper for [MutableStateFlow]. Required for Swift interop.
 */
@Suppress("DELEGATED_MEMBER_HIDES_SUPERTYPE_OVERRIDE")
class CMutableStateFlow<T : Any>(
    private val mutableStateFlow: MutableStateFlow<T>
) : CStateFlow<T>(mutableStateFlow), MutableStateFlow<T> by mutableStateFlow {
    constructor(initialValue: T) : this(MutableStateFlow(initialValue))
}