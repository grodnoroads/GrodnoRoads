import com.egoriku.grodnoroads.extension.commonDependencies
import com.egoriku.grodnoroads.extension.setupIosTarget

plugins {
    id("grodnoroads.kmplibrary")
}

android {
    namespace = "com.egoriku.grodnoroads.shared.crashlytics"
}

kotlin {
    androidTarget()
    setupIosTarget(baseName = "shared_crashlytics")

    sourceSets {
        commonDependencies {
            implementation(projects.kmp.libraries.logger)

            implementation(libs.dev.gitlive.firebase.crashlytics)

            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)
        }
    }
}