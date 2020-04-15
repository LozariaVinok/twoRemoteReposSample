package irina.activityreminder.model

import android.content.SharedPreferences
import irina.activityreminder.di.ComponentContainer
import irina.activityreminder.model.base.IntSharedPreferencesLiveData
import irina.activityreminder.model.base.StringSharedPreferencesLiveData
import javax.inject.Inject

class Settings {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    val doNotNotifyFrom: StringSharedPreferencesLiveData

    val doNotNotifyTo: StringSharedPreferencesLiveData

    val reminderPeriod: IntSharedPreferencesLiveData

    init {
        ComponentContainer.component.inject(this)
        doNotNotifyFrom =
            StringSharedPreferencesLiveData(
                sharedPreferences,
                KEY_DO_NOT_NOTIFY_FROM,
                "22:00"
            )
        doNotNotifyTo =
            StringSharedPreferencesLiveData(
                sharedPreferences,
                KEY_DO_NOT_NOTIFY_TO,
                "09:00"
            )
        reminderPeriod =
            IntSharedPreferencesLiveData(
                sharedPreferences,
                KEY_REMINDER_PERIOD,
                (30 * 60 * 1000)
            )
    }


    val doNotNotifyPeriodString: String
        get() {
            return "${doNotNotifyFrom.value}~${doNotNotifyTo.value}"
        }

    companion object {
        private const val KEY_REMINDER_PERIOD = "settings.reminderperiod"
        private const val KEY_DO_NOT_NOTIFY_TO = "settings.doNotNotifyTo"
        private const val KEY_DO_NOT_NOTIFY_FROM = "settings.doNotNotifyFrom"
    }


}