package com.egoriku.grodnoroads.extensions

inline fun consume(f: () -> Unit): Boolean {
    f()
    return true
}

inline fun unConsume(f: () -> Unit): Boolean {
    f()
    return false
}