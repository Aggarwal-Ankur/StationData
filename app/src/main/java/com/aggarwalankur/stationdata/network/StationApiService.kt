package com.aggarwalankur.stationdata.network

import com.aggarwalankur.stationdata.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Header

interface StationApiService {
    @GET("mobile/v1/network/station/1/timetable.json")
    suspend fun searchGithubUsers(
        @Header("X-Api-Authentication") apiKey : String): StationApiResponse
}