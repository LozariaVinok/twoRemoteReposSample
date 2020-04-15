package irina.activityreminder.model

import android.test.mock.MockContext
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import irina.activityreminder.di.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SettingsTest {

    @Rule
    @JvmField
    public val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var context: MockContext
    private lateinit var settings: Settings

    @Before
    fun setUp() {
        ComponentContainer.component = buildTestComponent()
        settings = Settings()
    }

    private fun buildTestComponent(): AppComponent {
        return DaggerAppComponent.builder()
            .singletonModule(SingletonModule)
            .appModule(AppModule(context))
            .dataModule(MockDataModule())
            .build()
    }

    @Test
    fun getDoNotNotifyFrom() {
        assertEquals("22:00", settings.doNotNotifyFrom.value)
    }

    @Test
    fun setDoNotNotifyFrom() {
        settings.doNotNotifyFrom.setValue("21:00")
        assertEquals("21:00", settings.doNotNotifyFrom.value)
    }

    @Test
    fun saveDoNotNotifyFrom() {
        setDoNotNotifyFrom()
        val settings = Settings()
        assertEquals("21:00", settings.doNotNotifyFrom.value)
    }

    @Test
    fun getDoNotNotifyTo() {
        assertEquals("09:00", settings.doNotNotifyTo.value)
    }

    @Test
    fun setDoNotNotifyTo() {
        val value = "10:00"
        settings.doNotNotifyTo.setValue(value)
        assertEquals(value, settings.doNotNotifyTo.value)
    }

    @Test
    fun saveDoNotNotifyTo() {
        setDoNotNotifyTo()
        val settings = Settings()
        assertEquals("10:00", settings.doNotNotifyTo.value)
    }


    @Test
    fun getReminderPeriod() {
        assertEquals(1800000, settings.reminderPeriod.value)
    }

    @Test
    fun setReminderPeriod() {
        val value: Int = 3
        settings.reminderPeriod.setValue(value)
        assertEquals(value, settings.reminderPeriod.value)
    }

    @Test
    fun saveReminderPeriod() {
        val value: Int = 3
        settings.reminderPeriod.setValue(value)
        val settings = Settings()
        assertEquals(3, settings.reminderPeriod.value)
    }

}
