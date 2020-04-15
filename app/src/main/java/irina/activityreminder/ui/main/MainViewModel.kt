package irina.activityreminder.ui.main

import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import irina.activityreminder.di.ComponentContainer.component
import irina.activityreminder.model.TimerManager
import irina.activityreminder.ui.base.ObservableViewModel
import irina.activityreminder.utils.TimeUtils
import javax.inject.Inject

class MainViewModel : ObservableViewModel() {
    @Inject
    lateinit var timerManager: TimerManager

    init {
        component.inject(this)
    }

    val isServiceShouldRun = MutableLiveData(true)

    val progress: LiveData<Float> = timerManager.progress

    var started = MutableLiveData(true)

    val lastRemind: String
        @Bindable
        get() {
            return "Last remind: " + TimeUtils.convertLongToTime(timerManager.lastRemind)
        }

    fun resetButtonClicked() {
        timerManager.reset()
    }


    fun startStopButtonClicked() {
        started.value = if (started.value!!) {
            isServiceShouldRun.postValue(false)
            false
        } else {
            isServiceShouldRun.postValue(true)
            true
        }
    }

    fun onResume() {
        timerManager.setMostFrequentPeriod(true)
    }

    fun onPause() {
        timerManager.setMostFrequentPeriod(false)
    }


}
