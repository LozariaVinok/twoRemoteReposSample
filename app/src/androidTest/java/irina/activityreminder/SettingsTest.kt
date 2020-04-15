package irina.activityreminder


import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import irina.activityreminder.ui.MainActivity
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class SettingsTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun settingsTest() {
        val appCompatImageButton = onView(
            allOf(
                withId(R.id.settings_button), withContentDescription("Settings"),
                childAtPosition(
                    allOf(
                        withId(R.id.main),
                        childAtPosition(
                            withId(R.id.nav_host_fragment),
                            0
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatImageButton.perform(click())

        val doNotNotifyFromTextView = onView(
            allOf(
                withId(R.id.do_not_notify_from_value),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_host_fragment),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        doNotNotifyFromTextView.perform(click())

        var timerPicker = onView(
            allOf(
                withClassName(`is`("android.widget.TimePicker")),

                isDisplayed()
            )
        )
        timerPicker.perform(setTime(18, 0))

        var okButton = onView(
            allOf(
                withText("OK")
            )
        )
        okButton.perform(click())

        doNotNotifyFromTextView.check(matches(withText("18:00")))

        val doNotNotifyToTextView = onView(
            allOf(
                withId(R.id.do_not_notify_to_value)
            )
        )
        doNotNotifyToTextView.perform(click())


        timerPicker.perform(click())

        timerPicker = onView(
            allOf(
                withClassName(`is`("android.widget.TimePicker")),

                isDisplayed()
            )
        )
        timerPicker.perform(setTime(7, 0))

        okButton = onView(
            allOf(
                withText("OK")
            )
        )
        okButton.perform(click())
        doNotNotifyToTextView.check(matches(withText("07:00")))

        val increaseButton = onView(
            allOf(
                withId(R.id.increase_value)
            )
        )
        increaseButton.perform(click())

        val reminderPeriodValue = onView(
            allOf(
                withId(R.id.reminder_period_value)
            )
        )
        reminderPeriodValue.check(matches(withText("31")))

        val decreaseButton = onView(
            allOf(
                withId(R.id.decrease_value)
            )
        )
        decreaseButton.perform(click())

        reminderPeriodValue.check(matches(withText("30")))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }

    private fun setTime(hour: Int, minute: Int): ViewAction {
        return object : ViewAction {
            override fun perform(uiController: UiController, view: View) {
                val tp = view as TimePicker
                tp.hour = hour
                tp.minute = minute
            }

            override fun getDescription(): String {
                return "Set the passed time into the TimePicker";
            }

            override fun getConstraints(): Matcher<View> {
                return isAssignableFrom(TimePicker::class.java)
            }
        };
    }
}
