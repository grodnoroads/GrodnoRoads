import com.egoriku.grodnoroads.extension.debug
import com.egoriku.grodnoroads.extension.loadKeystore
import com.egoriku.grodnoroads.extension.release

plugins {
    id("grodnoroads.application")
    id("grodnoroads.compose")
}

android {
    namespace = "com.egoriku.grodnoroads.uidemo"

    defaultConfig {
        applicationId = "com.egoriku.grodnoroads.uidemo"

        minSdk = libs.versions.minSdk.get().toInt()
        compileSdk = libs.versions.compileSdk.get().toInt()
        targetSdk = libs.versions.compileSdk.get().toInt()

        versionCode = 1
        versionName = "1.0.0"
    }

    signingConfigs {
        debug {
            storeFile = loadKeystore("$rootDir/config/debug.keystore")
            storePassword = "android"
            keyAlias = "androiddebugkey"
            keyPassword = "android"
        }

        release {
            storeFile = loadKeystore("$rootDir/config/debug.keystore")
            storePassword = "android"
            keyAlias = "androiddebugkey"
            keyPassword = "android"
        }
    }

    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
        }
    }
}

dependencies {
    implementation(projects.compose.foundation.core)
    implementation(projects.compose.foundation.preview)
    implementation(projects.compose.foundation.uikit)
    implementation(projects.compose.snackbar)
    implementation(projects.kmp.libraries.extensions)

    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.icons)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.ui)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.appcompat)
    implementation(libs.google.material)
}