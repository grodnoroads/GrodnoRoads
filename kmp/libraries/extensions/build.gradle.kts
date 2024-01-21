import com.egoriku.grodnoroads.extension.androidDependencies
import com.egoriku.grodnoroads.extension.commonDependencies
import com.egoriku.grodnoroads.extension.commonTestDependencies
import com.egoriku.grodnoroads.extension.setupIosTarget

plugins {
    id("grodnoroads.kmplibrary")
}

android {
    namespace = "com.egoriku.grodnoroads.extensions"
}

kotlin {
    androidTarget()
    setupIosTarget(baseName = "extensions")

    sourceSets {
        commonDependencies {
            implementation(libs.androidx.annotation)
            implementation(libs.kotlin.datetime)
        }
        androidDependencies {
            implementation(libs.androidx.appcompat)
        }
        commonTestDependencies {
            implementation(libs.kotlin.test)
        }
    }
}