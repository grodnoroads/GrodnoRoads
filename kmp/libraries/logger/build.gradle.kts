import com.egoriku.grodnoroads.extension.setupIosTarget

plugins {
    id("grodnoroads.kmplibrary")
}

android {
    namespace = "com.egoriku.grodnoroads.logger"
}

kotlin {
    androidTarget()
    setupIosTarget(baseName = "logger")
}