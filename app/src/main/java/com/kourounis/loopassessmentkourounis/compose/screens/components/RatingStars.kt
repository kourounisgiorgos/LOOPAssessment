package com.kourounis.loopassessmentkourounis.compose.screens.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kourounis.loopassessmentkourounis.compose.data.Movie
import kotlin.math.floor

@Composable
fun RatingStars(
    movie : Movie
) {
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