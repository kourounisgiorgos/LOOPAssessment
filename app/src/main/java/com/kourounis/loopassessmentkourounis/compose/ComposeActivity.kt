package com.kourounis.loopassessmentkourounis.compose

import com.kourounis.loopassessmentkourounis.compose.screens.MainScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.*
import com.kourounis.loopassessmentkourounis.R
import com.kourounis.loopassessmentkourounis.compose.data.Movie
import com.kourounis.loopassessmentkourounis.compose.utils.loadMoviesFromJson

class ComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeNavigation()
        }
    }
}

@Composable
fun ComposeNavigation() {
    val navController = rememberNavController()

    val context = LocalContext.current
    val movies = remember { mutableStateOf(emptyList<Movie>()) }
    val staffPicks = remember { mutableStateOf(emptyList<Movie>()) }

    LaunchedEffect(Unit) {
        movies.value = loadMoviesFromJson(context, R.raw.movies)
        staffPicks.value = loadMoviesFromJson(context, R.raw.staff_picks)
    }

    NavHost(navController, startDestination = "main-screen") {
        composable("main-screen")
        {
            MainScreen(
                movies = movies.value,
                staffPicks = staffPicks.value,
                onGoToAllMovies = { navController.navigate("all-movies") },
                onMovieDetails = { navController.navigate("movie-details") }
            )
        }
    }
}
