package irina.activityreminder.di

import dagger.Component
import irina.activityreminder.model.AfterDoNotNotifyPeriodWorker
import irina.activityreminder.model.Settings
import irina.activityreminder.model.TimerManager
import irina.activityreminder.ui.SchedulerService
import irina.activityreminder.ui.main.MainViewModel
import irina.activityreminder.ui.settings.SettingsViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, SingletonModule::class, DataModule::class])
interface AppComponent {
    fun inject(mainViewModel: MainViewModel)
    fun inject(settingsViewModel: SettingsViewModel)
    fun inject(schedulerService: SchedulerService)
    fun inject(timerManager: TimerManager)
    fun inject(settings: Settings)
    fun inject(worker: AfterDoNotNotifyPeriodWorker)
}