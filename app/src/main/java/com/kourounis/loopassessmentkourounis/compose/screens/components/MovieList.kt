package com.kourounis.loopassessmentkourounis.compose.screens.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kourounis.loopassessmentkourounis.compose.data.Movie

@Composable
fun MovieList(
    paddingValues: PaddingValues? = null,
    movieList: List<Movie>,
    onMovieDetails: ((Movie) -> Unit)?,
    onFavoriteUpdated: (Set<String>) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = if(paddingValues != null) Modifier.fillMaxSize().padding(paddingValues) else Modifier.fillMaxSize()
    ) {
        items(movieList) { movie ->
            MovieItem(movie, onMovieDetails, onFavoriteUpdated)
        }
    }
}