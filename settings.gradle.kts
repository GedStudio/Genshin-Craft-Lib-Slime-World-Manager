pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://papermc.io/repo/repository/maven-public/")
    }

}


rootProject.name = "slimeworldmanager"
include(":slimeworldmanager-api")
include(":slimeworldmanager-plugin")
include(":slimeworldmanager-clsm")
