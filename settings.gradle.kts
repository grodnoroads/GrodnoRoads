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
    // https://stackoverflow.com/a/75032841
    repositoriesMode.set(RepositoriesMode.PREFER_PROJECT)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Grodno-Roads"

include(":app:android")
include(":app:ui-demo")

include(":compose:foundation:core")
include(":compose:foundation:preview")
include(":compose:foundation:theme")
include(":compose:foundation:uikit")

include(":compose:common-ui")
include(":compose:location-requester")
include(":compose:maps:compose")
include(":compose:snackbar")

include(":libraries:analytics")
include(":libraries:audioplayer")
include(":libraries:resources")

include(":kmp:features:root")
include(":kmp:features:onboarding")
include(":kmp:features:mainflow")

include(":kmp:features:guidance")
include(":kmp:features:specialEventReminder")

include(":kmp:features:tabs")

include(":kmp:features:appSettings")
include(":kmp:features:settings:alerts")
include(":kmp:features:settings:appearance")
include(":kmp:features:settings:changelog")
include(":kmp:features:settings:faq")
include(":kmp:features:settings:map")

include(":kmp:libraries:coroutines")
include(":kmp:libraries:datastore")
include(":kmp:libraries:extensions")
include(":kmp:libraries:location")
include(":kmp:libraries:logger")
include(":kmp:libraries:uuid")

include(":kmp:shared:components")
include(":kmp:shared:crashlytics")
include(":kmp:shared:geolocation")
include(":kmp:shared:models")
include(":kmp:shared:persistent")
