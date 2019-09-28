package com.dxworks.rgbcolors.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Readings(
    @Json(name = "readingId") var readingId:String?,
    @Json(name = "temperature") var temperature:Int?,
    @Json(name = "humidity") var humidity:Int?,
    @Json(name = "timeStamp") var timeStamp:String?
)