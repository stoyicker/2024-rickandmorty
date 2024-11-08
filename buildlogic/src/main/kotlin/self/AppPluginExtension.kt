package self

import com.android.build.api.dsl.ApplicationExtension
import javax.inject.Inject
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

open class AppPluginExtension @Inject constructor(private val project: Project) {
  fun namespaceSuffix(suffix: String) = with(project) {
    configure<ApplicationExtension> {
      namespace = "stoyicker.interviewdemo.$suffix"
    }
  }
}
