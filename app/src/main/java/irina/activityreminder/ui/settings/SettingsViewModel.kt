package irina.activityreminder.ui.settings


import androidx.databinding.Bindable
import androidx.lifecycle.MutableLiveData
import irina.activityreminder.di.ComponentContainer.component
import irina.activityreminder.model.Settings
import irina.activityreminder.ui.base.ObservableViewModel
import javax.inject.Inject

class SettingsViewModel : ObservableViewModel() {
    @Inject
    lateinit var settings: Settings

    init {
        component.inject(this)
    }

    val doNotNotifyFromMinutes: Int
        get() {
            return doNotNotifyFrom.value?.split(":")?.get(1)?.toInt() ?: 0
        }
    val doNotNotifyFromHour: Int
        get() {
            return doNotNotifyFrom.value?.split(":")?.get(0)?.toInt() ?: 0
        }
    val doNotNotifyToMinutes: Int
        get() {
            return doNotNotifyTo.value?.split(":")?.get(1)?.toInt() ?: 0
        }
    val doNotNotifyToHour: Int
        get() {
            return doNotNotifyTo.value?.split(":")?.get(0)?.toInt() ?: 0
        }

    val doNotNotifyFrom: MutableLiveData<String> = settings.doNotNotifyFrom

    val doNotNotifyTo: MutableLiveData<String> = settings.doNotNotifyTo


    var reminderPeriod: String
        @Bindable
        get() {
            return if (settings.reminderPeriod.value == null) "" else (settings.reminderPeriod.value!! / 60 / 1000).toString()
        }
        set(value) {
            settings.reminderPeriod.setValue(if (value.isEmpty()) 0 else value.toInt() * 60 * 1000)
        }

}
