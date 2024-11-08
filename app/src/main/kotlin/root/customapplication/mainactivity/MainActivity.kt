package root.customapplication.mainactivity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import root.customapplication.DayNightThemeManager

@AndroidEntryPoint
internal class MainActivity : ComponentActivity() {
  @Inject
  lateinit var dayNightThemeManager: DayNightThemeManager

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    dayNightThemeManager.refreshIsNightModeActive()
    setContent { CustomRootComposable(dayNightThemeManager) }
  }
}
