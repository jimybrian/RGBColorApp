package com.dxworks.rgbcolors.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dxworks.rgbcolors.R
import com.dxworks.rgbcolors.databinding.FragmentReadingsBinding
import com.dxworks.rgbcolors.di.ViewModelFactory
import com.dxworks.rgbcolors.models.Readings
import com.dxworks.rgbcolors.models.ReadingsRepository
import com.dxworks.rgbcolors.utils.UtilMethods
import com.github.mikephil.charting.components.LegendEntry
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import dagger.android.support.DaggerFragment
import java.util.ArrayList
import javax.inject.Inject

class ReadingsFragment : DaggerFragment(){

    lateinit var binding:FragmentReadingsBinding

    lateinit var readingsRepo:ReadingsRepository

    @Inject
    lateinit var factory: ViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_readings, container, false)
        readingsRepo = ViewModelProvider(this@ReadingsFragment, factory).get(ReadingsRepository::class.java)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        getReadings()
    }

    fun getReadings(){
        val currentDate = UtilMethods.getFormattedDate()
        readingsRepo.getReadings(currentDate!!).observe(this, Observer {
            t ->
            if(t != null){
                binding.txTemperature.setText(t.first().temperature.toString() + " C")
                binding.txHumidity.setText(t.first().humidity.toString() + " %")

                setTempChart(t)
                setHumChart(t)
            }
        })
    }
    
    fun setTempChart(t:List<Readings>){
        val lsBarEntries = ArrayList<BarEntry>()
        val lsLabels = ArrayList<String>()
        val lsColors = ArrayList<Int>()
        val lsEntries = ArrayList<LegendEntry>()

        val xAxis = binding.brTemp.getXAxis()
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM)
        xAxis.setTextSize(12f)
        xAxis.setTextColor(getResources().getColor(R.color.transparent))

        val yAxis = binding.brTemp.getAxisLeft()
        yAxis.setAxisMinimum(0f)
        yAxis.setTextSize(12f) // set the text size


        val yAxis2 = binding.brTemp.getAxisRight()
        yAxis2.setAxisMinimum(0f)
        yAxis2.setTextSize(12f) // set the text size

        var x = 0
        while (x < t.size) {
            val te = t[x]
            var bE = BarEntry(x.toFloat(), te.temperature!!.toFloat(), "Temperature")

            lsBarEntries.add(bE)
            lsLabels.add("Temp")
            lsColors.add(resources.getColor(R.color.color_blue))
            val lgEntry = LegendEntry()
            lsEntries.add(lgEntry)
            x += 1
        }

        val bDataset = BarDataSet(lsBarEntries, getString(R.string.temp_trend))
        bDataset.colors = lsColors
        bDataset.stackLabels = lsLabels.toTypedArray()
        val bData = BarData(bDataset)
        binding.brTemp.getDescription().setEnabled(false)
        binding.brTemp.setData(bData)
        binding.brTemp.getLegend().setCustom(lsEntries)
        binding.brTemp.notifyDataSetChanged()
        binding.brTemp.setClickable(false)
        binding.brTemp.invalidate()
    }
    
    fun setHumChart(t:List<Readings>){
        val lsBarEntries = ArrayList<BarEntry>()
        val lsLabels = ArrayList<String>()
        val lsColors = ArrayList<Int>()
        val lsEntries = ArrayList<LegendEntry>()

        val xAxis = binding.brHum.getXAxis()
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM)
        xAxis.setTextSize(12f)
        xAxis.setTextColor(getResources().getColor(R.color.transparent))

        val yAxis = binding.brHum.getAxisLeft()
        yAxis.setAxisMinimum(0f)
        yAxis.setTextSize(12f) // set the text size


        val yAxis2 = binding.brHum.getAxisRight()
        yAxis2.setAxisMinimum(0f)
        yAxis2.setTextSize(12f) // set the text size

        var x = 0
        while (x < t.size) {
            val te = t[x]
            var bE = BarEntry(x.toFloat(), te.humidity!!.toFloat(), "Humidity")

            lsBarEntries.add(bE)
            lsLabels.add("Temp")
            lsColors.add(resources.getColor(R.color.color_green))
            val lgEntry = LegendEntry()
            lsEntries.add(lgEntry)
            x += 1
        }

        val bDataset = BarDataSet(lsBarEntries, getString(R.string.temp_trend))
        bDataset.colors = lsColors
        bDataset.stackLabels = lsLabels.toTypedArray()
        val bData = BarData(bDataset)
        binding.brHum.getDescription().setEnabled(false)
        binding.brHum.setData(bData)
        binding.brHum.getLegend().setCustom(lsEntries)
        binding.brHum.notifyDataSetChanged()
        binding.brHum.setClickable(false)
        binding.brHum.invalidate()
    }

}