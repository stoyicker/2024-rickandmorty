package root.customapplication.mainactivity.detail

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import root.customapplication.CustomScaffold
import root.customapplication.DayNightThemeManager
import stoyicker.interviewdemo.app.R

const val NAVIGATION_DETAIL_SCREEN_ARG_ID = "id"
const val NAVIGATION_KEY_DETAIL_SCREEN = "detail/{$NAVIGATION_DETAIL_SCREEN_ARG_ID}"

@Composable
internal fun DetailScreen(dayNightThemeManager: DayNightThemeManager, id: String) {
  val context = LocalContext.current
  CustomScaffold(dayNightThemeManager, title = context.getString(R.string.top_bar_title_detail)) {
  }
}
