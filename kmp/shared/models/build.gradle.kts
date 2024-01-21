import com.egoriku.grodnoroads.extension.setupIosTarget

plugins {
    id("grodnoroads.kmplibrary")
}

android {
    namespace = "com.egoriku.grodnoroads.shared.models"
}

kotlin {
    androidTarget()
    setupIosTarget(baseName = "shared_models")
}