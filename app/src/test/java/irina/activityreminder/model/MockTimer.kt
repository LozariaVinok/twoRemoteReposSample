package irina.activityreminder.model

import java.util.*

class MockTimer : Timer() {
    override fun scheduleAtFixedRate(task: TimerTask?, delay: Long, period: Long) {
        //super.scheduleAtFixedRate(task, delay, period)
    }

    override fun cancel() {
        //super.cancel()
    }
}