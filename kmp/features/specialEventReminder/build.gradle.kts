import com.egoriku.grodnoroads.extension.androidDependencies
import com.egoriku.grodnoroads.extension.commonDependencies
import com.egoriku.grodnoroads.extension.commonTestDependencies
import com.egoriku.grodnoroads.extension.setupIosTarget

plugins {
    id("grodnoroads.kmplibrary")
    id("grodnoroads.compose")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.egoriku.grodnoroads.specialevent"
}

kotlin {
    androidTarget()
    setupIosTarget(baseName = "special_event")

    sourceSets {
        commonDependencies {
            implementation(projects.kmp.libraries.coroutines)
            implementation(projects.kmp.libraries.logger)

            implementation(libs.decompose)
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)
            implementation(libs.kotlin.coroutines)
            implementation(libs.kotlin.datetime)
            implementation(libs.mvikotlin)
            implementation(libs.mvikotlin.extensions)
            implementation(libs.mvikotlin.main)
        }
        androidDependencies {
            implementation(projects.compose.foundation.preview)
            implementation(projects.compose.commonUi)
            implementation(projects.libraries.resources)

            implementation(libs.androidx.compose.foundation)
            implementation(libs.androidx.compose.foundation)
            implementation(libs.androidx.compose.material3)
        }
        commonTestDependencies {
            implementation(libs.kotlin.test)
        }
    }
}