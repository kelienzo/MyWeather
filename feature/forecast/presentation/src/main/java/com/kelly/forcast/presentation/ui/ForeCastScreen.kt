package com.kelly.forcast.presentation.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.kelly.core.common.model.Favorites
import com.kelly.core.common.utils.formatDateWithPattern
import com.kelly.core.common.utils.getIcon
import com.kelly.core.common.utils.parseDate
import com.kelly.core.common.utils.ui.CircularLoading
import com.kelly.core.common.utils.ui.RootLayout
import com.kelly.core.common.utils.ui.TextCompose
import com.kelly.forcast.presentation.viewmodel.state.ForeCastUiState
import com.kelly.forecast.data.mapper.toFavorites
import com.kelly.forecast.data.model.City
import com.kelly.forecast.data.model.ForeCastResponse
import com.kelly.forecast.data.model.Items
import com.kelly.forecast.data.model.Main
import com.kelly.forecast.data.model.Weather
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForeCastScreen(
    cityId: Int,
    favorites: List<Favorites>,
    foreCastUiState: ForeCastUiState,
    onUiEvent: (ForeCastEvent) -> Unit
) {
    val isPresentInFavorites = favorites.any { it.cityId == foreCastUiState.response?.city?.id }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        onUiEvent(ForeCastEvent.OnGetForeCast(cityId))
    }

    RootLayout(
        snackbarHostState = snackbarHostState,
        useCustomisedTopAppBar = true,
        customisedTopAppBar = {
            TopAppBar(
                title = {
                    TextCompose(
                        text = foreCastUiState.response?.city?.name.orEmpty(),
                        fontSize = 16,
                        textColor = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onUiEvent(ForeCastEvent.OnBack) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back Arrow",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        foreCastUiState.response?.let {
                            if (isPresentInFavorites)
                                onUiEvent(ForeCastEvent.OnRemoveFavorites(it.city?.id ?: 0))
                            else
                                onUiEvent(ForeCastEvent.OnAddToFavorites(it.toFavorites))
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    message = if (isPresentInFavorites)
                                        "Removed from favorites" else "Added to favorites"
                                )
                            }
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Favorite Icon",
                            tint = if (isPresentInFavorites) Color.Yellow else Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.DarkGray),
                modifier = Modifier.shadow(elevation = 10.dp, ambientColor = Color.Black)
            )
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(top = 20.dp, bottom = 20.dp)
        ) {
            foreCastUiState.run {
                if (isLoading) {
                    item {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CircularLoading()
                        }
                    }
                }
                response?.let { res ->
                    if (res.list.isNullOrEmpty()) {
                        item {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                TextCompose(text = "No weather updates", fontSize = 15)
                            }
                        }
                    } else {
                        items(res.list.orEmpty()) {
                            val date = it.dt_txt?.parseDate("yyyy-MM-dd HH:mm:ss")
                            ForeCastItem(
                                day = date.formatDateWithPattern("EEEE"),
                                dateTime = date.formatDateWithPattern("dd MMM: hh:mm a"),
                                temperature = "${it.main?.temp}ºC",
                                humidity = "${it.main?.humidity}%",
                                description = it.weather?.getOrNull(0)?.description.orEmpty(),
                                iconUrl = getIcon(iconCode = it.weather?.getOrNull(0)?.icon.orEmpty())
                            )
                        }
                    }
                }

                errorMessage?.let { errorMsg ->
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            TextCompose(
                                text = errorMsg,
                                fontSize = 18,
                                textAlign = TextAlign.Center,
                                textColor = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ForeCastItem(
    modifier: Modifier = Modifier,
    day: String,
    dateTime: String,
    temperature: String,
    description: String,
    humidity: String,
    iconUrl: String
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .border(width = 1.dp, color = Color.White, shape = RoundedCornerShape(10.dp))
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            TextCompose(text = day, fontSize = 14)
            TextCompose(text = dateTime, fontSize = 14)
            TextCompose(text = "Humidity: $humidity", fontSize = 14)
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextCompose(
                text = temperature,
                fontSize = 16
            )
            TextCompose(
                text = description,
                fontSize = 14
            )
        }
//        ImageCompose(image = R.drawable.sun, modifier = Modifier.size(50.dp))
        AsyncImage(model = iconUrl, contentDescription = "Weather icon")
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_3)
@Composable
private fun ForeCasrtScreenPrev() {
    ForeCastScreen(
        cityId = 1,
        favorites = listOf(
            Favorites(
                cityId = 2,
                city = null,
                cnt = null,
                cod = null,
                message = null
            )
        ),
        foreCastUiState = ForeCastUiState(
            response = ForeCastResponse(
                city = City(
                    coord = null,
                    country = null,
                    id = 2,
                    name = null,
                    population = null,
                    sunrise = null,
                    sunset = null,
                    timezone = null
                ),
                cnt = null,
                cod = "",
                list = listOf(
                    Items(
                        clouds = null,
                        dt = null,
                        dt_txt = "2024-11-26 15:00:00",
                        main = Main(
                            feels_like = 21.23,
                            grnd_level = null,
                            humidity = 32,
                            pressure = null,
                            sea_level = null,
                            temp = 73.43,
                            temp_kf = 32.34,
                            temp_max = null,
                            temp_min = null
                        ),
                        pop = null,
                        rain = null,
                        sys = null,
                        visibility = null,
                        weather = listOf(
                            Weather(
                                description = "Clear Sky",
                                icon = "",
                                id = 500,
                                main = "Clear"
                            )
                        ),
                        wind = null
                    )
                ),
                message = null
            )
        ),
        onUiEvent = {}
    )
}

sealed interface ForeCastEvent {
    data object OnBack : ForeCastEvent
    data class OnGetForeCast(val cityId: Int) : ForeCastEvent
    data class OnRemoveFavorites(val cityId: Int) : ForeCastEvent
    data class OnAddToFavorites(val favorites: Favorites) : ForeCastEvent
}