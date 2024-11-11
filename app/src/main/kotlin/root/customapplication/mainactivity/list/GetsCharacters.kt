package root.customapplication.mainactivity.list

import rickandmortyapi.GetCharactersQuery

interface GetsCharacters {
  suspend fun getCharacters(page: Int, filter: String): GetCharactersQuery.Characters
}
