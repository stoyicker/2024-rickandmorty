package root.customapplication

import com.apollographql.apollo.ApolloClient
import dagger.Reusable
import javax.inject.Inject
import rickandmortyapi.GetCharacterQuery
import rickandmortyapi.GetCharactersQuery

@Reusable
internal class RickAndMortyRepository @Inject constructor(private val apolloClient: ApolloClient) {
  suspend fun getCharacters(page: Int, filter: String) =
    apolloClient.query(GetCharactersQuery(page, filter)).execute().dataOrThrow().characters

  /**
   * There is an interesting option here: if we modify the GraphQL schema slightly, we can have the
   * GetCharacters query return, for each model, the info we will use for the detail. Then here,
   * based on the fact that we know how large a page is and that the ids are an arithmetic
   * progression, we can use the GetCharactersQuery instead to reuse the data from it, effectively
   * making the detail screen available offline for every character that has been scrolled to while
   * online and is thus in the cache, even if we never entered the detail screen while online.
   *
   * That said, that makes the model for the list larger, which I don't like, and it also makes the
   * cache size grow faster, which I also don't like. In addition, it involves assuming that the
   * data for an entry from the list is the same as the detail data, which is probably a safe
   * assumption in this case, but still an unnecessary risk.
   */
  suspend fun getCharacter(id: String) =
    apolloClient.query(GetCharacterQuery(id)).execute().dataOrThrow().character
}
