import com.egoriku.grodnoroads.extension.androidDependencies
import com.egoriku.grodnoroads.extension.commonDependencies
import com.egoriku.grodnoroads.extension.setupIosTarget

plugins {
    id("grodnoroads.kmplibrary")
}

android {
    namespace = "com.egoriku.grodnoroads.location"
}

kotlin {
    androidTarget()
    setupIosTarget(baseName = "location")

    sourceSets {
        commonDependencies {
            compileOnly(libs.compose.stable.marker)
        }
        androidDependencies {
            implementation(libs.play.services.maps)
        }
    }
}