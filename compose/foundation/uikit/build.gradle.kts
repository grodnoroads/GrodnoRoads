plugins {
    id("grodnoroads.library")
    id("grodnoroads.compose")
}

android {
    namespace = "com.egoriku.grodnoroads.foundation.uikit"
}

dependencies {
    implementation(projects.compose.foundation.core)
    implementation(projects.compose.foundation.preview)

    implementation(libs.androidx.compose.material.icons)
    implementation(libs.androidx.compose.material3)
}