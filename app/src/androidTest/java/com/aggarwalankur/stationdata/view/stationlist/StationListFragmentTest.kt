package com.aggarwalankur.stationdata.view.stationlist

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.MediumTest
import com.aggarwalankur.stationdata.*
import com.aggarwalankur.stationdata.data.FakeRepository
import com.aggarwalankur.stationdata.data.StationRepository
import com.aggarwalankur.stationdata.util.EspressoIdlingResource
import com.google.common.truth.Truth
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@MediumTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
class StationListFragmentTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var repository: StationRepository

    // An Idling Resource that waits for Data Binding to have no pending bindings
    private val dataBindingIdlingResource = DataBindingIdlingResource()

    @Before
    fun init() {
        // Populate @Inject fields in test class
        hiltRule.inject()
    }

    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun noUIElementsSeenOnEmptyList() {

        launchFragmentInHiltContainer<StationListFragment>()

        onView(withId(R.id.swipe_layout)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.emptyListTv)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.retry_button)).check(matches(withEffectiveVisibility(Visibility.GONE)))
    }

    @Test
    fun errorFromRepo() {
        //Asserting that Replacement of modules works correctly
        Truth.assertThat(repository).isInstanceOf(FakeRepository::class.java)
        (repository as FakeRepository).setReturnError(true)

        launchFragmentInHiltContainer<StationListFragment>()

        onView(withId(R.id.emptyListTv)).check(matches(isDisplayed()))
        onView(withId(R.id.retry_button)).check(matches(isDisplayed()))
    }

    @Test
    fun errorOnceDataNextFromRepo() {
        (repository as FakeRepository).setReturnError(true)
        (repository as FakeRepository).addDepartureToList(timestamp = 1643455800,
            lineCode = "L01", direction = "ABC", timezone = "GMT-01:00")
        (repository as FakeRepository).addDepartureToList(timestamp = 1643456000,
            lineCode = "L02", direction = "XYZ")

        launchFragmentInHiltContainer<StationListFragment>()

        onView(withId(R.id.emptyListTv)).check(matches(isDisplayed()))
        onView(withId(R.id.retry_button)).check(matches(isDisplayed()))

        //Now retry
        (repository as FakeRepository).setReturnError(false)
        onView(withId(R.id.retry_button)).perform(click())

        //Check visibility of all views
        onView(withId(R.id.swipe_layout)).check(matches(isDisplayed()))
        onView(withId(R.id.emptyListTv)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.retry_button)).check(matches(withEffectiveVisibility(Visibility.GONE)))

        //Also check all data
        onView(withText("ABC")).check(matches(isDisplayed()))
        onView(withText("L01")).check(matches(isDisplayed()))
        onView(withText("10:30")).check(matches(isDisplayed())) //timestamp converted to time

        onView(withText("XYZ")).check(matches(isDisplayed()))
        onView(withText("L02")).check(matches(isDisplayed()))
        onView(withText("12:33")).check(matches(isDisplayed())) //timestamp converted to time

    }
}