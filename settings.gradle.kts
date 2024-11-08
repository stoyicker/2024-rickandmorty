buildCache.local.isEnabled = !System.getenv().containsKey("CI")

rootProject.name = "2024-rickandmorty"

pluginManagement {
  includeBuild("buildlogic")
  repositories {
    google()
    gradlePluginPortal()
    mavenCentral()
  }
}

include(":app")

dependencyResolutionManagement {
  repositories {
    google()
    mavenCentral()
  }
}

plugins {
  id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
