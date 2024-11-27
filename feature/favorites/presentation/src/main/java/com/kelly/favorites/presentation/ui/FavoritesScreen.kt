package com.kelly.favorites.presentation.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kelly.core.common.R
import com.kelly.core.common.model.Favorites
import com.kelly.core.common.model.FavoritesCity
import com.kelly.core.common.model.FavoritesCoord
import com.kelly.core.common.utils.ui.ImageCompose
import com.kelly.core.common.utils.ui.RootLayout
import com.kelly.core.common.utils.ui.SpacerHeightWidth
import com.kelly.core.common.utils.ui.TextCompose

@Composable
fun FavoritesScreen(
    favorites: List<Favorites>,
    onUiEvent: (FavoritesEvent) -> Unit
) {
    var searchInput by rememberSaveable { mutableStateOf("") }

    RootLayout(
        topBarText = "Favorites",
        onBack = { onUiEvent(FavoritesEvent.OnBack) }
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
                        contentDescription = "Search",
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

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(
                    if (searchInput.isNotBlank())
                        favorites.filter { it.city?.name?.contains(searchInput) == true }
                    else
                        favorites
                ) {
                    FavoritesCityItem(
                        name = it.city?.name.orEmpty(),
                        country = it.city?.country.orEmpty(),
                        latitude = it.city?.coord?.lat.toString(),
                        longitude = it.city?.coord?.lon.toString(),
                        onDelete = { onUiEvent(FavoritesEvent.OnDeleteFavorite(it.cityId)) },
                        modifier = Modifier.clickable {
                            onUiEvent(FavoritesEvent.OnViewForeCast(it.city?.id ?: 0))
                        }
                    )
                }
            }
            SpacerHeightWidth(20)
        }
    }
}

@Composable
private fun FavoritesCityItem(
    modifier: Modifier = Modifier,
    name: String,
    country: String,
    latitude: String,
    longitude: String,
    onDelete: () -> Unit
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
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.End
            ) {
                TextCompose(text = "Lat: $latitude", fontSize = 14)
                TextCompose(text = "Long: $longitude", fontSize = 14)
            }

            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color.White
                )
            }
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_3)
@Composable
private fun FavoritesScreenPrev() {
    FavoritesScreen(
        favorites = listOf(
            Favorites(
                cityId = 1232,
                city = FavoritesCity(
                    coord = FavoritesCoord(
                        lat = 213.42,
                        lon = 384.32
                    ),
                    country = "Spain",
                    id = 213,
                    name = "Malaga",
                    population = 324,
                    sunset = 424,
                    sunrise = 324,
                    timezone = 424
                ),
                cnt = 32,
                cod = "",
                message = 3
            ),
            Favorites(
                cityId = 43,
                city = FavoritesCity(
                    coord = FavoritesCoord(
                        lat = 213.42,
                        lon = 384.32
                    ),
                    country = "Spain",
                    id = 213,
                    name = "Malaga",
                    population = 324,
                    sunset = 424,
                    sunrise = 324,
                    timezone = 424
                ),
                cnt = 32,
                cod = "",
                message = 3
            )
        ),
        onUiEvent = {}
    )
}

sealed interface FavoritesEvent {
    data object OnBack : FavoritesEvent
    data class OnViewForeCast(val cityId: Int) : FavoritesEvent
    data class OnDeleteFavorite(val cityId: Int) : FavoritesEvent
}