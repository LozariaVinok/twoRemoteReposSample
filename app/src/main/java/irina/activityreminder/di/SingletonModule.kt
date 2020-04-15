package irina.activityreminder.di

import dagger.Module
import dagger.Provides
import irina.activityreminder.model.Settings
import irina.activityreminder.model.TimerManager
import org.jetbrains.annotations.NotNull
import javax.inject.Singleton

@Module
object SingletonModule {
    @Provides
    @NotNull
    @Singleton
    internal fun provideTimerManager(): TimerManager {
        return TimerManager()
    }

    @Provides
    @NotNull
    @Singleton
    internal fun provideSettings(): Settings {
        return Settings()
    }

}