package com.dxworks.rgbcolors.models

import android.graphics.Color
import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


class ColorItem() {
    init {

    }

    var red: Int = 0
    var blue: Int = 0
    var green: Int = 0
    var brightness: Int = 0


    fun setColor(color: Int, brightness: Int) {
        this@ColorItem.red = Color.red(color)
        this@ColorItem.green = Color.green(color)
        this@ColorItem.blue = Color.blue(color)
        this@ColorItem.brightness = brightness
    }

    fun setBrightnessVal(brightness: Int) {
        this@ColorItem.brightness = brightness
    }
}

@JsonClass(generateAdapter = true)
data class ColorMoshi(@Json(name = "red") var red: Int,
                        @Json(name = "blue") var blue: Int,
                        @Json(name = "green") var green: Int,
                        @Json(name = "brightness") var brightness: Int,
                        @Transient var colorInt:Int = 0){
    fun setColor(color: Int, brightness: Int) {
        this@ColorMoshi.red = Color.red(color)
        this@ColorMoshi.green = Color.green(color)
        this@ColorMoshi.blue = Color.blue(color)
        this@ColorMoshi.brightness = brightness
        this@ColorMoshi.colorInt = colorInt
    }

    fun setBrightnessVal(brightness: Int) {
        this@ColorMoshi.brightness = brightness
    }
}