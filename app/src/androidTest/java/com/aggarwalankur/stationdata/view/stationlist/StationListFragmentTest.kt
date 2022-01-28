package com.aggarwalankur.stationdata.view.stationlist

import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.MediumTest
import com.aggarwalankur.stationdata.MainActivity
import com.aggarwalankur.stationdata.R
import com.aggarwalankur.stationdata.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test

@MediumTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
class StationListFragmentTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private fun launchActivity(): ActivityScenario<MainActivity>? {
        val activityScenario = launch(MainActivity::class.java)

        return activityScenario
    }

    @Test
    fun basicFragmentLaunch() {

        // WHEN - On startup
        launchActivity()

        onView(withId(R.id.button)).check(matches(isDisplayed())).check(matches(withText(R.string.app_name)))

        onView(withId(R.id.button)).perform(click()).check(matches(withText(R.string.ok)))
    }

    @Test
    fun basicFragmentLaunchAgain() {

        launchFragmentInHiltContainer<StationListFragment>()

        onView(withId(R.id.button)).check(matches(isDisplayed())).check(matches(withText(R.string.app_name)))

        onView(withId(R.id.button)).perform(click()).check(matches(withText(R.string.ok)))
    }
}