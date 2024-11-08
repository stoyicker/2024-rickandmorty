plugins {
  alias(libs.plugins.self.app)
}

android {
  buildTypes {
    release {
      signingConfig = signingConfigs.getByName("debug")
    }
  }
}

app {
  namespaceSuffix("app")
}
