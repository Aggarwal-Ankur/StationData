package com.aggarwalankur.stationdata.network.dto

import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.*

//Having the conversion in data class is an option, but want to see if this can be done in ViewModel
private val simpleDateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

data class DateTime (
    @field:SerializedName("timestamp") val timestamp : Long,
    @field:SerializedName("tz") val timezone : String
) {
    val time : String
        get() {
            simpleDateFormat.timeZone = TimeZone.getTimeZone(timezone)
            return simpleDateFormat.format(timestamp * 1000L)
        }

}