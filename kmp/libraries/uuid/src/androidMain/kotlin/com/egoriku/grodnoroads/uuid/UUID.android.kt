package com.egoriku.grodnoroads.uuid

import java.util.UUID

actual object Uuid {
    actual fun randomUUID() = UUID.randomUUID().toString()
}