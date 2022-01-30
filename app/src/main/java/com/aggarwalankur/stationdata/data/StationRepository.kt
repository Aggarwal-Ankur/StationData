package com.aggarwalankur.stationdata.data

import com.aggarwalankur.stationdata.network.dto.Departure

interface StationRepository {
    suspend fun getStationDataFromApi () : List<Departure>
}