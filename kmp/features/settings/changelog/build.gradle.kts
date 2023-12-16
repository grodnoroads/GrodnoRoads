plugins {
    id("grodnoroads.kmplibrary")
    id("grodnoroads.compose")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.egoriku.grodnoroads.settings.changelog"
}

kotlin {
    androidTarget()
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "settings_changelog"
            isStatic = true
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.kmp.libraries.extensions)

                implementation(libs.decompose)
                implementation(libs.dev.gitlive.firebase.firestore)

                implementation(project.dependencies.platform(libs.koin.bom))
                implementation(libs.koin.core)

                implementation(libs.kotlin.coroutines.core)
                implementation(libs.mvikotlin)
                implementation(libs.mvikotlin.extensions)
                implementation(libs.mvikotlin.main)
            }
        }
        commonTest {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }
        androidMain {
            dependencies {
                implementation(projects.compose.foundation.preview)
                implementation(projects.compose.foundation.theme)
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
}