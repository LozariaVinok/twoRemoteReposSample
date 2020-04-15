package irina.activityreminder.ui

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.Observer
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import irina.activityreminder.R
import irina.activityreminder.di.ComponentContainer.component
import irina.activityreminder.model.AfterDoNotNotifyPeriodWorker
import irina.activityreminder.model.Settings
import irina.activityreminder.model.TimerManager
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class SchedulerService : LifecycleService() {

    @Inject
    lateinit var timerManager: TimerManager

    @Inject
    lateinit var settings: Settings

    private var reminderNotification: Notification? = null
    private var foregroundNotificationBuilder: NotificationCompat.Builder? = null
    private var manager: NotificationManager? = null
    private val reminderNotificationId = 123
    private val foregroundNotificationId = 256


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        component.inject(this)
        createNotificationChannel(CHANNEL_REMINDER_ID)

        reminderNotification = NotificationCompat.Builder(
            this,
            CHANNEL_REMINDER_ID
        )
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Time to get up")
            .setTimeoutAfter(30000)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        timerManager.remindEvent.observe(this, Observer { onShouldRemind() })
        timerManager.progress.observe(this, Observer { onReminderProgressUpdated(it) })
        timerManager.doNotNotifyEvent.observe(this, Observer { doNotNotifyMode(it.peekContent()) })
        timerManager.start()

        return START_NOT_STICKY
    }

    override fun onCreate() {
        super.onCreate()
        manager = getSystemService(
            Context.NOTIFICATION_SERVICE
        ) as NotificationManager

        createNotificationChannel(CHANNEL_ID)
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0, notificationIntent, 0
        )
        foregroundNotificationBuilder = NotificationCompat.Builder(
            this,
            CHANNEL_ID
        )
            .setContentTitle(getString(R.string.app_name))
            .setContentText("")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentIntent(pendingIntent)
            .setNotificationSilent()
        startForeground(foregroundNotificationId, foregroundNotificationBuilder?.build())
    }

    override fun onDestroy() {
        super.onDestroy()
        timerManager.stop()
        stopForeground(true)
        manager?.cancelAll()
    }

    private fun createNotificationChannel(channelId: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                channelId,
                "Foreground Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            manager?.createNotificationChannel(serviceChannel)
        }
    }

    private fun onShouldRemind() {
        with(NotificationManagerCompat.from(applicationContext)) {
            reminderNotification?.let { notify(reminderNotificationId, it) }
        }
    }

    private fun onReminderProgressUpdated(progress: Float) {
        foregroundNotificationBuilder?.setProgress(10000, (progress * 100).toInt(), false)
        foregroundNotificationBuilder?.setContentTitle(getString(R.string.app_name))
        manager?.notify(foregroundNotificationId, foregroundNotificationBuilder?.build());
    }

    companion object {
        fun startServiceIfItIsNotRunning(context: Context) {
            if (isServiceNotRunning(
                    SchedulerService::class.java,
                    context
                )
            ) {
                val serviceIntent = Intent(context, SchedulerService::class.java)
                ContextCompat.startForegroundService(context, serviceIntent)
            }
        }

        fun stopService(context: Context) {
            val serviceIntent = Intent(context, SchedulerService::class.java)
            context.stopService(serviceIntent)
        }

        private fun isServiceNotRunning(serviceClass: Class<*>, context: Context): Boolean {
            val manager =
                context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager?
            for (service in manager!!.getRunningServices(Int.MAX_VALUE)) {
                if (serviceClass.name == service.service.className) {
                    return false
                }
            }
            return true
        }

        private const val CHANNEL_REMINDER_ID = "Reminder"
        private const val CHANNEL_ID: String = "Scheduler"
    }

    private fun doNotNotifyMode(timeTill: Long) {
        foregroundNotificationBuilder?.setProgress(0, 0, false)
        foregroundNotificationBuilder?.setContentTitle(
            "Do not notify ${settings.doNotNotifyPeriodString
            }"
        )

        manager?.notify(foregroundNotificationId, foregroundNotificationBuilder?.build())
        val oneTimeWorkRequest =
            OneTimeWorkRequestBuilder<AfterDoNotNotifyPeriodWorker>().setInitialDelay(
                timeTill,
                TimeUnit.MILLISECONDS
            )
                .addTag("DoNotNotifyPeriod").build()

        WorkManager.getInstance(this).cancelAllWorkByTag("DoNotNotifyPeriod")
        WorkManager.getInstance(this).enqueue(oneTimeWorkRequest)
        timerManager.stop()

    }


}
