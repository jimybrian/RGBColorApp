package com.dxworks.rgbcolors.utils

import android.content.Context
import android.content.SharedPreferences
import com.dxworks.rgbcolors.R
import javax.inject.Inject

interface SharedPreferencesHelper{

    fun readSavedColor() : Int
    fun saveColor(color:Int)

    fun readBrightness() : Int
    fun saveBrightness(brightness:Int)
}

class SharedPreferences @Inject constructor(var context: Context) : SharedPreferencesHelper{
    var shPref:SharedPreferences
    val shaPrefName:String = "com.dxworks.rgbcolors"
    val savedColor:String = "SAVED_COLOR"
    val savedBrightness:String = "SAVED_BRIGHTNESS"

    init {
        shPref = context.getSharedPreferences(shaPrefName, Context.MODE_PRIVATE)
    }

    override fun readSavedColor(): Int {
        return shPref.getInt(savedColor, context.resources.getColor(R.color.color_blue))
    }

    override fun saveColor(color: Int) {
        shPref.edit().putInt(savedColor, color).apply()
    }

    override fun readBrightness(): Int {
        return shPref.getInt(savedBrightness, 100)
    }

    override fun saveBrightness(brightness: Int) {
        shPref.edit().putInt(savedBrightness, brightness).apply()
    }

}