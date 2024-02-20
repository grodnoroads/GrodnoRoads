package com.egoriku.grodnoroads.guidance.data.util

import android.content.Context
import com.egoriku.grodnoroads.resources.MR
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

actual object AreasGeoJsonAssetReader : KoinComponent {

    actual fun readAreasFile(): String {
        return MR.files.areas.readText(context = get<Context>())
    }
}