import com.egoriku.grodnoroads.extension.*

plugins {
    id("grodnoroads.application")
    id("grodnoroads.compose")
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.google.services)
    alias(libs.plugins.secrets)
}

android {
    namespace = "com.egoriku.grodnoroads"

    defaultConfig {
        applicationId = "com.egoriku.grodnoroads"

        minSdk = libs.versions.minSdk.get().toInt()
        compileSdk = libs.versions.compileSdk.get().toInt()
        targetSdk = libs.versions.compileSdk.get().toInt()

        versionCode = provideVersionCode("$projectDir/version.properties")
        versionName = provideVersionName("$projectDir/version.properties")
        resourceConfigurations += listOf("en", "ru", "be-rBY")
    }

    signingConfigs {
        debug {
            storeFile = loadKeystore("$rootDir/config/debug.keystore")
            storePassword = "android"
            keyAlias = "androiddebugkey"
            keyPassword = "android"
        }

        release {
            storeFile = loadKeystore("keystore.jks", "$rootDir/config/debug.keystore")
            storePassword = System.getenv("KEY_STORE_PASSWORD") ?: "android"
            keyAlias = System.getenv("KEY_ALIAS") ?: "androiddebugkey"
            keyPassword = System.getenv("KEY_PASSWORD") ?: "android"
        }
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
        }

        release {
            signingConfig = signingConfigs.getByName("release")
        }
    }

    buildFeatures {
        buildConfig = true
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(projects.kmp.features.root)
    implementation(projects.kmp.shared.crashlytics)
    implementation(projects.kmp.shared.persistent)
    implementation(projects.kmp.libraries.logger)

    implementation(projects.compose.foundation.core)
    implementation(projects.compose.foundation.theme)
    implementation(projects.libraries.analytics)
    implementation(projects.libraries.resources)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material3.windowsize)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.google.maps)
    implementation(libs.google.material)

    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.4")
}

secrets {
    propertiesFileName = "secrets.properties"
}