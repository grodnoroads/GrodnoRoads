@file:Suppress("unused")

package com.egoriku.grodnoroads

import android.app.Application
import com.egoriku.grodnoroads.crashlytics.shared.config.CrashlyticsConfig
import com.egoriku.grodnoroads.logger.logD
import com.egoriku.grodnoroads.root.di.initKoin
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.MapsInitializer.Renderer
import com.google.android.gms.maps.OnMapsSdkInitializedCallback

class RoadsApplication : Application(), OnMapsSdkInitializedCallback {

    override fun onCreate() {
        super.onCreate()
        MapsInitializer.initialize(applicationContext, Renderer.LATEST, this)

        CrashlyticsConfig.isCollectionEnabled(!BuildConfig.DEBUG)
        initKoin(context = this)
    }

    override fun onMapsSdkInitialized(renderer: Renderer) {
        when (renderer) {
            Renderer.LATEST -> logD("The latest version of the renderer is used.")
            Renderer.LEGACY -> logD("The legacy version of the renderer is used.")
        }
    }
}