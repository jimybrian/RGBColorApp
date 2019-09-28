package com.dxworks.rgbcolors.models

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dxworks.rgbcolors.ui.interfaces.ApiInterfaces
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class ReadingsRepository @Inject constructor(
    var retrofit: Retrofit
) : ViewModel() {

    var readingsMutableLiveData = MutableLiveData<List<Readings>>()

    fun getReadings(date:String) : MutableLiveData<List<Readings>>{
        var call = retrofit.create(ApiInterfaces::class.java)
        call.getReadings(date).enqueue(object:Callback<List<Readings>>{
            override fun onFailure(call: Call<List<Readings>>, t: Throwable) {
                readingsMutableLiveData.postValue(null)
            }

            override fun onResponse(
                call: Call<List<Readings>>,
                response: Response<List<Readings>>
            ) {
                readingsMutableLiveData.postValue(response.body())
            }

        })
        return readingsMutableLiveData
    }

}