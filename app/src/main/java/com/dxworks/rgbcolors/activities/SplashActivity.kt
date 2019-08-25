package com.dxworks.rgbcolors.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.dxworks.rgbcolors.R
import com.dxworks.rgbcolors.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity(){

    lateinit var binding:ActivitySplashBinding
    val SPLASH_TIMEOUT:Long = 4000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@SplashActivity, R.layout.activity_splash)

        Handler().postDelayed({
            val data = Intent()
            data.data = Uri.parse("FINISHED")
            setResult(RESULT_OK, data)
            finish()
        }, SPLASH_TIMEOUT)
    }
}