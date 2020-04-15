package irina.activityreminder


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import irina.activityreminder.ui.SchedulerService

internal class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let { SchedulerService.startServiceIfItIsNotRunning(it) }
    }

}