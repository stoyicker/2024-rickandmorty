package root.customapplication

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.NightMode
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@Singleton
internal class DayNightThemeManager @Inject constructor(
  @ApplicationContext private val context: Context,
  private val sharedPreferences: SharedPreferences
) {
  private val _nightModeBehavior = MutableStateFlow(latestNightModeBehavior)
  val nightModeBehavior = _nightModeBehavior.asStateFlow()
  private val _isNightModeActive = MutableStateFlow(latestNightModeIsActive)
  val isNightModeActive = _isNightModeActive.asStateFlow()

  fun rotateNightModeBehavior() {
    @NightMode val sanitizedCurrent = when (val it = latestNightModeBehavior) {
      AppCompatDelegate.MODE_NIGHT_YES,
      AppCompatDelegate.MODE_NIGHT_NO,
      AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM -> it

      else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
    }
    // MODE_NIGHT_AUTO_BATTERY is purposely out of the rotation for simplicity to avoid needing an
    // extra icon and contentDescription
    @NightMode val new = when (sanitizedCurrent) {
      AppCompatDelegate.MODE_NIGHT_YES -> AppCompatDelegate.MODE_NIGHT_NO
      AppCompatDelegate.MODE_NIGHT_NO -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
      else -> AppCompatDelegate.MODE_NIGHT_YES
    }
    sharedPreferences.edit()
      .putInt(PREFERENCE_KEY_NIGHT_MODE_BEHAVIOR, new)
      .apply()
    _nightModeBehavior.value = new
  }

  fun refreshIsNightModeActive() {
    _isNightModeActive.value = latestNightModeIsActive
  }

  @Suppress("DUPLICATE_LABEL_IN_WHEN") // Makes completion clearer
  @NightMode
  private val latestNightModeBehavior: Int
    get() = sharedPreferences.getInt(PREFERENCE_KEY_NIGHT_MODE_BEHAVIOR, Int.MIN_VALUE).run {
      @Suppress("DEPRECATION") // We want to allow deprecated values for compatibility
      when (this) {
        AppCompatDelegate.MODE_NIGHT_YES,
        AppCompatDelegate.MODE_NIGHT_NO,
        AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM,
        AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY,
        AppCompatDelegate.MODE_NIGHT_AUTO,
        AppCompatDelegate.MODE_NIGHT_AUTO_TIME -> this

        else -> AppCompatDelegate.MODE_NIGHT_UNSPECIFIED
      }
    }

  private val latestNightModeIsActive: Boolean
    get() = when (latestNightModeBehavior) {
      AppCompatDelegate.MODE_NIGHT_YES -> true
      AppCompatDelegate.MODE_NIGHT_NO -> false
      else ->
        context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK ==
          Configuration.UI_MODE_NIGHT_YES
    }

  companion object {
    private const val PREFERENCE_KEY_NIGHT_MODE_BEHAVIOR = "KEY_NIGHT_MODE_BEHAVIOR"
  }
}
