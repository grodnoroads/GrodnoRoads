@file:Suppress("NOTHING_TO_INLINE")
package com.egoriku.grodnoroads.extensions

import android.content.Context
import androidx.appcompat.content.res.AppCompatResources

inline fun Context.drawableCompat(id: Int) = AppCompatResources.getDrawable(this, id)