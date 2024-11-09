package root.customapplication

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import stoyicker.interviewdemo.app.R

@Composable
internal fun CustomScaffold(
  dayNightThemeManager: DayNightThemeManager,
  title: String,
  bottomBar: @Composable () -> Unit = {},
  content: @Composable (PaddingValues) -> Unit
) {
  val context = LocalContext.current
  val nightModeBehavior by dayNightThemeManager.nightModeBehavior
    .collectAsStateWithLifecycle()
  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text(text = title) },
        actions = {
          IconButton(onClick = {
            dayNightThemeManager.rotateNightModeBehavior()
          }) {
            Icon(
              painter = painterResource(
                id = when (nightModeBehavior) {
                  AppCompatDelegate.MODE_NIGHT_YES -> R.drawable.icon_theme_night
                  AppCompatDelegate.MODE_NIGHT_NO -> R.drawable.icon_theme_day
                  else -> R.drawable.icon_theme_follow_system
                }
              ),
              contentDescription = context.getString(
                when (nightModeBehavior) {
                  AppCompatDelegate.MODE_NIGHT_YES -> R.string.content_description_night_theme
                  AppCompatDelegate.MODE_NIGHT_NO -> R.string.content_description_day_theme
                  else -> R.string.content_description_follow_system
                }
              )
            )
          }
        }
      )
    },
    bottomBar = bottomBar,
    content = content
  )
}
