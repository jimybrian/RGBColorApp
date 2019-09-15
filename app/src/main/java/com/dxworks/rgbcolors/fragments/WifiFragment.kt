package com.dxworks.rgbcolors.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.dxworks.rgbcolors.R
import com.dxworks.rgbcolors.databinding.FragmentWifiBinding
import com.dxworks.rgbcolors.models.ColorItem
import com.dxworks.rgbcolors.utils.SharedPreferences

class WifiFragment : Fragment(){

    lateinit var binding:FragmentWifiBinding
    lateinit var shPref:SharedPreferences
    var pickedColor: ColorItem = ColorItem()

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

        }

        //Setup Buttons
        binding.vwButtons.btnWhite.setOnClickListener { v ->
            val white = resources.getColor(R.color.white)
            binding.vwSelectedColor.setBackgroundColor(white)
            pickedColor.setColor(white, binding.skBar.progress)

        }

        binding.vwButtons.btnOff.setOnClickListener { v ->
            val black = resources.getColor(R.color.black)
            binding.vwSelectedColor.setBackgroundColor(black)

        }

        binding.vwButtons.btnRed.setOnClickListener { v ->
            val red = resources.getColor(R.color.rgbRed)
            binding.vwSelectedColor.setBackgroundColor(red)
            pickedColor.setColor(red, binding.skBar.progress)

        }

        binding.vwButtons.btnBlue.setOnClickListener { v ->
            val blue = resources.getColor(R.color.rgbBlue)
            binding.vwSelectedColor.setBackgroundColor(blue)
            pickedColor.setColor(blue, binding.skBar.progress)

        }

        binding.vwButtons.btnGreen.setOnClickListener { v ->
            val green = resources.getColor(R.color.rgbGreen)
            binding.vwSelectedColor.setBackgroundColor(green)
            pickedColor.setColor(green, binding.skBar.progress)

        }

        return binding.root
    }

}