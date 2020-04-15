package irina.activityreminder


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import irina.activityreminder.ui.MainActivity
import irina.activityreminder.ui.widget.StartStopButton
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@LargeTest
@RunWith(AndroidJUnit4::class)
class StartStopButtonTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun startStopSwitchTest() {
        val startStopButton = onView(
            allOf(
                withId(R.id.stop_start_button),
                childAtPosition(
                    allOf(
                        withId(R.id.main),
                        childAtPosition(
                            withId(R.id.nav_host_fragment),
                            0
                        )
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        startStopButton.perform(click())
        startStopButton.check(matches(withIsStartedField(false)))
        startStopButton.perform(click())
        startStopButton.check(matches(withIsStartedField(true)))
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

    private fun withIsStartedField(expectedIsStartedValue: Boolean): Matcher<View?>? {
        return object : BoundedMatcher<View?, StartStopButton>(StartStopButton::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("Checking the matcher on received view: ")
                description.appendText("with expectedIsStartedValue = $expectedIsStartedValue")
            }

            override fun matchesSafely(foundView: StartStopButton): Boolean {
                return foundView.isStarted == expectedIsStartedValue
            }
        }
    }

}

