package self

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension

internal class AppPlugin : Plugin<Project> {
  override fun apply(target: Project) = with(target) {
    extensions.create("app", AppPluginExtension::class.java)
    val libs = extensions
      .getByType(VersionCatalogsExtension::class.java)
      .named("libs")
    pluginManager.apply(
      libs.findPlugin("android-application")
        .get()
        .get()
        .pluginId
    )
    pluginManager.apply(
      libs.findPlugin("kotlin-android")
        .get()
        .get()
        .pluginId
    )
    configure<ApplicationExtension> {
      buildTypes {
        release {
          isMinifyEnabled = true
          isShrinkResources = true
          proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"))
        }
      }
      compileSdk = 34
      defaultConfig {
        minSdk = 21
        targetSdk = compileSdk
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
      }
    }
    configure<KotlinProjectExtension> {
      jvmToolchain(17)
    }
    dependencies {
      "androidTestImplementation"(libs.findLibrary("androidx-test-runner").get().get())
    }
  }
}
