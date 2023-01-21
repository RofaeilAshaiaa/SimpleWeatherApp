package com.example.skeletonproject.domain

import com.example.skeletonproject.data.models.WeatherData
import com.example.skeletonproject.data.repository.WeatherRepository
import javax.inject.Inject

class GetWeatherDataUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    suspend operator fun invoke(): WeatherData {
        return weatherRepository.getWeatherData()
    }
}