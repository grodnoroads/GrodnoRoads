import com.egoriku.grodnoroads.extension.androidDependencies
import com.egoriku.grodnoroads.extension.commonDependencies
import com.egoriku.grodnoroads.extension.commonTestDependencies
import com.egoriku.grodnoroads.extension.iosTarget

plugins {
    id("grodnoroads.kmplibrary")
    id("grodnoroads.compose")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.egoriku.grodnoroads.guidance"
}

kotlin {
    androidTarget()
    iosTarget()

    sourceSets {
        commonDependencies {
            implementation(projects.kmp.features.specialEventReminder)
            implementation(projects.kmp.shared.crashlytics)
            implementation(projects.kmp.shared.geolocation)
            implementation(projects.kmp.shared.persistent)
            implementation(projects.kmp.libraries.coroutines)
            implementation(projects.kmp.libraries.extensions)
            implementation(projects.kmp.libraries.logger)
            implementation(projects.kmp.libraries.resources)
            implementation(projects.kmp.libraries.uuid)

            compileOnly(libs.compose.stable.marker)
            implementation(libs.decompose)
            implementation(libs.dev.gitlive.firebase.database)
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)
            implementation(libs.kotlin.collections)
            implementation(libs.kotlin.serialization.json)
            implementation(libs.mvikotlin)
            implementation(libs.mvikotlin.extensions)
            implementation(libs.mvikotlin.main)
        }
        androidDependencies {
            implementation(projects.kmp.shared.components)
            implementation(projects.compose.commonUi)
            implementation(projects.compose.foundation.core)
            implementation(projects.compose.foundation.preview)
            implementation(projects.compose.foundation.uikit)
            implementation(projects.compose.maps.compose)
            implementation(projects.compose.locationRequester)
            implementation(projects.compose.snackbar)
            implementation(projects.libraries.audioplayer)
            implementation(projects.libraries.resources)

            implementation(libs.androidx.activity.compose)

            implementation(libs.androidx.compose.foundation)
            implementation(libs.androidx.compose.material3)
            implementation(libs.androidx.compose.material.icons)

            implementation(libs.balloon.compose)
            implementation(libs.decompose.compose.jetpack)
            implementation(libs.google.maps)
            implementation(libs.google.maps.utils)

            implementation(libs.koin.compose)
        }
        commonTestDependencies {
            implementation(libs.kotlin.datetime)
            implementation(libs.kotlin.test)
        }
    }
}