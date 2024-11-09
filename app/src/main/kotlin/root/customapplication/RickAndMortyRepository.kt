package root.customapplication

import com.apollographql.apollo.ApolloClient
import dagger.Reusable
import javax.inject.Inject
import rickandmortyapi.GetCharactersQuery

@Reusable
internal class RickAndMortyRepository @Inject constructor(private val apolloClient: ApolloClient) {
  suspend fun getCharacters(page: Int, filter: String) =
    apolloClient.query(GetCharactersQuery(page, filter)).execute().dataOrThrow().characters
}
