package com.aggarwalankur.stationdata.data

import com.aggarwalankur.stationdata.BuildConfig
import com.aggarwalankur.stationdata.network.dto.Departure
import com.aggarwalankur.stationdata.network.StationApiService

class StationRepositoryImpl (
    private val service: StationApiService
) : StationRepository{
    override suspend fun getStationDataFromApi () : List<Departure> {
        return service.searchGithubUsers(BuildConfig.API_KEY).timeTable.departures
    }
}