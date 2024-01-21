package com.egoriku.grodnoroads.logger

import android.util.Log

actual fun logD(message: String) {
    Log.d(TAG, message)
}