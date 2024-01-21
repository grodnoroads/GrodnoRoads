import com.egoriku.grodnoroads.extension.commonDependencies
import com.egoriku.grodnoroads.extension.setupIosTarget

plugins {
    id("grodnoroads.kmplibrary")
}

android {
    namespace = "com.egoriku.grodnoroads.coroutines"
}

kotlin {
    androidTarget()
    setupIosTarget(baseName = "coroutines")

    sourceSets {
        commonDependencies {
            implementation(libs.decompose)
            api(libs.kotlin.coroutines)
        }
    }
}