package root.customapplication.mainactivity.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import root.customapplication.CustomScaffold
import root.customapplication.DayNightThemeManager
import root.customapplication.mainactivity.detail.NAVIGATION_KEY_DETAIL_SCREEN
import root.customapplication.mainactivity.screenPadding
import stoyicker.interviewdemo.app.R

const val NAVIGATION_KEY_LIST_SCREEN = "list"

@Composable
internal fun ListScreen(
  dayNightThemeManager: DayNightThemeManager,
  navController: NavController,
  initialFilter: String = "",
  initialFilterExpanded: Boolean = false,
  listScreenViewModel: ListScreenViewModel = hiltViewModel()
) {
  val context = LocalContext.current
  val characterEntries = listScreenViewModel.characterEntries.collectAsLazyPagingItems()
  var filter by rememberSaveable { mutableStateOf(initialFilter) }
  var expanded by rememberSaveable { mutableStateOf(initialFilterExpanded) }
  CustomScaffold(
    dayNightThemeManager,
    title = context.getString(R.string.top_bar_title_list),
    bottomBar = {
      SearchBar(
        modifier = Modifier.fillMaxWidth()
          .screenPadding,
        inputField = {
          SearchBarDefaults.InputField(
            query = filter,
            onQueryChange = {
              filter = it
              listScreenViewModel.updateFilter(it)
            },
            onSearch = {
              expanded = false
              listScreenViewModel.updateFilter(it)
            },
            expanded = expanded,
            onExpandedChange = { expanded = it },
            placeholder = {
              Text(text = context.getString(R.string.placeholder_search))
            }
          )
        },
        expanded = false,
        onExpandedChange = {},
        content = {}
      )
    }
  ) { paddingValues ->
    Box(
      modifier = Modifier.fillMaxWidth()
        .padding(paddingValues)
    ) {
      if (arrayOf(
          characterEntries.loadState.prepend,
          characterEntries.loadState.append,
          characterEntries.loadState.refresh
        ).any { it is LoadState.Loading }
      ) {
        LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
      }
      Box(
        modifier = Modifier.fillMaxWidth()
          .screenPadding
      ) {
        if (characterEntries.itemCount == 0) {
          Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
          ) {
            Text(
              modifier = Modifier.fillMaxWidth(),
              style = MaterialTheme.typography.displayMedium,
              text = context.getString(R.string.no_items_found),
              textAlign = TextAlign.Center
            )
          }
        } else {
          val size = 100.dp
          // State management across rotation works really bad. But I'm not working around a Google
          // bug in a demo app. In a real scenario, I'd probably have a viewModel to save the
          // relevant data (e.g. position, offset) and restore it manually
          val lazyGridState = rememberLazyGridState()
          LazyVerticalGrid(
            modifier = Modifier.fillMaxWidth(),
            columns = GridCells.Adaptive(size),
            state = lazyGridState
          ) {
            items(
              count = characterEntries.itemCount,
              key = characterEntries.itemKey { it.id }
            ) {
              with(characterEntries[it] ?: return@items) {
                CharacterEntryCard(name, image, size) {
                  navController.navigate("$NAVIGATION_KEY_DETAIL_SCREEN/$id")
                }
              }
            }
          }
        }
      }
    }
  }
}
