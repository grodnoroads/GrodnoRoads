import com.egoriku.grodnoroads.extension.androidDependencies
import com.egoriku.grodnoroads.extension.commonDependencies
import com.egoriku.grodnoroads.extension.setupIosTarget

plugins {
    id("grodnoroads.kmplibrary")
    id("grodnoroads.compose")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.egoriku.grodnoroads.root"
}

kotlin {
    androidTarget()
    setupIosTarget(baseName = "root") {
        export(libs.decompose)
        export(libs.essenty.lifecycle)

        export(projects.kmp.features.appSettings)
        export(projects.kmp.features.guidance)
        export(projects.kmp.features.mainflow)
        export(projects.kmp.features.onboarding)
        export(projects.kmp.features.settings.alerts)
        export(projects.kmp.features.settings.appearance)
        export(projects.kmp.features.settings.changelog)
        export(projects.kmp.features.settings.faq)
        export(projects.kmp.features.settings.map)
        export(projects.kmp.features.tabs)
        export(projects.kmp.libraries.coroutines)
        export(projects.kmp.libraries.location)
    }

    sourceSets {
        commonDependencies {
            api(projects.kmp.features.appSettings)
            api(projects.kmp.features.guidance)
            api(projects.kmp.features.mainflow)
            api(projects.kmp.features.onboarding)
            api(projects.kmp.features.settings.alerts)
            api(projects.kmp.features.settings.appearance)
            api(projects.kmp.features.settings.changelog)
            api(projects.kmp.features.settings.faq)
            api(projects.kmp.features.settings.map)
            api(projects.kmp.features.tabs)
            implementation(projects.kmp.shared.geolocation)
            api(projects.kmp.libraries.coroutines)
            api(projects.kmp.libraries.location)
            implementation(projects.kmp.libraries.crashlytics)
            implementation(projects.kmp.libraries.datastore)

            api(libs.decompose)
            implementation(libs.dev.gitlive.firebase.crashlytics)
            implementation(libs.dev.gitlive.firebase.database)
            implementation(libs.dev.gitlive.firebase.firestore)

            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)

            implementation(libs.mvikotlin)
            implementation(libs.mvikotlin.main)
        }
        androidDependencies {
            implementation(projects.features.map.mapUi)
            implementation(projects.compose.foundation.uikit)

            implementation(libs.androidx.compose.material3)
            implementation(libs.decompose.compose.jetpack)
            implementation(libs.koin.android)
        }
    }
}