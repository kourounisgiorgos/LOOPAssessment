package com.kourounis.loopassessmentkourounis.compose.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.kourounis.loopassessmentkourounis.R
import com.kourounis.loopassessmentkourounis.compose.data.Movie
import com.kourounis.loopassessmentkourounis.compose.screens.components.FavoriteButton
import com.kourounis.loopassessmentkourounis.compose.screens.components.RatingStars
import com.kourounis.loopassessmentkourounis.compose.utils.formatWithDots
import com.kourounis.loopassessmentkourounis.compose.utils.getFavoriteMovieIds
import com.kourounis.loopassessmentkourounis.compose.utils.getLanguageName
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun MovieDetailsScreen(movie: Movie?, onBack: () -> Unit) {

    if (movie == null) {
        Text("Movie not found", fontSize = 20.sp, modifier = Modifier.padding(16.dp))
        return
    }

    val context = LocalContext.current
    var favoriteMovieIds by remember { mutableStateOf(getFavoriteMovieIds(context).toMutableSet()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color.White)
            .padding(vertical = 16.dp, horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            FavoriteButton(
                movie = movie,
                context = context,
                onFavoriteUpdated = { updatedFavorites ->
                    favoriteMovieIds = updatedFavorites.toMutableSet()
                }
            )

            Spacer(modifier = Modifier.width(16.dp))

            Image(
                painter = painterResource(R.drawable.ic_close),
                contentDescription = "Favorite",
                modifier = Modifier
                    .size(30.dp)
                    .clickable {
                        onBack.invoke()
                    },
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        AsyncImage(
            model = movie.posterUrl,
            contentDescription = "Movie Poster",
            modifier = Modifier
                .shadow(16.dp, shape = RoundedCornerShape(14.dp))
                .clip(RoundedCornerShape(14.dp))
                .width(203.dp)
                .height(295.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        RatingStars(movie)

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            Text(
                text = LocalDate.parse(movie.releaseDate, DateTimeFormatter.ISO_DATE)
                    .format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Light
            )

            Spacer(modifier = Modifier.width(4.dp))

            Text(
                text = "${movie.runtime / 60}h • ${movie.runtime % 60}m",
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Light
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            Text(
                text = movie.title,
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.width(4.dp))

            Text(
                text = "(${movie.releaseDate.split("-")[0]})",
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Light
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
        ) {
            items(movie.genres) { genre ->
                Card(
                    shape = CircleShape,
                    colors = CardDefaults.cardColors(containerColor = Color(0X141C250D)),
                    modifier = Modifier.padding(vertical = 8.dp).align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        text = genre,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column {
            Text(
                text = "Overview",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = movie.overview,
                fontSize = 14.sp,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Light
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Key Facts",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
            ) {
                FactItem(modifier = Modifier.weight(1f), title = "Budget", value = "$ ${formatWithDots(movie.budget)}")
                FactItem(modifier = Modifier.weight(1f), title = "Revenue", value = "$ ${formatWithDots(movie.revenue)}")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
            ) {
                FactItem(modifier = Modifier.weight(1f), title = "Original Language", value = getLanguageName(movie.language))
                FactItem(modifier = Modifier.weight(1f), title = "Rating", value = "${String.format("%.2f", movie.rating)} (${movie.reviews})")
            }
        }

    }
}

@Composable
private fun FactItem(modifier: Modifier, title: String, value: String) {
    Column(
        modifier = modifier
            .background(Color(0xFFEEEEEE), shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = title,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = value,
            fontSize = 16.sp,
            fontWeight = FontWeight.Light
        )
    }
}

