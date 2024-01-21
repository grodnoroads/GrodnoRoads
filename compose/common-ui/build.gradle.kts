plugins {
    id("grodnoroads.library")
    id("grodnoroads.compose")
}

android {
    namespace = "com.egoriku.grodnoroads.foundation.common.ui"
}

dependencies {
    implementation(projects.compose.foundation.core)
    implementation(projects.compose.foundation.preview)
    implementation(projects.compose.foundation.uikit)

    implementation(projects.libraries.resources)

    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.icons)

    implementation(libs.kotlin.collections)
}