package com.example.spacexlaunchtracker.di

import com.example.spacexlaunchtracker.Constants
import com.example.spacexlaunchtracker.api.SpaceXApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Singleton
    @Provides
    fun providesGsonBuilder(): Gson{
        return GsonBuilder().create()
    }

    @Singleton
    @Provides
    fun providesOkhttpClient(): OkHttpClient{
        return OkHttpClient().newBuilder().build()
    }

    @Singleton
    @Provides
    fun providesRetrofitClient(gson: Gson, okHttpClient: OkHttpClient): Retrofit{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideSpaceXApi(retrofit: Retrofit): SpaceXApi{
        return retrofit.create(SpaceXApi::class.java)
    }
}