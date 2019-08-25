package com.dxworks.rgbcolors.activities

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.dxworks.rgbcolors.R
import com.dxworks.rgbcolors.databinding.ActivityMainBinding
import com.dxworks.rgbcolors.models.ColorItem
import com.dxworks.rgbcolors.utils.SharedPreferences
import com.google.gson.Gson
import com.sirvar.bluetoothkit.BluetoothKit
import java.io.IOException
import java.util.*


class MainActivity : AppCompatActivity() {

    lateinit var binding:ActivityMainBinding
    var pickedColor:ColorItem = ColorItem()

    var btKit:BluetoothKit? = null
    var hc05Device:BluetoothDevice? = null

    var isConnected:Boolean = false
    var isBluetoothEnabled:Boolean = false
    var bSocket:BluetoothSocket? = null

    val tag = "MAIN_ACTIVITY"

    val deviceID = "00001101-0000-1000-8000-00805f9b34fb"

    lateinit var shPref:SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this@MainActivity, SplashActivity::class.java))
        binding = DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main)
        setSupportActionBar(binding.toolbar.toolbar)
        supportActionBar?.title = ""

        shPref = SharedPreferences(this@MainActivity)

        val intialBlue = shPref.readSavedColor()
//        binding.clrPicker.setColor(intialBlue, true)
        binding.vwSelectedColor.setBackgroundColor(intialBlue)
        val brightness = shPref.readBrightness()
        binding.skBar.progress = brightness

        pickedColor.setColor(intialBlue, binding.skBar.progress)




        binding.clrPicker.addOnColorChangedListener { c ->
            binding.vwSelectedColor.setBackgroundColor(c)
            pickedColor.setColor(c, binding.skBar.progress)
            sendColorToBluetooth(c, binding.skBar.progress)
        }

        binding.skBar.setOnSeekBarChangeListener(object:SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                pickedColor.setBrightnessVal(progress)
                shPref.saveBrightness(progress)
                sendBrightnessToBluetooth()
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
            sendColorToBluetooth(binding.lnColorPicker.color, binding.skBar.progress)
        }

        //Setup Buttons
        binding.vwButtons.btnWhite.setOnClickListener { v ->
            val white = resources.getColor(R.color.white)
            binding.vwSelectedColor.setBackgroundColor(white)
            pickedColor.setColor(white, binding.skBar.progress)
            sendColorToBluetooth(white, binding.skBar.progress)
        }

        binding.vwButtons.btnOff.setOnClickListener { v ->
            val black = resources.getColor(R.color.black)
            binding.vwSelectedColor.setBackgroundColor(black)
            switchOffLeds()
        }

        binding.vwButtons.btnRed.setOnClickListener { v ->
            val red = resources.getColor(R.color.rgbRed)
            binding.vwSelectedColor.setBackgroundColor(red)
            pickedColor.setColor(red, binding.skBar.progress)
            sendColorToBluetooth(red, binding.skBar.progress)
        }

        binding.vwButtons.btnBlue.setOnClickListener { v ->
            val blue = resources.getColor(R.color.rgbBlue)
            binding.vwSelectedColor.setBackgroundColor(blue)
            pickedColor.setColor(blue, binding.skBar.progress)
            sendColorToBluetooth(blue, binding.skBar.progress)
        }

        binding.vwButtons.btnGreen.setOnClickListener { v ->
            val green = resources.getColor(R.color.rgbGreen)
            binding.vwSelectedColor.setBackgroundColor(green)
            pickedColor.setColor(green, binding.skBar.progress)
            sendColorToBluetooth(green, binding.skBar.progress)
        }
    }

    fun switchOffLeds(){
        try {
            val black = resources.getColor(R.color.black)
            pickedColor.setColor(black, 0);
            val color = Gson().toJson(pickedColor)
            Log.d("Picked Color", color)
//            Toast.makeText(this@MainActivity, color, Toast.LENGTH_SHORT).show()
            if (isBluetoothEnabled && isConnected) {
                if (hc05Device != null) {
                    val testString = color
                    val bytes = testString.toByteArray()
                    btKit?.write(bytes)
                }
            }
        }catch (e:Exception){
            Log.d(tag, e.message)
        }
    }

    fun sendColorToBluetooth(c:Int, b:Int){
        try {
            shPref.saveBrightness(b)
            shPref.saveColor(c)
            val color = Gson().toJson(pickedColor)
            Log.d("Picked Color", color)
//            Toast.makeText(this@MainActivity, color, Toast.LENGTH_SHORT).show()
            if (isBluetoothEnabled && isConnected) {
                if (hc05Device != null) {
                    val testString = color
                    val bytes = testString.toByteArray()
                    btKit?.write(bytes)
                }
            }
        }catch (e:Exception){
            Log.d(tag, e.message)
        }
    }

    fun sendBrightnessToBluetooth(){
        try {
            val color = Gson().toJson(pickedColor)
            Log.d("Picked Color", color)
//            Toast.makeText(this@MainActivity, color, Toast.LENGTH_SHORT).show()
            if (isBluetoothEnabled && isConnected) {
                if (hc05Device != null) {
                    val testString = color
                    val bytes = testString.toByteArray()
                    btKit?.write(bytes)
                }
            }
        }catch (e:Exception){
            Log.d(tag, e.message)
        }
    }


    fun setButtonState(){
        if(!isConnected)
            binding.btnConnect.setText(resources.getString(R.string.disconnect))
        else
            binding.btnConnect.setText(resources.getString(R.string.connect))
    }

    fun connectToBT(){
        try {
            if (isBluetoothEnabled) {
                hc05Device = connectToDevice()
                if (!isConnected) {
                    if (hc05Device != null) {
                        btKit?.connect(hc05Device!!)
                        isConnected = true
                        Toast.makeText(this@MainActivity, "Connected to device", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    if (hc05Device != null) {
                        btKit?.disconnect()
                        isConnected = false
                        Toast.makeText(this@MainActivity, "Disconnected from the device", Toast.LENGTH_SHORT).show()
                        Log.d(tag, "Disconnected from the device")
                    }
                }
                setButtonState()
            } else
                Toast.makeText(
                    this@MainActivity,
                    "Please connect to bluetooth first",
                    Toast.LENGTH_SHORT
                ).show()
        }catch (e:Exception){
            Log.d(tag, e.message)
        }
    }

    override fun onResume() {
        super.onResume()
    }

    fun connectToDevice() : BluetoothDevice?{
        try {
            val hcDevice = btKit?.getDeviceByName("HC-05")
            //Return the device
            return hcDevice
        }catch (ex:Exception){
            Log.d(tag, "Cannot connect to bluetooth")
            return null
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.mnuConnect){
            when(!isBluetoothEnabled) {
                true -> {
                    btKit = BluetoothKit()
                    btKit?.enable()
                    isBluetoothEnabled = true
                    connectToBT()
                    Log.d(tag, "Enabled bluetooth")
                }
                false -> {
                    btKit?.disconnect()
                    btKit?.disable()
//                    btKit = null
//                    hc05Device = null
                    isBluetoothEnabled = false
                    Log.d(tag, "Disabled bluetooth")
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    @Throws(IOException::class)
    private fun createBluetoothSocket(device: BluetoothDevice, uuid:UUID): BluetoothSocket {
        if (Build.VERSION.SDK_INT >= 10) {
            try {
                val m = device.javaClass.getMethod(
                    "createInsecureRfcommSocketToServiceRecord",
                    *arrayOf<Class<*>>(UUID::class.java)
                )
                return m.invoke(device, uuid) as BluetoothSocket
            } catch (e: Exception) {
                Log.e(tag, "Could not create Insecure RFComm Connection", e)
            }

        }
        return device.createRfcommSocketToServiceRecord(uuid)
    }

    inner class BluetoothReceiver : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            bSocket = createBluetoothSocket(hc05Device!!, UUID.fromString(deviceID)) //                                hc05Device?.createRfcommSocketToServiceRecord(UUID.randomUUID())
            bSocket?.connect()
        }

    }

}
