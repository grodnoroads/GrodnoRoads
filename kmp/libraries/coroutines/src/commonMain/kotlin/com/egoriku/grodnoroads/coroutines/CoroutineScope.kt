package com.egoriku.grodnoroads.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

fun CoroutineScope.reLaunch(
    job: Job?,
    block: suspend CoroutineScope.() -> Unit
): Job {
    job?.cancel()
    return launch(block = block)
}