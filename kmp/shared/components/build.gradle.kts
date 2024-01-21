import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING
import com.egoriku.grodnoroads.extension.provideVersionName
import com.egoriku.grodnoroads.extension.setupIosTarget

plugins {
    id("grodnoroads.kmplibrary")
    alias(libs.plugins.buildkonfig)
}

android {
    namespace = "com.egoriku.grodnoroads.shared.components"
}

kotlin {
    androidTarget()
    setupIosTarget(baseName = "components")
}

buildkonfig {
    packageName = "com.egoriku.grodnoroads.shared"
    objectName = "AppBuildConfig"
    exposeObjectWithName = objectName

    defaultConfigs {
        buildConfigField(
            type = STRING,
            name = "versionName",
            value = provideVersionName("$rootDir/app/android/version.properties")
        )
    }
}
