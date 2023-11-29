plugins {
    id("grodnoroads.library")
    id("grodnoroads.compose")
}

android {
    namespace = "com.egoriku.grodnoroads.compose.snakbar"
}

dependencies {
    implementation(projects.compose.foundation.core)
    implementation(projects.compose.foundation.theme)
    implementation(projects.compose.foundation.uikit)

    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui.tooling.preview)
    debugImplementation(libs.androidx.compose.ui.tooling)
}