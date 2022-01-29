package com.aggarwalankur.stationdata.di

import com.aggarwalankur.stationdata.BuildConfig
import com.aggarwalankur.stationdata.network.StationApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import retrofit2.converter.gson.GsonConverterFactory

import retrofit2.Retrofit

import okhttp3.logging.HttpLoggingInterceptor

import okhttp3.OkHttpClient

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    const val BASE_URL = "https://global.api-dev.flixbus.com/"

    @Singleton
    @Provides
    fun provideApiService(): StationApiService {
        val httpClient = OkHttpClient.Builder()

        //Interceptor only for debug build
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
            httpClient.addInterceptor(logging)
        }

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
            .create(StationApiService::class.java)
    }
}