plugins {
  `kotlin-dsl`
}

dependencies {
  compileOnly(gradleApi())
  implementation(libs.plugin.android.build)
  implementation(libs.plugin.kotlin)
}

gradlePlugin {
  plugins {
    register("self-app") {
      id = libs.plugins.self.app.get().pluginId
      version = libs.plugins.self.app.get().version
      implementationClass = "self.AppPlugin"
    }
  }
}
