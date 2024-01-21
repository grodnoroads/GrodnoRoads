import com.egoriku.grodnoroads.extension.androidDependencies
import com.egoriku.grodnoroads.extension.commonDependencies
import com.egoriku.grodnoroads.extension.setupIosTarget

plugins {
    id("grodnoroads.kmplibrary")
}

android {
    namespace = "com.egoriku.grodnoroads.shared.persistent"
}

kotlin {
    androidTarget()
    setupIosTarget(baseName = "shared_persistent")

    sourceSets {
        commonDependencies {
            api(projects.kmp.libraries.datastore)
            implementation(projects.kmp.libraries.location)

            compileOnly(libs.compose.stable.marker)
        }
        androidDependencies {
            implementation(projects.libraries.resources)
        }
    }
}