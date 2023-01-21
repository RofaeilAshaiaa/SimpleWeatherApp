package com.example.skeletonproject.presentation.homescreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skeletonproject.data.models.SearchResultItem
import com.example.skeletonproject.data.models.WeatherData
import com.example.skeletonproject.domain.GetWeatherDataUseCase
import com.example.skeletonproject.domain.SearchCitiesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenVM @Inject constructor(
    val getWeatherDataUseCase: GetWeatherDataUseCase,
    val searchCitiesUseCase: SearchCitiesUseCase,
) : ViewModel() {

    private val _weatherData: MutableStateFlow<WeatherData> = MutableStateFlow(WeatherData())
    val weatherData: StateFlow<WeatherData> = _weatherData

    private val _searchResult = MutableStateFlow(emptyList<SearchResultItem>())
    val searchResult: StateFlow<List<SearchResultItem>> = _searchResult

    private var _showSearchView: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val showSearchView: StateFlow<Boolean> = _showSearchView

    private val _loading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading


    fun loadWeatherData(dispatcher: CoroutineDispatcher = Dispatchers.IO) {
        viewModelScope.launch(dispatcher) {
            _loading.value = true
            val authors = getWeatherDataUseCase.invoke()
            _weatherData.value = authors
            _loading.value = false
        }
    }

    fun performSearchQuery(query: String, dispatcher: CoroutineDispatcher = Dispatchers.IO) {
        Log.d("mask", "Query= $query")
        if (query.isEmpty()){
            _searchResult.value = emptyList()
            return
        }
        viewModelScope.launch(dispatcher) {
            val searchResultItems = searchCitiesUseCase.invoke(query)
            _searchResult.value = searchResultItems
        }
    }

    fun toggleSearchViewVisibility() {
        _showSearchView.value = _showSearchView.value.not()
        if (_showSearchView.value.not()){
            _searchResult.value = emptyList()
        }
    }

}