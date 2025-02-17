package com.test.nrk.moviefinder.model

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("Search") val movies: List<Movie>?
)

data class Movie(
    @SerializedName("imdbID") val id: String,
    @SerializedName("Title") val title: String,
    @SerializedName("Year") val year: String,
    @SerializedName("Type") val type: String,
    @SerializedName("Poster") val posterUrl: String
)