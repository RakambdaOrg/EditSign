enableFeaturePreview("VERSION_CATALOGS")

pluginManagement {
    repositories {
        jcenter()
        maven {
            name = "Fabric"
            url = uri("https://maven.fabricmc.net/")
        }
        gradlePluginPortal()
    }
}

rootProject.name = "EditSign"
include("common")
include("fabric")
//include("forge")