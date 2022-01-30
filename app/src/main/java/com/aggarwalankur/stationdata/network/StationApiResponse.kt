package com.aggarwalankur.stationdata.network

import com.aggarwalankur.stationdata.network.dto.TimeTable
import com.google.gson.annotations.SerializedName

data class StationApiResponse (
    @field:SerializedName( "timetable")
    val timeTable: TimeTable
)