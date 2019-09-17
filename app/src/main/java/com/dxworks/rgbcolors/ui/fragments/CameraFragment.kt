package com.dxworks.rgbcolors.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.dxworks.rgbcolors.R
import com.dxworks.rgbcolors.databinding.FragmentReadingsBinding
import dagger.android.support.DaggerFragment

class CameraFragment : DaggerFragment(){

    lateinit var binding:FragmentReadingsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_readings, container, false)
        return binding.root
    }

}