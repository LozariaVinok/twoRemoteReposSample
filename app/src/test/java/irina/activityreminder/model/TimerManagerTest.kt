package irina.activityreminder.model

import android.test.mock.MockContext
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import irina.activityreminder.di.*
import irina.activityreminder.utils.TimeUtils
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TimerManagerTest {

    @Rule
    @JvmField
    public val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var context: MockContext


    @Before
    fun setUp() {
        ComponentContainer.component = buildTestComponent()
    }

    private fun buildTestComponent(): AppComponent {
        return DaggerAppComponent.builder()
            .singletonModule(SingletonModule)
            .appModule(AppModule(context))
            .dataModule(MockDataModule())
            .build()
    }

    @Test
    fun progress50Percent() {
        val timerManager = initTimerManager()
        timerManager.updateProgress(TimeUtils.timeToDate(12, 30).time)
        timerManager.updateProgress(TimeUtils.timeToDate(12, 45).time)
        Assert.assertEquals(50, timerManager.progress.value!!.toInt())
    }

    @Test
    fun progress0PercentAfterCompletion() {
        val timerManager = initTimerManager()
        timerManager.updateProgress(TimeUtils.timeToDate(12, 30).time)
        timerManager.updateProgress(TimeUtils.timeToDate(13, 0).time)
        timerManager.updateProgress(TimeUtils.timeToDate(13, 1).time)
        Assert.assertEquals(3, timerManager.progress.value!!.toInt())
    }

    @Test
    fun resetProgress() {
        val timerManager = initTimerManager()
        timerManager.updateProgress(TimeUtils.timeToDate(12, 9).time)
        timerManager.updateProgress(TimeUtils.timeToDate(13, 0).time)
        timerManager.reset()
        timerManager.updateProgress(TimeUtils.timeToDate(13, 1).time)
        timerManager.updateProgress(TimeUtils.timeToDate(13, 2).time)
        Assert.assertEquals(3, timerManager.progress.value!!.toInt())
    }

    @Test
    fun doNotNotifyPeriod() {
        val timerManager = initTimerManager()
        timerManager.updateProgress(TimeUtils.timeToDate(12, 55).time)
        timerManager.updateProgress(TimeUtils.timeToDate(23, 45).time)

        Assert.assertEquals(33300000, timerManager.doNotNotifyEvent.value!!.peekContent())
    }

    private fun initTimerManager(): TimerManager {
        val timerManager = TimerManager()
        return timerManager
    }


};