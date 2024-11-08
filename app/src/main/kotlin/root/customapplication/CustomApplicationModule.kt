package root.customapplication

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
internal object CustomApplicationModule {
  @Provides
  @Reusable
  fun sharedPreferences(@ApplicationContext context: Context): SharedPreferences =
    context.getSharedPreferences(SharedPreferencesDefault.FILE_NAME, Context.MODE_PRIVATE)
}
