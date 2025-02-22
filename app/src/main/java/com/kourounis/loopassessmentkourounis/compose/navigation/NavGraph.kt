package com.kourounis.loopassessmentkourounis.compose.navigation

import androidx.navigation.NavHostController
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.*
import com.kourounis.loopassessmentkourounis.R
import com.kourounis.loopassessmentkourounis.compose.data.Movie
import com.kourounis.loopassessmentkourounis.compose.screens.AllMoviesScreen
import com.kourounis.loopassessmentkourounis.compose.screens.MainScreen
import com.kourounis.loopassessmentkourounis.compose.screens.MovieDetailsScreen
import com.kourounis.loopassessmentkourounis.compose.utils.loadMoviesFromJson

@Composable
fun NavGraph(navController: NavHostController) {
    val context = LocalContext.current
    val movies = remember { mutableStateOf(emptyList<Movie>()) }
    val staffPicks = remember { mutableStateOf(emptyList<Movie>()) }

    LaunchedEffect(Unit) {
        movies.value = loadMoviesFromJson(context, R.raw.movies)
        staffPicks.value = loadMoviesFromJson(context, R.raw.staff_picks)
    }

    NavHost(navController, startDestination = "main-screen") {
        composable("main-screen") {
            MainScreen(
                movies = movies.value,
                staffPicks = staffPicks.value,
                onGoToAllMovies = { navController.navigate("all-movies-screen") },
                onMovieDetails = { movie -> navController.navigate("movie-details/${movie.id}") }
            )
        }

        composable("all-movies-screen") {
            AllMoviesScreen(
                movies = movies.value,
                onBack = { navController.navigate("main-screen") },
                onMovieDetails = { movie -> navController.navigate("movie-details/${movie.id}") }
            )
        }

        composable("movie-details/{movieId}") { backStackEntry ->
            val movieId = backStackEntry.arguments?.getString("movieId")?.toIntOrNull()
            val selectedMovie = movies.value.find { it.id == movieId }

            MovieDetailsScreen(
                movie = selectedMovie,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
