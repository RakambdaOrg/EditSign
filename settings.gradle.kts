rootProject.name = "EditSign"

pluginManagement {
	repositories {
		maven {
			name = "Fabric"
			url = uri("https://maven.fabricmc.net/")
		}
		maven {
			name = "MinecraftForge"
			url = uri("https://maven.minecraftforge.net")
		}
		maven{
			name = "SpongePowered"
			url = uri("https://repo.spongepowered.org/repository/maven-public/")
		}
		gradlePluginPortal()
	}
	resolutionStrategy {
		eachPlugin {
			if (requested.id.id == "net.minecraftforge.gradle") {
				useModule("${requested.id}:ForgeGradle:${requested.version}")
			} else if (requested.id.id == "org.spongepowered.mixin") {
				useModule("org.spongepowered:mixingradle:${requested.version}")
			}
		}
	}
}

val includeFabric: String by settings
val includeForge: String by settings

include("common")
if (includeFabric.toBoolean()) {
	include("fabric")
}
if (includeForge.toBoolean()) {
	include("forge")
}
