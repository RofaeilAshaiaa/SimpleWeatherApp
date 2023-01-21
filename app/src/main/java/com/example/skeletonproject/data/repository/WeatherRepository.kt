package com.example.skeletonproject.data.repository

import com.example.skeletonproject.data.models.SearchResultItem
import com.example.skeletonproject.data.models.WeatherData

interface WeatherRepository {

    suspend fun getWeatherData(): WeatherData

    suspend fun searchCities(cityName: String): List<SearchResultItem>

}