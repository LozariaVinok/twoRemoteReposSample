package irina.activityreminder.model

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import irina.activityreminder.di.DataModule
import org.jetbrains.annotations.NotNull
import javax.inject.Singleton

@Module
class MockDataModule : DataModule() {


    @Provides
    @NotNull
    @Singleton
    override fun provideSharedPreferences(context: Context): SharedPreferences {
        return MockSharedPreference()
    }

}
