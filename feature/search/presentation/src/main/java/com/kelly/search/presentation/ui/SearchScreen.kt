package com.kelly.search.presentation.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kelly.core.common.R
import com.kelly.core.common.utils.ui.CircularLoading
import com.kelly.core.common.utils.ui.ImageCompose
import com.kelly.core.common.utils.ui.RootLayout
import com.kelly.core.common.utils.ui.SpacerHeightWidth
import com.kelly.core.common.utils.ui.TextCompose
import com.kelly.search.data.model.City
import com.kelly.search.data.model.Coord
import com.kelly.search.data.model.SearchResponse
import com.kelly.search.presentation.viewmodel.state.SearchUiState
import kotlinx.coroutines.delay

@Composable
fun SearchScreen(searchUiState: SearchUiState, onUiEvent: (SearchEvent) -> Unit) {

    var searchInput by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(searchInput) {
        if (searchInput.isNotBlank()) {
            delay(1000)
            onUiEvent(SearchEvent.OnSearch(searchInput))
        }
    }

    RootLayout(
        topBarText = "Search City",
        onBack = { onUiEvent(SearchEvent.OnBack) }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SpacerHeightWidth(20)

            TextField(
                value = searchInput,
                onValueChange = { searchInput = it },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    ImageCompose(
                        image = R.drawable.search_icon,
                        contentDescription = "Search city",
                        colorFilter = ColorFilter.tint(Color.White)
                    )
                },
                placeholder = {
                    TextCompose(text = "Search city", fontSize = 15)
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Gray,
                    unfocusedContainerColor = Color.Gray
                ),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = null),
                shape = RoundedCornerShape(10.dp)
            )

            SpacerHeightWidth(20)

            searchUiState.run {
                if (isLoading) {
                    CircularLoading()
                } else {
                    response?.let { res ->
                        CityItem(
                            name = res.city?.name.orEmpty(),
                            country = res.city?.country.orEmpty(),
                            latitude = res.city?.coord?.lat.toString(),
                            longitude = res.city?.coord?.lon.toString(),
                            modifier = Modifier.clickable {
                                onUiEvent(
                                    SearchEvent.NavigateToForeCast(res.city?.id ?: 0)
                                )
                            }
                        )
                    } ?: errorMessage?.let { errorMsg ->
                        TextCompose(
                            text = errorMsg,
                            fontSize = 14,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CityItem(
    modifier: Modifier = Modifier,
    name: String,
    country: String,
    latitude: String,
    longitude: String
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
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            TextCompose(text = "City: $name", fontSize = 14)
            TextCompose(text = "Country: $country", fontSize = 14)
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.End
        ) {
            TextCompose(text = "Lat: $latitude", fontSize = 14)
            TextCompose(text = "Long: $longitude", fontSize = 14)
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_3)
@Composable
private fun SearchScreenPrev() {
    SearchScreen(
        searchUiState = SearchUiState(
            response = SearchResponse(
                city = City(
                    coord = Coord(
                        lat = 232.33,
                        lon = 32.4
                    ),
                    country = "England",
                    id = 2324,
                    name = "London",
                    population = 2134,
                    sunrise = 2312,
                    sunset = 4382974,
                    timezone = 324
                ),
                cnt = 2,
                cod = "",
                message = 2
            )
        ), onUiEvent = {})
}

sealed interface SearchEvent {
    data object OnBack : SearchEvent
    data class OnSearch(val searchText: String) : SearchEvent
    data class NavigateToForeCast(val cityId: Int) : SearchEvent
}