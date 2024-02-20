package com.egoriku.grodnoroads.guidance.data.util

import com.egoriku.grodnoroads.resources.MR

actual object AreasGeoJsonAssetReader {

    actual fun readAreasFile() = MR.files.areas.readText()
}