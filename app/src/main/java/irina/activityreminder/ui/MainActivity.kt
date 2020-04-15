package irina.activityreminder.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import irina.activityreminder.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        SchedulerService.startServiceIfItIsNotRunning(
            this
        )
    }
}
