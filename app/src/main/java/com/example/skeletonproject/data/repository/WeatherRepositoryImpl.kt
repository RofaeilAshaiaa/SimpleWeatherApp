package com.example.skeletonproject.data.repository

import android.content.Context
import com.example.skeletonproject.data.models.SearchResultItem
import com.example.skeletonproject.data.models.WeatherData
import com.example.skeletonproject.data.source.remote.WeatherRemoteRepo
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepositoryImpl @Inject constructor(
    val weatherRemoteRepo: WeatherRemoteRepo,
    @ApplicationContext context: Context
) : WeatherRepository {

    override suspend fun getWeatherData(): WeatherData {
        val weatherDataResponse = weatherRemoteRepo.getWeatherData()
        return weatherDataResponse.body()!!
    }

    override suspend fun searchCities(cityName: String): List<SearchResultItem> {
        val searchResult = weatherRemoteRepo.searchCities(cityName = cityName)
        return searchResult.body()!!
    }

}