package com.kourounis.loopassessmentkourounis.compose.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.kourounis.loopassessmentkourounis.compose.data.Movie
import com.kourounis.loopassessmentkourounis.compose.screens.components.Loader
import com.kourounis.loopassessmentkourounis.compose.screens.components.MovieList
import com.kourounis.loopassessmentkourounis.compose.utils.getFavoriteMovieIds

@Composable
fun MainScreen(
    onGoToAllMovies: () -> Unit,
    onMovieDetails: (Movie) -> Unit,
    movies: List<Movie>,
    staffPicks: List<Movie>
) {

    Loader(loading = movies.isEmpty() || staffPicks.isEmpty()) {
        if (movies.isNotEmpty() && staffPicks.isNotEmpty()) {
            MainScreenContent(
                onGoToAllMovies = onGoToAllMovies,
                onMovieDetails = onMovieDetails,
                movies = movies,
                staffPicks = staffPicks
            )
        }
    }


}

@Composable
fun MainScreenContent(
    onGoToAllMovies: (() -> Unit)? = null,
    onMovieDetails: ((Movie) -> Unit)? = null,
    movies: List<Movie> = emptyList(),
    staffPicks: List<Movie> = emptyList()
) {
    val context = LocalContext.current
    var favoriteMovieIds by remember { mutableStateOf(getFavoriteMovieIds(context).toMutableSet()) }

    val favoriteMovies = movies.filter { it.id.toString() in favoriteMovieIds }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        ProfileHeader(onGoToAllMovies = onGoToAllMovies)

        Spacer(modifier = Modifier.height(64.dp))

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            text = buildAnnotatedString {
                append("YOUR ")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("FAVOURITES")
                }
            },
            fontSize = 12.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (favoriteMovies.isNotEmpty()) {
            MovieFavoritesPager(favoriteMovies, onMovieDetails)
        } else {
            Text(text = "No favorites yet", fontSize = 14.sp, color = Color.Gray)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            text = buildAnnotatedString {
                append("OUR ")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("STAFF PICKS")
                }
            },
            fontSize = 12.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        MovieList(
            paddingValues = PaddingValues(horizontal = 24.dp),
            staffPicks,
            onMovieDetails
        ) { updatedFavorites ->
            favoriteMovieIds = updatedFavorites.toMutableSet()
        }

    }

}

@Composable
private fun ProfileHeader(onGoToAllMovies: (() -> Unit)?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTN3-b6hE_5K-l4bv_gBuFtF5zWoPEhSkLsuw&s",
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.FillBounds
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column {
                Text(
                    text = "Hello \uD83D\uDC4B",
                    fontSize = 16.sp,
                )
                Text(
                    text = "Jane Doe",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

        }

        Box(
            modifier = Modifier
                .size(40.dp)
                .shadow(elevation = 8.dp, shape = RoundedCornerShape(12.dp))
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White)
                .padding(8.dp)
                .clickable { onGoToAllMovies?.invoke() }
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                modifier = Modifier.align(Alignment.Center),
                tint = Color.Black
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun MovieFavoritesPager(favoriteMovies: List<Movie>, onMovieDetails: ((Movie) -> Unit)?) {
    val pagerState = rememberPagerState()

    HorizontalPager(
        count = favoriteMovies.size,
        state = pagerState,
        contentPadding = PaddingValues(start = 64.dp, end = 64.dp),
        itemSpacing = (-64).dp,
        modifier = Modifier.fillMaxWidth()
    ) { page ->

        val movie = favoriteMovies[page]

        AsyncImage(
            model = movie.posterUrl,
            contentDescription = "Movie Poster",
            modifier = Modifier
                .shadow(8.dp, shape = RoundedCornerShape(14.dp))
                .clip(RoundedCornerShape(14.dp))
                .width(182.dp)
                .height(270.dp)
                .clickable{
                    onMovieDetails?.invoke(movie)
                }
        )
    }
}

