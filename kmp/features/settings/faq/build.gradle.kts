import com.egoriku.grodnoroads.extension.androidDependencies
import com.egoriku.grodnoroads.extension.commonDependencies
import com.egoriku.grodnoroads.extension.setupIosTarget

plugins {
    id("grodnoroads.kmplibrary")
    id("grodnoroads.compose")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.egoriku.grodnoroads.settings.faq"
}

kotlin {
    androidTarget()
    setupIosTarget(baseName = "settings_faq")

    sourceSets {
        commonDependencies {
            implementation(projects.kmp.shared.crashlytics)
            implementation(projects.kmp.libraries.coroutines)
            implementation(projects.kmp.libraries.extensions)

            compileOnly(libs.compose.stable.marker)
            implementation(libs.decompose)
            implementation(libs.dev.gitlive.firebase.firestore)

            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)
            implementation(libs.kotlin.collections)

            implementation(libs.mvikotlin)
            implementation(libs.mvikotlin.extensions)
            implementation(libs.mvikotlin.main)
        }
        androidDependencies {
            implementation(projects.compose.foundation.preview)
            implementation(projects.compose.commonUi)

            implementation(projects.libraries.resources)

            implementation(libs.androidx.compose.foundation)
            implementation(libs.androidx.compose.material3)
        }
    }
}