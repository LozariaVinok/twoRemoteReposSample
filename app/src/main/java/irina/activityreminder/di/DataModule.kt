package irina.activityreminder.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import irina.activityreminder.R
import org.jetbrains.annotations.NotNull
import javax.inject.Singleton

@Module
open class DataModule {


    @Provides
    @NotNull
    @Singleton
    open fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(
            context.getString(R.string.shared_preferences_global_key),
            Context.MODE_PRIVATE
        )
    }

}
