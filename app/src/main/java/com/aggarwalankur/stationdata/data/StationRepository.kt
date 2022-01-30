package com.aggarwalankur.stationdata.data

import com.aggarwalankur.stationdata.network.Departure

interface StationRepository {
    suspend fun getStationDataFromApi () : List<Departure>
}