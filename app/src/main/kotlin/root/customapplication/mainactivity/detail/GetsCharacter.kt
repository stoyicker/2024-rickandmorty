package root.customapplication.mainactivity.detail

import rickandmortyapi.GetCharacterQuery

interface GetsCharacter {
  suspend fun getCharacter(id: String): GetCharacterQuery.Character
}
