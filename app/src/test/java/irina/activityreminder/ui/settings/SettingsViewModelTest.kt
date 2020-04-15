package irina.activityreminder.ui.settings

import android.test.mock.MockContext
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import irina.activityreminder.di.AppModule
import irina.activityreminder.di.ComponentContainer
import irina.activityreminder.di.DaggerAppComponent
import irina.activityreminder.di.SingletonModule
import irina.activityreminder.model.MockDataModule
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SettingsViewModelTest {
    private lateinit var settingsViewModel: SettingsViewModel

    @Rule
    @JvmField
    public val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var context: MockContext

    @Before
    fun beforeAllTestMethods() {
        ComponentContainer.component = DaggerAppComponent.builder()
            .singletonModule(SingletonModule)
            .appModule(AppModule(context))
            .dataModule(MockDataModule())
            .build()
        settingsViewModel = SettingsViewModel()
    }

    @Test
    fun getDoNotNotifyFromMinutes() {
        assertEquals(0, settingsViewModel.doNotNotifyFromMinutes)
    }

    @Test
    fun getDoNotNotifyFromHour() {
        assertEquals(22, settingsViewModel.doNotNotifyFromHour)
    }

    @Test
    fun getDoNotNotifyToMinutes() {
        assertEquals(0, settingsViewModel.doNotNotifyToMinutes)
    }

    @Test
    fun getDoNotNotifyToHour() {
        assertEquals(9, settingsViewModel.doNotNotifyToHour)
    }

    @Test
    fun getDoNotNotifyFrom() {
        assertEquals("22:00", settingsViewModel.doNotNotifyFrom.value)
    }

    @Test
    fun setDoNotNotifyFrom() {
        val savedValue = settingsViewModel.doNotNotifyFrom.value
        settingsViewModel.doNotNotifyFrom.value = "18:00"
        assertEquals("18:00", settingsViewModel.doNotNotifyFrom.value)
        settingsViewModel.doNotNotifyFrom.value = savedValue
    }

    @Test
    fun getDoNotNotifyTo() {
        assertEquals("09:00", settingsViewModel.doNotNotifyTo.value)
    }

    @Test
    fun setDoNotNotifyTo() {
        val savedValue = settingsViewModel.doNotNotifyTo.value
        settingsViewModel.doNotNotifyTo.value = "11:00"
        assertEquals("11:00", settingsViewModel.doNotNotifyTo.value)
        settingsViewModel.doNotNotifyTo.value = savedValue
    }

    @Test
    fun getReminderPeriod() {
        assertEquals("30", settingsViewModel.reminderPeriod)
    }

    @Test
    fun setReminderPeriod() {
        val savedValue = settingsViewModel.reminderPeriod
        settingsViewModel.reminderPeriod = "25"
        assertEquals("25", settingsViewModel.reminderPeriod)
        settingsViewModel.reminderPeriod = savedValue
    }
}