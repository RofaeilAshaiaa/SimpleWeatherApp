package com.example.skeletonproject.domain

import com.example.skeletonproject.data.models.SearchResultItem
import com.example.skeletonproject.data.repository.WeatherRepository
import javax.inject.Inject

class SearchCitiesUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    suspend operator fun invoke(cityName: String): List<SearchResultItem> {
        return weatherRepository.searchCities(cityName)
    }
}