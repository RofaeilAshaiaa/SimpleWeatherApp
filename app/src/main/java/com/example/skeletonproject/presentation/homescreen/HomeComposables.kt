package com.example.skeletonproject.presentation.homescreen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skeletonproject.R
import com.example.skeletonproject.data.models.ForecastDay
import com.example.skeletonproject.data.models.SearchResultItem
import com.example.skeletonproject.data.models.WeatherData
import com.example.skeletonproject.presentation.theme.SkeletonProjectTheme
import com.example.skeletonproject.utils.getDateFormatted
import com.example.skeletonproject.utils.getIconUrl
import com.example.skeletonproject.utils.getNameOfDay
import com.example.skeletonproject.utils.getTimeFormatted
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun HomeScreen(viewModel: HomeScreenVM) {
    viewModel.loadWeatherData()
    val weatherData: WeatherData by viewModel.weatherData.collectAsState()
    val searchResultItems: List<SearchResultItem> by viewModel.searchResult.collectAsState()
    val loading: Boolean by viewModel.loading.collectAsState()
    val showSearchView: Boolean by viewModel.showSearchView.collectAsState()

    if (loading) {
        Column(verticalArrangement = Arrangement.Center) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(64.dp)
            )
        }
    } else {
        Image(
            painter = painterResource(id = R.drawable.weather_unsplash_1),
            contentDescription = "",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Transparent)
        ) {
            if (showSearchView) {
                SearchComponent(
                    searchListener = object : SearchListener {

                        override fun performSearchQuery(query: String) {
                            Log.d("mask", "performSearchQuery")
                            viewModel.performSearchQuery(query)
                        }

                        override fun closeSearch() {
                            Log.d("mask", "closeSearch")
                            viewModel.toggleSearchViewVisibility()
                        }
                    },
                    searchResultItems = searchResultItems
                )
            }
            Box(
                modifier = Modifier
                    .padding(top = 40.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = getTimeFormatted(weatherData.location?.localtime) ?: "00:00AM",
                    fontSize = 16.sp,
                    color = Color.White,
                    fontWeight = FontWeight.W600,
                    modifier = Modifier.align(Alignment.Center),
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_baseline_search_24),
                    contentDescription = "",
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .size(32.dp)
                        .clickable(onClick = { viewModel.toggleSearchViewVisibility() })
                        .align(Alignment.CenterEnd),
                    contentScale = ContentScale.FillBounds,

                    )
            }
            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 40.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = weatherData.location?.name ?: "San Francisco",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.W700,
                    color = Color.White,
                    modifier = Modifier
                )
            }
            Row(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = getDateFormatted(weatherData.location?.localtime)
                        ?: "Tuesday, 12 Apr 2022",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W400,
                    modifier = Modifier
                )
            }
            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 120.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                GlideImage(
                    modifier = Modifier.size(120.dp), imageModel = {
                        getIconUrl(weatherData.current?.condition?.icon)
                    }, imageOptions = ImageOptions(
                        contentScale = ContentScale.Fit, alignment = Alignment.Center
                    )
                )
            }

            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = weatherData.current?.tempF.toString() + "°F",
                    color = Color.White,
                    fontSize = 56.sp,
                    fontWeight = FontWeight.W700,
                    modifier = Modifier
                )
            }

            Row(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "It’s a ${
                        weatherData.forecastData?.forecastDays?.get(0)?.day?.condition?.text?.lowercase()
                    } day.",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W400,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }

            Row(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .align(Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                WeatherProperty(
                    modifier = Modifier.padding(end = 40.dp),
                    data = weatherData.current?.windMph.toString() + " mph",
                    iconResourceId = R.drawable.ic_wind
                )
                WeatherProperty(
                    data = weatherData.current?.humidity.toString() + "%",
                    iconResourceId = R.drawable.ic_rain_droplet
                )
            }

            Row(
                modifier = Modifier
                    .padding(top = 120.dp)
                    .align(Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ForecastDayComponent(
                    modifier = Modifier.padding(end = 48.dp),
                    forecastDay = weatherData.forecastData?.forecastDays?.get(0),
                    data = "Today"
                )
                ForecastDayComponent(
                    modifier = Modifier.padding(end = 48.dp),
                    forecastDay = weatherData.forecastData?.forecastDays?.get(1),
                    data = "Tomorrow"
                )
                ForecastDayComponent(
                    forecastDay = weatherData.forecastData?.forecastDays?.get(2),
                    data = getNameOfDay(weatherData.forecastData?.forecastDays?.get(2)?.date)
                        ?: "After Tomorrow"
                )
            }
        }
    }

}

