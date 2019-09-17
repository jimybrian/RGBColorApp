package com.dxworks.rgbcolors.di

import android.app.Application
import android.content.Context
import com.dxworks.rgbcolors.ui.activities.MainBottomBarActivity
import com.dxworks.rgbcolors.ui.fragments.BluetoothFragment
import com.dxworks.rgbcolors.ui.fragments.CameraFragment
import com.dxworks.rgbcolors.ui.fragments.ReadingsFragment
import com.dxworks.rgbcolors.ui.fragments.WifiFragment
import com.dxworks.rgbcolors.utils.SharedPreferences
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import javax.inject.Inject

@Module
abstract class ActivityBindingModule{
    @ContributesAndroidInjector
    abstract fun bindsMainBottomBarActivity(): MainBottomBarActivity
}

@Module
abstract class FragmentBindingModule{
    @ContributesAndroidInjector
    abstract fun bindsWifiFragment(): WifiFragment
    @ContributesAndroidInjector
    abstract fun bindsReadingsFragment(): ReadingsFragment
    @ContributesAndroidInjector
    abstract fun bindsCameraFragment(): CameraFragment
    @ContributesAndroidInjector
    abstract fun bindsBluetoothFragment(): BluetoothFragment
}

@Module
abstract class ApplicationModule {
    @Binds
    abstract fun provideContext(application: Application): Context
}