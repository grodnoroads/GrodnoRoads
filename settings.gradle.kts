enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        includeBuild("build-logic")
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Grodno-Roads"

include(":app")

include(":features:map:map-data")
include(":features:map:map-domain")
include(":features:map:map-ui")

include(":features:settings")

include(":features:setting:alerts")
include(":features:setting:appearance")
include(":features:setting:faq")
include(":features:setting:map")
include(":features:setting:whatsnew")

include(":compose:snackbar")
include(":compose:foundation-core")

include(":libraries:analytics")
include(":libraries:audioplayer")
include(":libraries:crashlytics")
include(":libraries:extensions")
include(":libraries:foundation")
include(":libraries:location")
include(":libraries:location-permissions")

include(":libraries:maps:compose")
include(":libraries:maps:core")

include(":libraries:resources")

include(":shared:appSettings")
include(":shared:appComponent")