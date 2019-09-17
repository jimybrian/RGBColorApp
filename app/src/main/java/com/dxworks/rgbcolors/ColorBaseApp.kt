package com.dxworks.rgbcolors

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.dxworks.rgbcolors.di.ApplicationComponent
import com.dxworks.rgbcolors.di.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class ColorBaseApp : DaggerApplication(){
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val appComponent: ApplicationComponent = DaggerApplicationComponent
            .builder()
            .application(this)
            .build()
        appComponent.inject(this@ColorBaseApp)
        return appComponent
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }

}