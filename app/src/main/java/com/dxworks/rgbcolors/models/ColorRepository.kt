package com.dxworks.rgbcolors.models

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.smartarmenia.dotnetcoresignalrclientjava.HubConnection
import com.smartarmenia.dotnetcoresignalrclientjava.HubConnectionListener
import com.smartarmenia.dotnetcoresignalrclientjava.HubMessage
import com.smartarmenia.dotnetcoresignalrclientjava.WebSocketHubConnectionP2
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import javax.inject.Inject

class ColorRepository : ViewModel {

    lateinit var hubConnection:HubConnection
    val hubUrl:String = "http://192.168.0.18:86/ColorHub"
    var keepConnAlive = false

    var isConnectedLiveData = MutableLiveData<Boolean>()

    @Inject
    constructor(context: Context) : super(){

    }


    fun connectToHub() {
            try {
                hubConnection = WebSocketHubConnectionP2(hubUrl, "")
                hubConnection.connect()
                hubConnection.addListener(object : HubConnectionListener {
                    override fun onConnected() {
                        isConnectedLiveData.postValue(true)
                    }

                    override fun onMessage(message: HubMessage?) {

                    }

                    override fun onDisconnected() {
                        isConnectedLiveData.postValue(false)
                        when(keepConnAlive){
                            true -> connectToHub()
                        }
                    }

                    override fun onError(exception: Exception?) {
                        isConnectedLiveData.postValue(false)
                    }
                })
            }
            catch (e:Exception){

            }
    }

    fun disconnectFromHub(){
        try{
            when {
                isConnectedLiveData.value != null -> if (isConnectedLiveData.value!!) {
                    hubConnection.disconnect()
                }
            }
        }catch (e:Exception){

        }
    }

    override fun onCleared() {
        super.onCleared()
        try{
            when {
                isConnectedLiveData.value != null -> if (isConnectedLiveData.value!!) {
                    hubConnection.disconnect()
                }
            }
        }catch (e:Exception){

        }
    }

    fun sendColor(color:ColorMoshi){
        when {
            isConnectedLiveData.value != null -> if (isConnectedLiveData.value!!) {
                val moshi = Moshi.Builder().build()
                val adapter:JsonAdapter<ColorMoshi> = moshi.adapter(ColorMoshi::class.java)
                hubConnection.invoke("sendColor", adapter.toJson(color))
            }
        }
    }

}