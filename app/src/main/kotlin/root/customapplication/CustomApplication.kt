package root.customapplication

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@HiltAndroidApp
internal class CustomApplication : Application() {
  @Inject
  lateinit var dayNightThemeManager: DayNightThemeManager

  // Purposely keep this outside of the DI graph, we don't want other components to have access to
  // a CoroutineScope as big as one encompassing the entire lifetime of the app
  private val appCoroutineScope = CoroutineScope(Dispatchers.Main)

  override fun onCreate() {
    super.onCreate()
    appCoroutineScope.launch {
      dayNightThemeManager.nightModeBehavior
        .collectLatest {
          AppCompatDelegate.setDefaultNightMode(it)
          dayNightThemeManager.refreshIsNightModeActive()
        }
    }
  }

  override fun onTerminate() {
    appCoroutineScope.cancel()
    super.onTerminate()
  }
}
