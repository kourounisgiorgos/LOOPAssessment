package com.kourounis.loopassessmentkourounis.compose.screens.components

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.kourounis.loopassessmentkourounis.R
import com.kourounis.loopassessmentkourounis.compose.data.Movie
import com.kourounis.loopassessmentkourounis.compose.utils.getFavoriteMovieIds
import com.kourounis.loopassessmentkourounis.compose.utils.toggleFavoriteMovie

@Composable
fun FavoriteButton(movie: Movie, context: Context, onFavoriteUpdated: (Set<String>) -> Unit) {
    var isFavorite by remember { mutableStateOf(movie.id.toString() in getFavoriteMovieIds(context)) }

    Icon(
        painter = if (isFavorite) painterResource(R.drawable.ic_bookmark_filled) else painterResource(
            R.drawable.ic_bookmark
        ),
        contentDescription = "Favorite",
        modifier = Modifier
            .size(24.dp)
            .clickable {
                isFavorite = toggleFavoriteMovie(context, movie.id.toString())
                onFavoriteUpdated(getFavoriteMovieIds(context))
            },
        tint = if (isFavorite) Color(0XFFFD9E02) else Color.Gray
    )
}
