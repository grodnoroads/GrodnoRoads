package com.egoriku.grodnoroads.extensions

import android.content.Context
import android.media.AudioManager

val Context.audioManager: AudioManager
    get() = getSystemService(Context.AUDIO_SERVICE) as AudioManager
