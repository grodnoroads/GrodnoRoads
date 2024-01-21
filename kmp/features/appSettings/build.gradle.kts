import com.egoriku.grodnoroads.extension.androidDependencies
import com.egoriku.grodnoroads.extension.commonDependencies
import com.egoriku.grodnoroads.extension.setupIosTarget

plugins {
    id("grodnoroads.kmplibrary")
    id("grodnoroads.compose")
}

android {
    namespace = "com.egoriku.grodnoroads.appsettings"
}

kotlin {
    androidTarget()
    setupIosTarget(baseName = "appsettings")

    sourceSets {
        commonDependencies {
            implementation(projects.kmp.shared.components)
            implementation(projects.kmp.shared.models)

            compileOnly(libs.compose.stable.marker)
            implementation(libs.decompose)
        }
        androidDependencies {
            implementation(projects.compose.foundation.preview)
            implementation(projects.compose.foundation.uikit)
            implementation(projects.compose.commonUi)

            implementation(projects.libraries.resources)

            implementation(libs.androidx.browser)
            implementation(libs.androidx.core)
            implementation(libs.androidx.compose.foundation)
            implementation(libs.androidx.compose.material3)
            implementation(libs.androidx.compose.material.icons)
        }
    }
}