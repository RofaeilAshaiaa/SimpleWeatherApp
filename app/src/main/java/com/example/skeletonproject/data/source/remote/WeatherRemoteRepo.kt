package com.example.skeletonproject.data.source.remote

import android.content.Context
import com.example.skeletonproject.R
import com.example.skeletonproject.data.models.SearchResultItem
import com.example.skeletonproject.data.models.WeatherData
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRemoteRepo @Inject constructor(
    private val weatherRemoteService: WeatherRemoteService,
    @ApplicationContext context: Context,
) {
    private val apiKey: String = context.getString(R.string.weather_api_key)

    suspend fun getWeatherData(): Response<WeatherData> {
        return weatherRemoteService.getWeatherData(apiKey = apiKey)
    }

    suspend fun searchCities(cityName: String): Response<List<SearchResultItem>> {
        return weatherRemoteService.searchCities(apiKey = apiKey, cityName = cityName)
    }
}