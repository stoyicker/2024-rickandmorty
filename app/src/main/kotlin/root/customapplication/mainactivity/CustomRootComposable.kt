package root.customapplication.mainactivity

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import root.customapplication.CustomTheme
import root.customapplication.DayNightThemeManager
import root.customapplication.LocalIsNightMode
import root.customapplication.mainactivity.detail.DetailScreen
import root.customapplication.mainactivity.detail.NAVIGATION_DETAIL_SCREEN_ARG_ID
import root.customapplication.mainactivity.detail.NAVIGATION_KEY_DETAIL_SCREEN
import root.customapplication.mainactivity.list.ListScreen
import root.customapplication.mainactivity.list.NAVIGATION_KEY_LIST_SCREEN

@Composable
internal fun CustomRootComposable(dayNightThemeManager: DayNightThemeManager) {
  val navController = rememberNavController()
  val isNightMode by dayNightThemeManager.isNightModeActive.collectAsStateWithLifecycle()
  CompositionLocalProvider(LocalIsNightMode provides isNightMode) {
    CustomTheme {
      Surface {
        NavHost(navController, startDestination = NAVIGATION_KEY_LIST_SCREEN) {
          composable(NAVIGATION_KEY_LIST_SCREEN) { ListScreen(dayNightThemeManager) }
          composable(NAVIGATION_KEY_DETAIL_SCREEN) {
            val fallback = {
              navController.navigate(NAVIGATION_KEY_LIST_SCREEN) {
                popUpTo(NAVIGATION_KEY_LIST_SCREEN) { inclusive = true }
              }
            }
            val arguments = it.arguments ?: return@composable fallback()
            val id =
              arguments.getString(NAVIGATION_DETAIL_SCREEN_ARG_ID) ?: return@composable fallback()
            DetailScreen(dayNightThemeManager, id)
          }
        }
      }
    }
  }
}
