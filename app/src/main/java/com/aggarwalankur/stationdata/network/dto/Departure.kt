package com.aggarwalankur.stationdata.network.dto

import com.google.gson.annotations.SerializedName

data class Departure (
    @field:SerializedName("datetime") val datetime : DateTime,
    @field:SerializedName("line_code") val lineCode : String,
    @field:SerializedName("direction") val direction : String
)

