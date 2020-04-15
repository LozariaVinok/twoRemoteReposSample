package irina.activityreminder

import android.app.Application
import irina.activityreminder.di.ComponentContainer
import timber.log.Timber

class ActivityReminderApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        ComponentContainer.buildComponent(this)
    }

}