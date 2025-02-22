package com.kourounis.loopassessmentkourounis.compose.screens.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.kourounis.loopassessmentkourounis.R
import com.kourounis.loopassessmentkourounis.compose.data.Movie
import kotlin.math.floor

@Composable
fun MovieItem(
    movie: Movie,
    onMovieDetails: ((Movie) -> Unit)?,
    onFavoriteUpdated: (Set<String>) -> Unit
) {

    val context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = movie.posterUrl,
            contentDescription = "Movie Poster",
            modifier = Modifier
                .height(89.dp)
                .width(60.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.FillBounds
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = movie.releaseDate.split("-")[0],
                fontSize = 12.sp,
                fontWeight = FontWeight.Light
            )
            Text(
                text = movie.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Row {
                val maxRating = 5
                val filledStars = floor(movie.rating).toInt()
                val emptyStars = maxRating - filledStars

                repeat(filledStars) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = "Filled Star",
                        tint = Color(0XFFFD9E02),
                        modifier = Modifier.size(16.dp)
                    )
                }

                repeat(emptyStars) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = "Empty Star",
                        tint = Color.LightGray,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }


        FavoriteButton(movie, context, onFavoriteUpdated)
    }
}