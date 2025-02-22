package com.kourounis.loopassessmentkourounis.compose.data

data class Movie(
    val id: Int,
    val title: String,
    val rating: Double,
    val revenue: Long,
    val releaseDate: String,
    val posterUrl: String,
    val runtime: Int,
    val overview: String,
    val reviews: Int,
    val budget: Long,
    val language: String,
    val genres: List<String>
)