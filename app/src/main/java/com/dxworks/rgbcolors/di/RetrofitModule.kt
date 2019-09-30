package com.dxworks.rgbcolors.di

import com.dxworks.rgbcolors.ui.interfaces.ApiInterfaces
import com.dxworks.rgbcolors.utils.Constants
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton
import javax.net.ssl.*

@Module
class RetrofitModule{

    @Singleton
    @Provides
    fun provideApiInterface(retrofit: Retrofit) : ApiInterfaces{
        return retrofit.create(ApiInterfaces::class.java)
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient) : Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.apiUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor) : OkHttpClient {

        val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return arrayOf<X509Certificate>()
            }

            override fun checkClientTrusted(
                chain: Array<java.security.cert.X509Certificate>,
                authType: String
            ) {
            }

            override fun checkServerTrusted(
                chain: Array<java.security.cert.X509Certificate>,
                authType: String
            ) {
            }
        })
        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, java.security.SecureRandom())

        return OkHttpClient.Builder()
            .sslSocketFactory(sslContext.socketFactory)
            .hostnameVerifier { hostname, session -> true }
            .addInterceptor(httpLoggingInterceptor)
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor() : HttpLoggingInterceptor {
        val httpInterceptor: HttpLoggingInterceptor = HttpLoggingInterceptor()
        httpInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return httpInterceptor
    }

    @Provides
    @Named("network_scheduler")
    fun provideNetworkScheduler(): Scheduler {
        return Schedulers.io()
    }
}