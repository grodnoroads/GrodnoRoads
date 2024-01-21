plugins {
    id("grodnoroads.library")
    id("grodnoroads.compose")
}

android {
    namespace = "com.egoriku.grodnoroads.compose.snackbar"
}

dependencies {
    implementation(projects.compose.foundation.core)
    implementation(projects.compose.foundation.preview)
    implementation(projects.compose.foundation.uikit)
    implementation(projects.kmp.libraries.extensions)

    implementation(libs.androidx.compose.material3)
}