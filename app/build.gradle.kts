plugins {
  alias(libs.plugins.apollo)
  alias(libs.plugins.self.app)
}

android {
  buildTypes {
    release {
      signingConfig = signingConfigs.getByName("debug")
    }
  }
}

apollo {
  service("rickandmortyapi") {
    failOnWarnings = true
    generateOptionalOperationVariables = false
    packageName = "rickandmortyapi"
    warnOnDeprecatedUsages = true
  }
}

app {
  namespaceSuffix("app")
}

dependencies {
  implementation(libs.bundles.apollo)
}
