package com.dxworks.rgbcolors.models

import android.graphics.Color

class ColorItem() {
    init {

    }
    var red:Int = 0
    var blue:Int = 0
    var green:Int = 0
    var brightness:Int = 0


    fun setColor(color:Int, brightness:Int){
        this@ColorItem.red = Color.red(color)
        this@ColorItem.green = Color.green(color)
        this@ColorItem.blue = Color.blue(color)
        this@ColorItem.brightness = brightness
    }

    fun setBrightnessVal(brightness: Int){
        this@ColorItem.brightness = brightness
    }
}