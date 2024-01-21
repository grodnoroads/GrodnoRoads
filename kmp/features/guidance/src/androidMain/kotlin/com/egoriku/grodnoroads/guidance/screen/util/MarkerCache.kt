package com.egoriku.grodnoroads.guidance.screen.util

import android.content.Context
import android.util.LruCache
import androidx.core.graphics.drawable.toBitmap
import com.egoriku.grodnoroads.extensions.drawableCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

class MarkerCache(private val context: Context) {

    private val maxSize = (Runtime.getRuntime().maxMemory() / 1024 / 8).toInt()
    private val lruCache = LruCache<Int, BitmapDescriptor>(maxSize)

    fun getIcon(id: Int): BitmapDescriptor {
        return when (val cachedBitmap = lruCache.get(id)) {
            null -> {
                val bitmap = requireNotNull(context.drawableCompat(id)).toBitmap()

                BitmapDescriptorFactory.fromBitmap(bitmap).also { bitmapDescriptor ->
                    lruCache.put(id, bitmapDescriptor)
                }
            }
            else -> cachedBitmap
        }
    }
}