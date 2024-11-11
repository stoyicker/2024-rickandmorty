package root.customapplication.mainactivity.detail

import android.content.SharedPreferences
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollTo
import androidx.navigation.compose.rememberNavController
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import rickandmortyapi.GetCharacterQuery
import rickandmortyapi.fragment.CharacterDetail
import root.customapplication.DayNightThemeManager
import stoyicker.interviewdemo.app.R

internal class DetailScreenTest {
  @get:Rule
  val composeTestRule = createComposeRule()
  private val getsCharacter = mock(GetsCharacter::class.java)
  private val stubViewModel = DetailScreenViewModel(getsCharacter)

  @Test
  fun testTextInfo() = runTest {
    val targetContext = InstrumentationRegistry.getInstrumentation().targetContext
    val themeManager = DayNightThemeManager(targetContext, mock(SharedPreferences::class.java))
    val id = "10"
    val name = "hola name"
    val species = "hola"
    val gender = "stub gender"
    val status = ""
    val characterDetail = CharacterDetail(
      name = name,
      species = species,
      gender = gender,
      status = status,
      type = null,
      image = null
    )
    val character = GetCharacterQuery.Character("", characterDetail)
    `when`(getsCharacter.getCharacter(id)).thenReturn(character)

    composeTestRule.setContent {
      DetailScreen(
        themeManager,
        rememberNavController(),
        id,
        stubViewModel
      )
    }

    composeTestRule.onNodeWithText(targetContext.getString(R.string.field_label_name))
      .assertIsDisplayedAfterScroll()
    composeTestRule.onNodeWithText(name).assertIsDisplayedAfterScroll()
    composeTestRule.onNodeWithText(targetContext.getString(R.string.field_label_species))
      .assertIsDisplayedAfterScroll()
    composeTestRule.onNodeWithText(species).assertIsDisplayedAfterScroll()
    composeTestRule.onNodeWithText(targetContext.getString(R.string.field_label_gender))
      .assertIsDisplayedAfterScroll()
    composeTestRule.onNodeWithText(gender).assertIsDisplayedAfterScroll()
    composeTestRule.onNodeWithText(targetContext.getString(R.string.field_label_status))
      .assertDoesNotExist()
    composeTestRule.onNodeWithText(status).assertDoesNotExist()
    composeTestRule.onNodeWithText(targetContext.getString(R.string.field_label_type))
      .assertDoesNotExist()
  }
}

private fun SemanticsNodeInteraction.assertIsDisplayedAfterScroll() {
  performScrollTo()
  assertIsDisplayed()
}
