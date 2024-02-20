package com.egoriku.grodnoroads.logger

actual fun logD(message: String) {
    print("$TAG: $message")
}