package com.kelly.home.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.kelly.core.common.R
import com.kelly.core.common.utils.getIcon
import com.kelly.core.common.utils.ui.CircularLoading
import com.kelly.core.common.utils.ui.ImageCompose
import com.kelly.core.common.utils.ui.RootLayout
import com.kelly.core.common.utils.ui.SpacerHeightWidth
import com.kelly.core.common.utils.ui.TextCompose
import com.kelly.home.data.model.CurrentWeatherResponse
import com.kelly.home.data.model.Main
import com.kelly.home.data.model.Weather
import com.kelly.home.presentation.viewmodel.state.HomeUiState

@Composable
fun HomeScreen(
    homeUiState: HomeUiState,
    isLocationGranted: Boolean,
    onUiEvent: (HomeEvent) -> Unit
) {

    LaunchedEffect(isLocationGranted) {
        if (isLocationGranted)
            onUiEvent(HomeEvent.GetCurrentWeatherData)
    }

    RootLayout { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SpacerHeightWidth(20)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ImageCompose(
                    image = R.drawable.search_icon,
                    contentDescription = "Search icon",
                    modifier = Modifier
                        .size(30.dp)
                        .clickable { onUiEvent(HomeEvent.NavigateToSearch) },
                    colorFilter = ColorFilter.tint(Color.White)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    modifier = Modifier.clickable { onUiEvent(HomeEvent.NavigateToFavorites) }
                ) {
                    TextCompose(
                        text = "Favorites",
                        fontSize = 18,
                        textColor = Color.White,
                        textDecoration = TextDecoration.Underline
                    )
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Favorite Icon",
                        tint = Color.Yellow
                    )
                }
            }

            SpacerHeightWidth(20)

            homeUiState.run {
                if (isLoading) {
                    CircularLoading()
                } else {
                    response?.let { res ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .border(
                                    width = 1.dp,
                                    color = Color.White,
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .background(Color.DarkGray.copy(0.20f))
                                .padding(10.dp),
                            verticalArrangement = Arrangement.spacedBy(
                                20.dp,
                                Alignment.CenterVertically
                            ),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
//                            ImageCompose(
//                                image = R.drawable.cloudy,
//                                modifier = Modifier.size(100.dp),
//                                contentDescription = "Weather symbol"
//                            )

                            TextCompose(text = res.name.orEmpty(), fontSize = 20)

                            TextCompose(text = "${res.main?.temp}ºC", fontSize = 40)

                            AsyncImage(
                                model = getIcon(iconCode = res.weather?.getOrNull(0)?.icon.orEmpty()),
                                contentDescription = "Weather icon",
                                modifier = Modifier
                                    .size(100.dp)
                                    .clip(RoundedCornerShape(5.dp)),
                                contentScale = ContentScale.FillBounds
                            )

                            TextCompose(
                                text = res.weather?.getOrNull(0)?.description.orEmpty(),
                                fontSize = 20
                            )
                        }

                        SpacerHeightWidth(20)

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(10.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                TextCompose(text = "${res.main?.humidity}%", fontSize = 14)
                                TextCompose(text = "Humidity", fontSize = 14)
                            }
                            Column(
                                verticalArrangement = Arrangement.spacedBy(10.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                TextCompose(text = "${res.main?.feels_like}ºC", fontSize = 14)
                                TextCompose(text = "Feels like", fontSize = 14)
                            }
                        }

//                        SpacerHeightWidth(20)

//                        LazyRow(
//                            modifier = Modifier.fillMaxWidth(),
//                            horizontalArrangement = Arrangement.spacedBy(10.dp),
//                            verticalAlignment = Alignment.CenterVertically
//                        ) {
//                            items(res.weather.orEmpty()) {
//                                WeatherIcons(
//                                    description = it.description.orEmpty(),
//                                    iconUrl = getIcon(iconCode = it.icon.orEmpty())
//                                )
//                            }
//                        }
                    } ?: errorMessage?.let { errorMsg ->
                        TextCompose(
                            text = buildAnnotatedString {
                                withStyle(SpanStyle()) {
                                    append("$errorMsg\n")
                                }
                                withStyle(SpanStyle(fontSize = 15.sp)) {
                                    append("Tap to retry")
                                }
                            },
                            fontSize = 14,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.clickable {
                                onUiEvent(HomeEvent.GetCurrentWeatherData)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun WeatherIcons(
    modifier: Modifier = Modifier,
    description: String = "Moderate rain",
//    icon: Int = R.drawable.sun
    iconUrl: String
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .border(width = 1.dp, color = Color.White, shape = RoundedCornerShape(10.dp))
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextCompose(text = description, textAlign = TextAlign.Center)
//        ImageCompose(image = icon, modifier = Modifier.size(40.dp))
        AsyncImage(
            model = iconUrl,
            contentDescription = "Weather icon",
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(5.dp)),
            contentScale = ContentScale.FillBounds
        )
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_3)
@Composable
private fun HomeScreenPrev() {
    HomeScreen(homeUiState = HomeUiState(
        response = CurrentWeatherResponse(
            base = null,
            clouds = null,
            cod = null,
            coord = null,
            dt = null,
            id = null,
            main = Main(
                feels_like = 232.3,
                grnd_level = 122,
                humidity = 24,
                pressure = 24,
                sea_level = 2342,
                temp = 87.3,
                temp_max = 343.5,
                temp_min = 89.45,
            ),
            name = "Rhode Island",
            rain = null,
            sys = null,
            timezone = 213,
            visibility = null,
            weather = listOf(
                Weather(description = "Small rain", icon = null, id = null, main = null),
                Weather(description = "Heavy rain", icon = null, id = null, main = null),
                Weather(description = "Cloudy", icon = null, id = null, main = null),
                Weather(description = "Drizzle", icon = null, id = null, main = null),
            ),
            wind = null
        )
    ),
        isLocationGranted = false,
        onUiEvent = {}
    )
}

sealed interface HomeEvent {
    data object GetCurrentWeatherData : HomeEvent

    data object NavigateToFavorites : HomeEvent
    data object NavigateToSearch : HomeEvent
}