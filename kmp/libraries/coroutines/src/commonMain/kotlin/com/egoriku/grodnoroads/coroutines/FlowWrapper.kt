package com.egoriku.grodnoroads.coroutines

import com.arkivanov.decompose.Cancellation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

open class FlowWrapper<T>(private val flow: Flow<T>) {

    fun collect(consumer: (T) -> Unit): Cancellation {
        val scope = CoroutineScope(Dispatchers.Main.immediate)

        flow
            .onEach { consumer(it) }
            .launchIn(scope)

        return Cancellation { scope.cancel() }
    }
}