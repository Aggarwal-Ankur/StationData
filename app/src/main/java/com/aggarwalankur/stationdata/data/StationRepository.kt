package com.aggarwalankur.stationdata.data

import com.aggarwalankur.stationdata.BuildConfig
import com.aggarwalankur.stationdata.network.StationApiService
import javax.inject.Inject

class StationRepository @Inject constructor(private val service: StationApiService) {
    suspend fun getStationDataFromApi () {
        service.searchGithubUsers(BuildConfig.API_KEY)
    }
}