package com.aggarwalankur.stationdata.data

import com.aggarwalankur.stationdata.network.dto.DateTime
import com.aggarwalankur.stationdata.network.dto.Departure
import kotlinx.coroutines.delay
import java.lang.Exception
import javax.inject.Inject

class FakeRepository @Inject constructor() : StationRepository {

    private val departureList = ArrayList<Departure>()

    private var shouldReturnError = false

    private var fakeDelay = 0L

    //Helper to fake errors
    fun setReturnError(value: Boolean) {
        shouldReturnError = value
    }

    //Helper to fake a delay
    fun setDelay (delay : Long) {
        fakeDelay = delay
    }

    //Helper to add fake data
    fun addDepartureToList (timestamp : Long, timezone : String = "GMT+01:00",
                            lineCode : String, direction : String) {
        val dateTime = DateTime (timestamp, timezone)
        val departure = Departure(dateTime, lineCode, direction)

        departureList.add(departure)
    }

    fun addDepartureToList (departure: Departure) {
        departureList.add(departure)
    }

    fun clearDepartureList() {
        departureList.clear()
    }

    override suspend fun getStationDataFromApi(): List<Departure> {
        if (shouldReturnError) throw Exception("Fake Error")

        if (fakeDelay > 0) delay(fakeDelay)

        return departureList
    }
}