package com.aggarwalankur.stationdata.view.stationlist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.aggarwalankur.stationdata.MainCoroutineRule
import com.aggarwalankur.stationdata.data.FakeRepository
import com.aggarwalankur.stationdata.getOrAwaitValue
import com.aggarwalankur.stationdata.network.dto.DateTime
import com.aggarwalankur.stationdata.network.dto.Departure
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class StationsViewModelTest {
    private lateinit var viewModel: StationsViewModel

    // Use a fake repository to be injected into the viewmodel
    private lateinit var repo: FakeRepository

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        repo = FakeRepository()
        viewModel = StationsViewModel(repo)
    }

    @After
    fun teardown() {
        repo.clearDepartureList()
    }

    @Test
    fun `that initial loading state is Loading and changes to success`() {
        mainCoroutineRule.pauseDispatcher()
        //Create viewmodel after pause(), this is a special testcase
        viewModel = StationsViewModel(repo)

        val initialState = viewModel.status.getOrAwaitValue()

        assertThat(initialState).isInstanceOf(LoadStatus.LOADING::class.java)

        mainCoroutineRule.resumeDispatcher()

        val nextstate = viewModel.status.getOrAwaitValue()

        assertThat(nextstate).isInstanceOf(LoadStatus.SUCCESS::class.java)

        assertThat(viewModel.departures.getOrAwaitValue()).isEmpty()
    }

    @Test
    fun `that when data added to repo, refresh triggers change in viewmodel data`() {
        //Initial is empty
        assertThat(viewModel.departures.getOrAwaitValue()).isEmpty()

        repo.addDepartureToList(timestamp = 1643455800, lineCode = "L01", direction = "ABC")
        repo.addDepartureToList(timestamp = 1643456000, lineCode = "L02", direction = "XYZ")

        val dateTime = DateTime (1643458000, "GMT-01:00")
        val third = Departure(dateTime, "L03", "PQR")

        repo.addDepartureToList(third)

        viewModel.refresh()

        val newList = viewModel.departures.getOrAwaitValue()
        assertThat(newList.size).isEqualTo(3)
        assertThat(newList.contains(third))
    }

    @Test
    fun `that error in repo sets error-state in viewmodel`() {
        assertThat(viewModel.status.getOrAwaitValue()).isInstanceOf(LoadStatus.SUCCESS::class.java)

        repo.addDepartureToList(timestamp = 1643455800, lineCode = "L01", direction = "ABC")

        repo.setReturnError(true)

        //Refresh viewmodel
        viewModel.refresh()
        assertThat(viewModel.status.getOrAwaitValue()).isInstanceOf(LoadStatus.ERROR::class.java)
    }

}