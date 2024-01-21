package com.egoriku.grodnoroads.uuid

import platform.Foundation.NSUUID

actual object Uuid {
    actual fun randomUUID(): String = NSUUID().UUIDString()
}