@Composable
fun WeatherProperty(modifier: Modifier = Modifier, data: String, iconResourceId: Int) {
    Row(
        modifier = modifier, verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = iconResourceId),
            contentDescription = "",
            modifier = Modifier
                .padding(end = 8.dp)
                .size(24.dp),
            contentScale = ContentScale.FillBounds
        )
        Text(
            text = data,
            fontSize = 12.sp,
            fontWeight = FontWeight.W400,
            color = Color.White,
        )
    }
}

@Composable
fun ForecastDayComponent(modifier: Modifier = Modifier, forecastDay: ForecastDay?, data: String) {
    Column(
        modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GlideImage(
            modifier = Modifier
                .size(32.dp)
                .align(Alignment.CenterHorizontally),
            imageModel = { getIconUrl(forecastDay?.day?.condition?.icon) },
            imageOptions = ImageOptions(
                contentScale = ContentScale.Fit, alignment = Alignment.Center
            )
        )
        Text(
            text = "${forecastDay?.day?.mintempF}°/${forecastDay?.day?.maxtempF}°F",
            fontSize = 12.sp,
            fontWeight = FontWeight.W400,
            color = Color.White,
        )
        Text(
            text = data,
            fontSize = 12.sp,
            color = Color.White,
            fontWeight = FontWeight.W700,
        )
    }
}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
//    HomeScreen(null)
}

@Preview(showBackground = true, backgroundColor = 0xFF0000FF)
@Composable
fun WeatherPropertyPreview() {
//    WeatherProperty(imageUrl = "https://i.imgur.com/DvpvklR.png")
}

@Preview(showBackground = true, backgroundColor = 0xFF0000FF)
@Composable
fun ForecastDayPreview() {
    //ForecastDay(forecastDay = weatherData.forecastData?.forecastDays?.get(0))
}

@Composable
fun SearchComponent(
    searchListener: SearchListener,
    searchResultItems: List<SearchResultItem>
) {
    Column(
        modifier = Modifier.background(
            color = Color.White, RoundedCornerShape(
                topEnd = 0.dp, topStart = 0.dp, bottomEnd = 25.dp, bottomStart = 25.dp
            )
        ), horizontalAlignment = Alignment.CenterHorizontally
    ) {

        var query: String by rememberSaveable { mutableStateOf("") }
        Log.d("mask", "SearchComponent-> Query= $query")
        Log.d("mask", "SearchComponent -> search Items= $searchResultItems")
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .clickable(onClick = { searchListener.closeSearch() }),
                imageVector = Icons.Rounded.ArrowBack,
                tint = MaterialTheme.colors.onBackground,
                contentDescription = "Search icon"
            )
            OutlinedTextField(
                shape = RoundedCornerShape(25),
                value = query,
                onValueChange = { onQueryChanged ->
                    query = onQueryChanged
                    Log.d("mask", "onValueChange -> Query= $query")
                    if (onQueryChanged.isNotEmpty()) {
                        searchListener.performSearchQuery(query)
                    }
                },
                trailingIcon = {
                    if (query.isBlank().not()) {
                        IconButton(onClick = {
                            query = ""
                        }) {
                            Icon(
                                imageVector = Icons.Rounded.Clear,
                                tint = MaterialTheme.colors.onBackground,
                                contentDescription = "Clear icon"
                            )
                        }
                    }
                },
                maxLines = 1,
                colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
                placeholder = { Text(text = stringResource(R.string.search_city_hint)) },
                textStyle = MaterialTheme.typography.subtitle1,
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                modifier = Modifier.widthIn(300.dp)
            )

        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, top = 40.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            itemsIndexed(searchResultItems) { index, item ->
                Text(
                    text = item.name.toString(),
                    modifier = Modifier
                        .padding(bottom = 25.dp)
                        .align(Alignment.Start)
                )
            }
        }

        IconButton(modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .background(
                color = Color(0xFFF1F4FF), RoundedCornerShape(
                    topEnd = 0.dp, topStart = 0.dp, bottomEnd = 25.dp, bottomStart = 25.dp
                )
            ),
            onClick = { searchListener.closeSearch() },
            content = {
                Icon(
                    imageVector = Icons.Rounded.KeyboardArrowUp,
                    tint = MaterialTheme.colors.onBackground,
                    contentDescription = "Clear icon"
                )
            })

    }

}

@Preview(showBackground = true, backgroundColor = 0)
@Composable
fun SearchComponentPreview() {
    SkeletonProjectTheme {
        SearchComponent(searchListener = object : SearchListener {
            override fun performSearchQuery(query: String) {

            }

            override fun closeSearch() {

            }
        }, searchResultItems = emptyList())
    }
}


@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SkeletonProjectTheme {
        Greeting("Android")
    }
}