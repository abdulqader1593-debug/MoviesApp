package com.example.moviesapp.data.model

import com.google.gson.annotations.SerializedName


data class MoviesList1(
    @SerializedName("data")
    val movieList: List<Movies>
)

