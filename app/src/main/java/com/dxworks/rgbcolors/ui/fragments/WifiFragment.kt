package com.dxworks.rgbcolors.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.dxworks.rgbcolors.R
import com.dxworks.rgbcolors.databinding.FragmentWifiBinding
import com.dxworks.rgbcolors.models.ColorMoshi
import com.dxworks.rgbcolors.ui.interfaces.ApiInterfaces
import com.dxworks.rgbcolors.utils.SharedPreferences
import dagger.android.support.DaggerFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


class WifiFragment : DaggerFragment(){

    @Inject
    lateinit var apiService:ApiInterfaces

    @Inject
    lateinit var shPref:SharedPreferences

    lateinit var binding:FragmentWifiBinding
    var pickedColor: ColorMoshi = ColorMoshi( 0, 0,0,0, 0)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_wifi, container, false)
        shPref = SharedPreferences(activity!!)

        val intialBlue = shPref.readSavedColor()
        binding.vwSelectedColor.setBackgroundColor(intialBlue)
        val brightness = shPref.readBrightness()
        binding.skBar.progress = brightness
        pickedColor.setColor(intialBlue, binding.skBar.progress)


        binding.clrPicker.addOnColorChangedListener { c ->
            binding.vwSelectedColor.setBackgroundColor(c)
            pickedColor.setColor(c, binding.skBar.progress)
            sendColor()
        }

        binding.skBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                pickedColor.setBrightnessVal(progress)
                shPref.saveBrightness(progress)

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                //TODO
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                //TODO
            }

        })

        binding.lnColorPicker.setOnColorChangedListener { c ->
            binding.vwSelectedColor.setBackgroundColor(c)
            pickedColor.setColor(binding.lnColorPicker.color, binding.skBar.progress)
            sendColor()
        }

        //Setup Buttons
        binding.vwButtons.btnWhite.setOnClickListener { v ->
            val white = resources.getColor(R.color.white)
            binding.vwSelectedColor.setBackgroundColor(white)
            pickedColor.setColor(white, binding.skBar.progress)
            sendColor()
        }

        binding.vwButtons.btnOff.setOnClickListener { v ->
            val black = resources.getColor(R.color.black)
            binding.vwSelectedColor.setBackgroundColor(black)
            sendColor()
        }

        binding.vwButtons.btnRed.setOnClickListener { v ->
            val red = resources.getColor(R.color.rgbRed)
            binding.vwSelectedColor.setBackgroundColor(red)
            pickedColor.setColor(red, binding.skBar.progress)
            sendColor()
        }

        binding.vwButtons.btnBlue.setOnClickListener { v ->
            val blue = resources.getColor(R.color.rgbBlue)
            binding.vwSelectedColor.setBackgroundColor(blue)
            pickedColor.setColor(blue, binding.skBar.progress)
            sendColor()
        }

        binding.vwButtons.btnGreen.setOnClickListener { v ->
            val green = resources.getColor(R.color.rgbGreen)
            binding.vwSelectedColor.setBackgroundColor(green)
            pickedColor.setColor(green, binding.skBar.progress)
            sendColor()
        }

        return binding.root
    }

    fun sendColor(){
        if(!binding.rdoSaveColor.isChecked) {
            val call = apiService.sendColor(pickedColor)
            Toast.makeText(this@WifiFragment.activity, "Sending Color", Toast.LENGTH_SHORT).show()
            call.enqueue(object : Callback<ColorMoshi> {
                override fun onFailure(call: Call<ColorMoshi>, t: Throwable) {
                    Toast.makeText(
                        this@WifiFragment.activity,
                        "Could not send color, try again later",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onResponse(call: Call<ColorMoshi>, response: Response<ColorMoshi>) {
                    Toast.makeText(this@WifiFragment.activity, "Color Sent", Toast.LENGTH_SHORT)
                        .show()
                    shPref.saveBrightness(pickedColor.colorInt)
                }
            })
        }else{
            val call = apiService.addColor(pickedColor)
            Toast.makeText(this@WifiFragment.activity, "Saving Color", Toast.LENGTH_SHORT).show()
            call.enqueue(object : Callback<ColorMoshi> {
                override fun onFailure(call: Call<ColorMoshi>, t: Throwable) {
                    Toast.makeText(
                        this@WifiFragment.activity,
                        "Could not saved color, try again later",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onResponse(call: Call<ColorMoshi>, response: Response<ColorMoshi>) {
                    Toast.makeText(this@WifiFragment.activity, "Color Sent", Toast.LENGTH_SHORT)
                        .show()
                    shPref.saveBrightness(pickedColor.colorInt)
                }
            })
        }
    }
}