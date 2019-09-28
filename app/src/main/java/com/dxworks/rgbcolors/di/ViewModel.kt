package com.dxworks.rgbcolors.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dxworks.rgbcolors.models.ColorRepository
import com.dxworks.rgbcolors.models.ReadingsRepository
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import java.lang.annotation.Documented
import java.lang.annotation.ElementType
import java.lang.annotation.RetentionPolicy
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton
import kotlin.reflect.KClass


@Singleton
class ViewModelFactory @Inject
constructor(private val creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        var creator: Provider<out ViewModel>? = creators[modelClass]
        if (creator == null) {
            for ((key, value) in creators) {
                if (modelClass.isAssignableFrom(key)) {
                    creator = value
                    break
                }
            }
        }
        if (creator == null) {
            throw IllegalArgumentException("unknown model class $modelClass")
        }
        try {
            return creator.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }
}

@Documented
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)

@Module
abstract class ViewModelModule{

    @Binds
    @IntoMap
    @ViewModelKey(ColorRepository::class)
    abstract fun bindColorViewModel(trotsViewModel: ColorRepository) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ReadingsRepository::class)
    abstract fun bindReadingsViewModel(trotsViewModel: ReadingsRepository) : ViewModel

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory) : ViewModelProvider.Factory


}