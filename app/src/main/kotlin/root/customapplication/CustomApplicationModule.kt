package root.customapplication

import android.content.Context
import android.content.SharedPreferences
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.cache.normalized.api.MemoryCacheFactory
import com.apollographql.apollo.cache.normalized.normalizedCache
import com.apollographql.apollo.cache.normalized.sql.SqlNormalizedCacheFactory
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlin.time.Duration.Companion.minutes

@InstallIn(SingletonComponent::class)
@Module
internal object CustomApplicationModule {
  @Provides
  @Reusable
  fun sharedPreferences(@ApplicationContext context: Context): SharedPreferences =
    context.getSharedPreferences(SharedPreferencesDefault.FILE_NAME, Context.MODE_PRIVATE)

  @Provides
  @Singleton
  fun apolloClient(@ApplicationContext context: Context) = ApolloClient.Builder()
    .serverUrl("https://rickandmortyapi.com/graphql")
    .normalizedCache(
      MemoryCacheFactory(
        // ChatGPT estimates an avg of 15 KB per entry, so let's make sure we can keep a few
        maxSizeBytes = 100 * 15 * 1_024,
        // Long enough to matter, short enough to be obnoxious when testing manually. Typically in
        // prod you would never invalidate your memory cache based on time imho
        expireAfterMillis = 5.minutes.inWholeMilliseconds
      ).chain(
        SqlNormalizedCacheFactory(context, "apolloDiskCache.db")
      )
    ).build()
}
