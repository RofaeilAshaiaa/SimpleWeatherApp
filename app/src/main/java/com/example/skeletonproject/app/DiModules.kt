package com.example.skeletonproject.app

import com.example.skeletonproject.data.repository.WeatherRepository
import com.example.skeletonproject.data.repository.WeatherRepositoryImpl
import com.example.skeletonproject.data.source.remote.WeatherRemoteService
import com.example.skeletonproject.data.source.remote.WeatherRemoteService.Companion.BASE_URL
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideMainService(): WeatherRemoteService {
        val okHttpClient = OkHttpClient.Builder().build()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherRemoteService::class.java)
    }

}

@Module
@InstallIn(SingletonComponent::class)
abstract class ReposModule {

    @Binds
    abstract fun bindWeatherRepository(
        weatherRepositoryImpl: WeatherRepositoryImpl
    ): WeatherRepository
}