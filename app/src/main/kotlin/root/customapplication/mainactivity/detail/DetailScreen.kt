package root.customapplication.mainactivity.detail

import android.content.res.Configuration
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import root.customapplication.CustomScaffold
import root.customapplication.DayNightThemeManager
import stoyicker.interviewdemo.app.R

const val NAVIGATION_DETAIL_SCREEN_ARG_ID = "id"
const val NAVIGATION_KEY_DETAIL_SCREEN = "detail"

@Composable
internal fun DetailScreen(
  dayNightThemeManager: DayNightThemeManager,
  navController: NavController,
  id: String,
  detailScreenViewModel: DetailScreenViewModel = hiltViewModel()
) {
  val context = LocalContext.current
  detailScreenViewModel.updateId(id)
  val state by detailScreenViewModel.characterDetail
    .collectAsStateWithLifecycle(DetailScreenViewModel.State.Loading)
  CustomScaffold(
    dayNightThemeManager,
    title = context.getString(R.string.top_bar_title_detail),
    navigationIcon = {
      IconButton({
        navController.popBackStack()
      }) {
        Icon(
          painter = painterResource(R.drawable.icon_navigation_back),
          contentDescription = context.getString(R.string.content_description_back)
        )
      }
    }
  ) { paddingValues ->
    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE
    val content: @Composable () -> Unit = {
      when (val it = state) {
        DetailScreenViewModel.State.Loading ->
          LinearProgressIndicator(modifier = Modifier.fillMaxWidth())

        is DetailScreenViewModel.State.Error -> navController.popBackStack()

        is DetailScreenViewModel.State.Success -> {
          val context = LocalContext.current
          with(it.characterDetail) {
            AsyncImage(
              modifier = Modifier
                .fillMaxSize(),
              contentDescription = name,
              contentScale = when (isLandscape) {
                true -> ContentScale.FillHeight
                false -> ContentScale.FillWidth
              },
              model = image
            )
            Column(
              modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
            ) {
              arrayOf(
                context.getString(R.string.field_label_name) to name,
                context.getString(R.string.field_label_status) to status,
                context.getString(R.string.field_label_species) to species,
                context.getString(R.string.field_label_type) to type,
                context.getString(R.string.field_label_gender) to gender
              ).filterNot { it.second.isNullOrBlank() }
                .map {
                  @Suppress("UNCHECKED_CAST")
                  it as Pair<String, String>
                }
                .forEach { (label, value) ->
                  Row(
                    verticalAlignment = Alignment.CenterVertically
                  ) {
                    Text(
                      modifier = Modifier
                        .padding(end = 4.dp)
                        .alignByBaseline(),
                      style = MaterialTheme.typography.titleMedium,
                      text = label
                    )
                    Text(
                      modifier = Modifier.alignByBaseline(),
                      overflow = TextOverflow.Ellipsis,
                      style = MaterialTheme.typography.bodyMedium,
                      text = value
                    )
                  }
                }
            }
          }
        }
      }
    }
    val scrollState = rememberScrollState()
    when (isLandscape) {
      true -> Row(
        modifier = Modifier
          .fillMaxSize()
          .padding(paddingValues)
          .horizontalScroll(scrollState)
      ) {
        content()
      }

      false -> Column(
        modifier = Modifier
          .fillMaxSize()
          .padding(paddingValues)
          .verticalScroll(scrollState)
      ) {
        content()
      }
    }
  }
}
