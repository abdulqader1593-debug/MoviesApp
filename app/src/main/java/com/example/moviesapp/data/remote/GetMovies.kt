package com.example.moviesapp.data.remote

import com.example.moviesapp.data.model.MoviesList1
import retrofit2.http.GET

interface GetMovies{
    @GET("/movies/infinite-scroll")
    suspend fun getMovies(): MoviesList1
}