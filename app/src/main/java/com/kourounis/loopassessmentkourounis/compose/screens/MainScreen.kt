package com.kourounis.loopassessmentkourounis.compose.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
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
import androidx.compose.ui.res.painterResource
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
import com.kourounis.loopassessmentkourounis.R
import com.kourounis.loopassessmentkourounis.compose.data.Movie
import com.kourounis.loopassessmentkourounis.compose.screens.components.FavoriteButton
import com.kourounis.loopassessmentkourounis.compose.screens.components.Loader
import com.kourounis.loopassessmentkourounis.compose.utils.getFavoriteMovieIds
import kotlin.math.floor

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
            .padding(vertical = 32.dp, horizontal = 24.dp)
    ) {
        ProfileHeader()

        Spacer(modifier = Modifier.height(64.dp))

        Text(
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
            text = buildAnnotatedString {
                append("OUR ")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("STAFF PICKS")
                }
            },
            fontSize = 12.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        StaffPicksList(staffPicks, onMovieDetails) { updatedFavorites ->
            favoriteMovieIds = updatedFavorites.toMutableSet()
        }
    }
}

@Composable
private fun ProfileHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
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
        contentPadding = PaddingValues(end = 64.dp),
        itemSpacing = (-60).dp,
        modifier = Modifier.fillMaxWidth()
    ) { page ->

        val movie = favoriteMovies[page]

        AsyncImage(
            model = movie.posterUrl,
            contentDescription = "Movie Poster",
            modifier = Modifier.width(182.dp).height(270.dp).clip(RoundedCornerShape(14.dp))
        )
    }
}

@Composable
fun StaffPicksList(
    staffPicks: List<Movie>,
    onMovieDetails: ((Movie) -> Unit)?,
    onFavoriteUpdated: (Set<String>) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(staffPicks) { movie ->
            StaffPickItem(movie, onMovieDetails, onFavoriteUpdated)
        }
    }
}

@Composable
fun StaffPickItem(
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
                val hasHalfStar = movie.rating % 1 != 0.0
                val emptyStars =
                    maxRating - filledStars - if (hasHalfStar) 1 else 0

                repeat(filledStars) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = "Filled Star",
                        tint = Color(0XFFFD9E02),
                        modifier = Modifier.size(16.dp)
                    )
                }

                if (hasHalfStar) {
                    Icon(
                        painter = painterResource(R.drawable.ic_half_star),
                        contentDescription = "Half Star",
                        tint = Color(0XFFFD9E02),
                        modifier = Modifier.size(16.dp)
                    )
                }

                repeat(emptyStars) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = "Empty Star",
                        tint = Color.Gray,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }


        FavoriteButton(movie, context, onFavoriteUpdated)
    }
}
