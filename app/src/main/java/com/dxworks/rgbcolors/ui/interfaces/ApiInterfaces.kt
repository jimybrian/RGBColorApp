package com.dxworks.rgbcolors.ui.interfaces


import com.dxworks.rgbcolors.models.ColorMoshi
import com.dxworks.rgbcolors.models.Readings
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiInterfaces{

    @POST("/api/Colors/sendcolor")
    fun sendColor(@Body color:ColorMoshi) :Call<ColorMoshi>

    @POST("/api/Colors/addcolor")
    fun addColor(@Body color:ColorMoshi) :Call<ColorMoshi>

    @GET("api/Readings/getReadings")
    fun getReadings(@Query("Date") date:String):Call<List<Readings>>
}