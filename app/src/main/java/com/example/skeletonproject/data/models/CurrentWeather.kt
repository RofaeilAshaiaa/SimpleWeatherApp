package com.example.skeletonproject.data.models

import com.google.gson.annotations.SerializedName

data class CurrentWeather(
    @SerializedName("temp_c") var tempC: Double? = null,
    @SerializedName("temp_f") var tempF: Double? = null,
    @SerializedName("is_day") var isDay: Int? = null,
    @SerializedName("condition") var condition: Condition? = Condition(),
    @SerializedName("wind_mph") var windMph: Double? = null,
    @SerializedName("humidity") var humidity: Int? = null
)