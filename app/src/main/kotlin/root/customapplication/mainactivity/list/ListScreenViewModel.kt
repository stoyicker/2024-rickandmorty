package root.customapplication.mainactivity.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest

@HiltViewModel
internal class ListScreenViewModel @Inject constructor(
  charactersPagingSourceFactory: CharactersPagingSource.Factory
) : ViewModel() {
  private val filter = MutableStateFlow<String>("")
  val characterEntries = filter.flatMapLatest {
    Pager(
      config = PagingConfig(pageSize = 20),
      pagingSourceFactory = { charactersPagingSourceFactory.create(it) }
    ).flow.cachedIn(viewModelScope)
  }

  fun updateFilter(newFilter: String) {
    filter.value = newFilter
  }
}
