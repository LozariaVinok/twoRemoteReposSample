package irina.activityreminder.model


import androidx.lifecycle.MutableLiveData
import irina.activityreminder.di.ComponentContainer.component
import irina.activityreminder.model.base.Event
import irina.activityreminder.utils.TimeUtils
import timber.log.Timber
import java.util.*
import javax.inject.Inject
import kotlin.math.abs

class TimerManager {
    @Inject
    lateinit var settings: Settings

    init {
        component.inject(this)
        settings.doNotNotifyTo.observeForever {
            start()
        }
        settings.doNotNotifyFrom.observeForever {
            start()
        }
    }

    val progress = MutableLiveData(0F)
    val remindEvent = MutableLiveData<Event<Any>>()
    val doNotNotifyEvent = MutableLiveData<Event<Long>>()
    var lastRemind: Long = System.currentTimeMillis()

    private var timerPeriod: Long = MOST_FREQUENT_PERIOD
    private var timer: Timer? = Timer()

    fun updateProgress(currentTime: Long) {
        if (currentTimeIsInShouldNotifyRange(currentTime)) {
            val progressTime = abs(currentTime - lastRemind)
            remindIfNeeded(progressTime, currentTime)
            progress.postValue(calculateProgress(progressTime))
//            Timber.d("progress ${settings.reminderPeriod}, ${progressTime}, ${progress.value}, $newProgress")

        } else {
            val doNotNotifyTo = settings.doNotNotifyTo.value?.split(":")
            if (doNotNotifyTo != null) {
                doNotNotifyEvent.postValue(
                    Event(
                        TimeUtils.timeTill(
                            currentTime,
                            doNotNotifyTo[0].toInt(),
                            doNotNotifyTo[1].toInt()
                        )
                    )
                )
            }
        }
    }

    private fun remindIfNeeded(progressTime: Long, currentTime: Long) {
        if (progressTime >= settings.reminderPeriod.value!!) {
            lastRemind = currentTime
            remindEvent.postValue(Event(Any()))

        }
    }

    private fun calculateProgress(progressTime: Long) =
        (100.0F / settings.reminderPeriod.value!! * progressTime)

    fun start() {
        if (timerPeriod <= 0) return
        Timber.d("start")
        if (timer != null) {
            timer?.cancel()
        }
        timer = Timer()
        val task = object : TimerTask() {
            override fun run() {
                updateProgress(System.currentTimeMillis())
            }
        }
        timer?.scheduleAtFixedRate(task, 0, timerPeriod)
    }

    fun stop() {
        Timber.d("stop")
        reset()
        timer?.cancel()
        timer = null
    }

    fun reset() {
        lastRemind = System.currentTimeMillis()
    }

    fun setMostFrequentPeriod(isMostFrequentPeriod: Boolean) {
        timerPeriod = if (isMostFrequentPeriod) {
            MOST_FREQUENT_PERIOD
        } else {
            FREQUENT_PERIOD
        }
        rescheduleTimerIfNeeded()
    }


    private fun rescheduleTimerIfNeeded() {
        if (timer != null) {
            start()
        }
    }

    private fun currentTimeIsInShouldNotifyRange(currentTime: Long): Boolean {
        val doNotNotifyTo = settings.doNotNotifyTo.value?.split(":")
        val doNotNotifyFrom = settings.doNotNotifyFrom.value?.split(":")
        if (doNotNotifyFrom == null || doNotNotifyTo == null) {
            return false
        }
        return !TimeUtils.timeIsBetween(
            currentTime,
            doNotNotifyFrom[0].toInt(),
            doNotNotifyFrom[1].toInt(),
            doNotNotifyTo[0].toInt(),
            doNotNotifyTo[1].toInt()
        )
    }

    companion object {
        private const val MOST_FREQUENT_PERIOD: Long = 1
        private const val FREQUENT_PERIOD: Long = 1000
    }

}

