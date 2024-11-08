package root.customapplication.mainactivity.list

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import root.customapplication.CustomScaffold
import root.customapplication.DayNightThemeManager
import stoyicker.interviewdemo.app.R

const val NAVIGATION_KEY_LIST_SCREEN = "list"

@Composable
internal fun ListScreen(dayNightThemeManager: DayNightThemeManager) {
  val context = LocalContext.current
  CustomScaffold(dayNightThemeManager, title = context.getString(R.string.top_bar_title_list)) {
  }
}
