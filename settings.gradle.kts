pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://papermc.io/repo/repository/maven-public/")
    }

}


rootProject.name = "gs-world"
include(":gs-world-api")
include(":gs-world-plugin")
include(":gs-world-clsm")
