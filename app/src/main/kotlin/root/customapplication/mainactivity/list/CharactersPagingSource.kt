package root.customapplication.mainactivity.list

import androidx.paging.PagingSource
import androidx.paging.PagingState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import rickandmortyapi.fragment.CharactersEntry

internal class CharactersPagingSource @AssistedInject constructor(
  private val getsCharacters: GetsCharacters,
  @Assisted private val filter: String
) : PagingSource<Int, CharactersEntry>() {
  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharactersEntry> {
    val page = params.key ?: 1
    val data = try {
      getsCharacters.getCharacters(page, filter)
    } catch (throwable: Throwable) {
      return LoadResult.Error(throwable)
    }
    return LoadResult.Page(
      data = data.results.map { it.charactersEntry },
      prevKey = data.info.prev,
      nextKey = data.info.next
    )
  }

  override fun getRefreshKey(state: PagingState<Int, CharactersEntry>) = state.run {
    anchorPosition?.let { closestPageToPosition(it)?.run { prevKey ?: nextKey } }
  }

  @AssistedFactory
  interface Factory {
    fun create(filter: String): CharactersPagingSource
  }
}
