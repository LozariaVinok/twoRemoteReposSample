package irina.activityreminder.model

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import irina.activityreminder.di.ComponentContainer
import javax.inject.Inject

class AfterDoNotNotifyPeriodWorker(
    context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {
    @Inject
    lateinit var timeManager: TimerManager

    init {
        ComponentContainer.component.inject(this)
    }

    override fun doWork(): Result {
        timeManager.start()
        return Result.success()
    }

}