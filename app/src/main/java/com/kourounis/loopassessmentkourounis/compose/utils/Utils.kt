package com.kourounis.loopassessmentkourounis.compose.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kourounis.loopassessmentkourounis.compose.data.Movie
import java.io.InputStreamReader

private const val PREFS_NAME = "movie_prefs"
private const val FAVORITES_KEY = "favorite_movies"

fun loadMoviesFromJson(context: Context, fileName: Int): List<Movie> {
    val inputStream = context.resources.openRawResource(fileName)
    val reader = InputStreamReader(inputStream)
    val movieListType = object : TypeToken<List<Movie>>() {}.type
    return Gson().fromJson(reader, movieListType)
}

fun getFavoriteMovieIds(context: Context): MutableSet<String> {
    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    return prefs.getStringSet(FAVORITES_KEY, mutableSetOf()) ?: mutableSetOf()
}

fun toggleFavoriteMovie(context: Context, movieId: String): Boolean {
    val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    val favorites = getFavoriteMovieIds(context).toMutableSet()

    val isFavorite = if (favorites.contains(movieId)) {
        favorites.remove(movieId)
        false
    } else {
        favorites.add(movieId)
        true
    }

    prefs.edit().remove(FAVORITES_KEY).apply()
    prefs.edit().putStringSet(FAVORITES_KEY, favorites).commit()

    return isFavorite
}

fun getLanguageName(languageCode: String): String {
    val languageMap = mapOf(
        "en" to "English",
        "ja" to "Japanese",
        "es" to "Spanish",
        "fr" to "French",
        "de" to "German",
        "zh" to "Chinese",
        "ko" to "Korean",
        "it" to "Italian",
        "ru" to "Russian",
        "pt" to "Portuguese",
        "ar" to "Arabic",
        "hi" to "Hindi"
    )
    return languageMap[languageCode] ?: "Unknown"
}

fun formatWithDots(number: Long): String {
    return "%,d".format(number).replace(",", ".")
}