package com.dxworks.rgbcolors.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.dxworks.rgbcolors.R
import com.dxworks.rgbcolors.databinding.ActivityMain2Binding
import com.dxworks.rgbcolors.fragments.BluetoothFragment
import com.dxworks.rgbcolors.fragments.CameraFragment
import com.dxworks.rgbcolors.fragments.ReadingsFragment
import com.dxworks.rgbcolors.fragments.WifiFragment

class MainBottomBarActivity : AppCompatActivity(){

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


    override fun onResume() {
        super.onResume()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return super.onOptionsItemSelected(item)
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}