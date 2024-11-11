This is my demo project. It showcases a list-detail implementation of data from the Rick And Morty GraphQL API with a couple of tests of different kinds.

The app is offline-first, with both memory and disk caches for all kinds of data.

Finally, the app has support for DayNight theming, rotating between forcing each of the variants and following the system.

Please make sure to run the release variant. Debuggable builds take significant performance hits when using Compose, but if I disable the debug build type then you won't be able to run the instrumented tests.
