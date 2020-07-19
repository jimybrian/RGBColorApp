package com.dxworks.rgbcolors.ui.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.dxworks.rgbcolors.R
import com.dxworks.rgbcolors.databinding.ActivityMain2Binding
import com.dxworks.rgbcolors.ui.fragments.BluetoothFragment
import com.dxworks.rgbcolors.ui.fragments.CameraFragment
import com.dxworks.rgbcolors.ui.fragments.ReadingsFragment
import com.dxworks.rgbcolors.ui.fragments.WifiFragment
import dagger.android.support.DaggerAppCompatActivity

class MainBottomBarActivity : DaggerAppCompatActivity(){

    lateinit var binding:ActivityMain2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this@MainBottomBarActivity, SplashActivity::class.java))
        binding = DataBindingUtil.setContentView(this@MainBottomBarActivity, R.layout.activity_main_2)

        openFragment(WifiFragment())
        binding.navBar.setOnNavigationItemSelectedListener { n ->
            when(n.itemId){
                R.id.navColor -> {
                    openFragment(WifiFragment())
                }
                R.id.navBluetooth -> {
                    openFragment(BluetoothFragment())
                }
                R.id.navReadings -> {
                    openFragment(ReadingsFragment())
                }
                R.id.navCCTV -> {
                    openFragment(CameraFragment())
                }
            }
            return@setOnNavigationItemSelectedListener true
        }
    }

    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()

        Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
    }


    override fun onResume() {
        super.onResume()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.commit()
    }
}