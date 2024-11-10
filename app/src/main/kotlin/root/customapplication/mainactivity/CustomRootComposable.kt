package root.customapplication.mainactivity

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import root.customapplication.CustomTheme
import root.customapplication.DayNightThemeManager
import root.customapplication.LocalIsNightMode
import root.customapplication.mainactivity.detail.DetailScreen
import root.customapplication.mainactivity.detail.NAVIGATION_DETAIL_SCREEN_ARG_ID
import root.customapplication.mainactivity.detail.NAVIGATION_KEY_DETAIL_SCREEN
import root.customapplication.mainactivity.list.ListScreen
import root.customapplication.mainactivity.list.NAVIGATION_KEY_LIST_SCREEN

val Modifier.screenPadding: Modifier
  get() = padding(start = 8.dp, top = 0.dp, end = 8.dp, bottom = 8.dp)

@Composable
internal fun CustomRootComposable(dayNightThemeManager: DayNightThemeManager) {
  val navController = rememberNavController()
  val isNightMode by dayNightThemeManager.isNightModeActive.collectAsStateWithLifecycle()
  CompositionLocalProvider(LocalIsNightMode provides isNightMode) {
    CustomTheme {
      Surface {
        NavHost(navController, startDestination = NAVIGATION_KEY_LIST_SCREEN) {
          composable(NAVIGATION_KEY_LIST_SCREEN) {
            ListScreen(dayNightThemeManager, navController)
          }
          composable(
            "$NAVIGATION_KEY_DETAIL_SCREEN/{$NAVIGATION_DETAIL_SCREEN_ARG_ID}",
            arguments = listOf(
              navArgument(NAVIGATION_DETAIL_SCREEN_ARG_ID) {
                type = NavType.StringType
              }
            )
          ) {
            val fallback = {
              navController.navigate(NAVIGATION_KEY_LIST_SCREEN) {
                popUpTo(NAVIGATION_KEY_LIST_SCREEN) { inclusive = true }
              }
            }
            val arguments = it.arguments ?: return@composable fallback()
            val id =
              arguments.getString(NAVIGATION_DETAIL_SCREEN_ARG_ID) ?: return@composable fallback()
            DetailScreen(dayNightThemeManager, navController, id)
          }
        }
      }
    }
  }
}
