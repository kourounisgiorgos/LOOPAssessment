package com.kourounis.loopassessmentkourounis.compose.data

data class Movie(
    val id: Int,
    val title: String,
    val rating: Double,
    val revenue: Long,
    val releaseDate: String,
    val director: Director,
    val posterUrl: String,
    val cast: List<Actor>,
    val runtime: Int,
    val overview: String,
    val reviews: Int,
    val budget: Long,
    val language: String,
    val genres: List<String>
)

data class Director(
    val name: String,
    val pictureUrl: String
)

data class Actor(
    val name: String,
    val pictureUrl: String,
    val character: String
)