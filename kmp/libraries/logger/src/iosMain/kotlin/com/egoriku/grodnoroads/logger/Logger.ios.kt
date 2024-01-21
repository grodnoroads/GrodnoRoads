package com.egoriku.grodnoroads.logger

import platform.Foundation.NSLog

actual fun logD(message: String) {
    NSLog("%@: %@", TAG, message)
}