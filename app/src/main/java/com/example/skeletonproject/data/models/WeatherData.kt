package com.example.skeletonproject.data.models

import com.google.gson.annotations.SerializedName

data class WeatherData(
    @SerializedName("location") var location: Location? = Location(),
    @SerializedName("current") var current: CurrentWeather? = CurrentWeather(),
    @SerializedName("forecast") var forecastData: ForecastData? = ForecastData()
)