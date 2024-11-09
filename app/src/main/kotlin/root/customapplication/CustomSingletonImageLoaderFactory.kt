package root.customapplication

import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import coil3.disk.DiskCache
import coil3.memory.MemoryCache.Builder
import coil3.request.crossfade
import dagger.Reusable
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import okio.Path.Companion.toOkioPath

@Reusable
internal class CustomSingletonImageLoaderFactory @Inject constructor(
  @ApplicationContext private val context: PlatformContext
) : SingletonImageLoader.Factory {
  override fun newImageLoader(platformContext: PlatformContext) = ImageLoader.Builder(context)
    .crossfade(true)
    .memoryCache {
      Builder()
        .maxSizePercent(platformContext)
        .build()
    }
    .diskCache {
      DiskCache.Builder()
        .directory(context.cacheDir.resolve("images.cache").toOkioPath())
        .maxSizePercent(0.1)
        .build()
    }
    .build()
}
