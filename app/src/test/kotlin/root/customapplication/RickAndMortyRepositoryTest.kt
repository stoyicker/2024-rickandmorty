package root.customapplication

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.ApolloResponse
import kotlin.test.assertFailsWith
import kotlin.test.assertSame
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.kotlin.argThat
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import rickandmortyapi.GetCharacterQuery

internal class RickAndMortyRepositoryTest {
  private val apolloClient = mock<ApolloClient>()
  private val subject = RickAndMortyRepository(apolloClient)

  @Test
  fun getCharacterSuccess() = runTest {
    val id = "37"
    val expected = mock<GetCharacterQuery.Character>()
    val data = mock<GetCharacterQuery.Data> {
      on { character } doReturn expected
    }
    val response = mock<ApolloResponse<GetCharacterQuery.Data>> {
      on { dataOrThrow() } doReturn data
    }
    val call = mock<ApolloCall<GetCharacterQuery.Data>>()
    // This stubbing can't go in the initialization closure because execute is suspend
    whenever(call.execute()) doReturn response
    whenever(
      apolloClient.query(
        argThat<GetCharacterQuery> {
          this.id.contentEquals(id)
        }
      )
    ) doReturn call

    val actual = subject.getCharacter(id)

    assertSame(expected, actual)
  }

  @Test
  fun getCharacterQueryThrows() = runTest {
    val id = "0"
    whenever(
      apolloClient.query(
        argThat<GetCharacterQuery> {
          this.id.contentEquals(id)
        }
      )
    ) doThrow RuntimeException("stub")

    assertFailsWith(RuntimeException::class) { subject.getCharacter(id) }
  }

  @Test
  fun getCharacterExecuteThrows() = runTest {
    val id = "9999"
    val call = mock<ApolloCall<GetCharacterQuery.Data>>()
    // This stubbing can't go in the initialization closure because execute is suspend
    whenever(call.execute()) doThrow IllegalStateException("stub")
    whenever(
      apolloClient.query(
        argThat<GetCharacterQuery> {
          this.id.contentEquals(id)
        }
      )
    ) doReturn call

    assertFailsWith(IllegalStateException::class) { subject.getCharacter(id) }
  }

  @Test
  fun getCharacterDataOrThrowThrows() = runTest {
    val id = "-1"
    val response = mock<ApolloResponse<GetCharacterQuery.Data>> {
      on { dataOrThrow() } doThrow UnsupportedOperationException("stub")
    }
    val call = mock<ApolloCall<GetCharacterQuery.Data>>()
    // This stubbing can't go in the initialization closure because execute is suspend
    whenever(call.execute()) doReturn response
    whenever(
      apolloClient.query(
        argThat<GetCharacterQuery> {
          this.id.contentEquals(id)
        }
      )
    ) doReturn call

    assertFailsWith(UnsupportedOperationException::class) { subject.getCharacter(id) }
  }

  @Test
  fun getCharacterCharacterThrows() = runTest {
    val id = ""
    val data = mock<GetCharacterQuery.Data> {
      on { character } doThrow NoClassDefFoundError("stub")
    }
    val response = mock<ApolloResponse<GetCharacterQuery.Data>> {
      on { dataOrThrow() } doReturn data
    }
    val call = mock<ApolloCall<GetCharacterQuery.Data>>()
    // This stubbing can't go in the initialization closure because execute is suspend
    whenever(call.execute()) doReturn response
    whenever(
      apolloClient.query(
        argThat<GetCharacterQuery> {
          this.id.contentEquals(id)
        }
      )
    ) doReturn call

    assertFailsWith(NoClassDefFoundError::class) { subject.getCharacter(id) }
  }
}
