import com.egoriku.grodnoroads.extension.androidDependencies
import com.egoriku.grodnoroads.extension.commonDependencies
import com.egoriku.grodnoroads.extension.setupIosTarget

plugins {
    id("grodnoroads.kmplibrary")
    id("grodnoroads.compose")
}

android {
    namespace = "com.egoriku.grodnoroads.onboarding"
}

kotlin {
    androidTarget()
    setupIosTarget(baseName = "onboarding")

    sourceSets {
        commonDependencies {
            compileOnly(libs.compose.stable.marker)
            implementation(libs.decompose)
        }
        androidDependencies {
            implementation(projects.compose.foundation.uikit)

            implementation(libs.androidx.compose.material3)
            implementation(libs.decompose.compose.jetpack)
        }
    }
}