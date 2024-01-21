package com.egoriku.grodnoroads.coroutines

import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.LifecycleOwner
import com.arkivanov.essenty.lifecycle.doOnDestroy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.coroutines.CoroutineContext

fun LifecycleOwner.coroutineScope(
    context: CoroutineContext = Dispatchers.Main.immediate,
): CoroutineScope {
    val scope = CoroutineScope(context + SupervisorJob())
    lifecycle.doOnDestroy(scope::cancel)

    return scope
}

/**
 * Converts decompose [Value] to coroutines [StateFlow]
 */
fun <T : Any> Value<T>.toStateFlow(): StateFlow<T> = ValueStateFlow(this)

private class ValueStateFlow<out T : Any>(private val source: Value<T>) : StateFlow<T> {

    override val value: T
        get() = source.value

    override val replayCache: List<T>
        get() = listOf(source.value)

    override suspend fun collect(collector: FlowCollector<T>): Nothing {
        val flow = MutableStateFlow(source.value)
        val disposable = source.observe { flow.value = it }

        try {
            flow.collect(collector)
        } finally {
            disposable.cancel()
        }
    }
}

inline fun <T : Any> ChildSlot<*, T>?.onChild(action: (T) -> Unit) {
    val instance = this?.child?.instance
    if (instance != null) {
        action(instance)
    }
}