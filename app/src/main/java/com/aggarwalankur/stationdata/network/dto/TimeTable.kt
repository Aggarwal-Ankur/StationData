package com.aggarwalankur.stationdata.network.dto
import com.google.gson.annotations.SerializedName

data class TimeTable (
    @field:SerializedName( "departures")
    val departures : List<Departure> = emptyList()
)