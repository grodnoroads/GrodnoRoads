plugins {
    id("grodnoroads.library")
    id("grodnoroads.compose")
}

android {
    namespace = "com.egoriku.grodnoroads.location.requester"
}

dependencies {
    implementation(projects.compose.foundation.core)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.appcompat)

    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.runtime)

    implementation(libs.kotlin.coroutines.playservices)

    implementation(libs.play.services.location)
}