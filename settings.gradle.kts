pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "notifykt"
include(
    ":notifykt-common",
    ":notifykt-android",
    ":notifykt-ios",
    ":notifykt-mobile",
)
include(":sample:composeApp")
