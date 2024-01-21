import com.egoriku.grodnoroads.extension.androidDependencies
import com.egoriku.grodnoroads.extension.commonDependencies
import com.egoriku.grodnoroads.extension.setupIosTarget

plugins {
    id("grodnoroads.kmplibrary")
}

android {
    namespace = "com.egoriku.grodnoroads.datastore"
}

kotlin {
    androidTarget()
    setupIosTarget(baseName = "datastore")

    sourceSets {
        commonDependencies {
            api(libs.androidx.datastore.core)
        }
        androidDependencies {
            api(libs.androidx.datastore)
        }
    }
}