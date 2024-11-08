plugins {
  alias(libs.plugins.apollo)
  alias(libs.plugins.compose.compiler)
  alias(libs.plugins.hilt)
  alias(libs.plugins.ksp)
  alias(libs.plugins.self.app)
}

android {
  buildFeatures.compose = true
  buildTypes {
    release {
      signingConfig = signingConfigs.getByName("debug")
    }
  }
  composeCompiler {
    reportsDestination = layout.buildDirectory.dir("compose_compiler")
    metricsDestination = layout.buildDirectory.dir("compose_compiler")
  }
  kotlinOptions {
    freeCompilerArgs += "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api"
    freeCompilerArgs += "-opt-in=androidx.compose.foundation.layout.ExperimentalLayoutApi"
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
  implementation(libs.bundles.androidx)

  implementation(libs.bundles.apollo)

  implementation(libs.bundles.compose)

  implementation(libs.hilt)
  ksp(libs.hilt.compiler)
}
