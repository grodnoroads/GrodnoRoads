package com.egoriku.grodnoroads.coroutines.flow

import kotlinx.coroutines.flow.Flow

/**
 * A wrapper for [Flow]. Required for Swift interop.
 */
open class CFlow<T : Any>(private val flow: Flow<T>) : Flow<T> by flow

fun <T : Any> Flow<T>.toCFlow() = CFlow(this)

fun <T : Any> flowOf(element: T): CFlow<T> = kotlinx.coroutines.flow.flowOf(element).toCFlow()