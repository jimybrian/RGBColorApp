package com.dxworks.rgbcolors.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.dxworks.rgbcolors.R
import com.dxworks.rgbcolors.databinding.FragmentBluetoothBinding

class BluetoothFragment : Fragment(){

    lateinit var binding:FragmentBluetoothBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bluetooth, container, false)
        return binding.root
    }

}