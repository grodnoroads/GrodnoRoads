package com.egoriku.grodnoroads.extensions

fun StringBuilder.appendIfNotEmpty(
    valueToCheck: String,
    vararg toAppend: String,
): StringBuilder = appendIf({ valueToCheck.isNotEmpty() }, *toAppend)

fun StringBuilder.appendIf(
    condition: () -> Boolean,
    vararg toAppend: String,
): StringBuilder = apply {
    if (condition()) toAppend.forEach { append(it) }
}