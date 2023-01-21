package com.example.skeletonproject.data.models

import com.google.gson.annotations.SerializedName

data class ForecastData(
    @SerializedName("forecastday") var forecastDays: ArrayList<ForecastDay> = arrayListOf()
)