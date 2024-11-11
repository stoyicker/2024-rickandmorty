package root.customapplication.mainactivity.detail

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import rickandmortyapi.fragment.CharacterDetail
import root.customapplication.mainactivity.detail.DetailScreenViewModel.State.Success

@HiltViewModel
internal class DetailScreenViewModel @Inject constructor(getsCharacter: GetsCharacter) :
  ViewModel() {
  private val id = MutableStateFlow<String>("1")
  val characterDetail = id.flatMapLatest<String, State> {
    flow {
      emit(State.Loading)
      emit(
        try {
          Success(getsCharacter.getCharacter(it).characterDetail)
        } catch (throwable: Throwable) {
          State.Error(throwable)
        }
      )
    }
  }

  fun updateId(newId: String) {
    id.value = newId
  }

  sealed interface State {
    data object Loading : State
    data class Success(val characterDetail: CharacterDetail) : State
    data class Error(val throwable: Throwable) : State
  }
}
