package com.example.skeletonproject.data.source.remote

import com.example.skeletonproject.data.models.SearchResultItem
import com.example.skeletonproject.data.models.WeatherData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author Rofaeil Ashaiaa
 * Created on 1/20/23.
 */
interface WeatherRemoteService {

    companion object {
        const val BASE_URL = "https://api.weatherapi.com/v1/"
    }

    @GET("forecast.json")
    suspend fun getWeatherData(
        @Query("q") cityName: String = "San Francisco",
        @Query("days") forecastDays: String = "3",
        @Query("aqi") airQualityData: String = "no",
        @Query("alerts") weatherAlerts: String = "no",
        @Query("key") apiKey: String,
    ): Response<WeatherData>

    @GET("search.json")
    suspend fun searchCities(
        @Query("q") cityName: String = "San Francisco",
        @Query("key") apiKey: String,
    ): Response<List<SearchResultItem>>


}