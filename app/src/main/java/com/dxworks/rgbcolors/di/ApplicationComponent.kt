package com.dxworks.rgbcolors.di

import android.app.Application
import com.dxworks.rgbcolors.ColorBaseApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class,
                        RetrofitModule::class,
                        ActivityBindingModule::class,
                        FragmentBindingModule::class,
                        ApplicationModule::class,
                        ViewModelModule::class])
interface ApplicationComponent : AndroidInjector<ColorBaseApp> {
    override fun inject(instance: ColorBaseApp?)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }
}