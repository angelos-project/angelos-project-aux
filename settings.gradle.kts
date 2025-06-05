pluginManagement {
    repositories {
        google()
        mavenCentral()
        mavenLocal()
        gradlePluginPortal()
        //maven(url = "./local-repo")
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        mavenLocal()
    }
}

rootProject.name = "angelos-project-aux"

include(":library")
include(":examples")
include(":jazzer")