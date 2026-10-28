package com.example.moviesapp.data.model

import com.example.moviesapp.adapters.VIEW_TYPE_TRENDING_MOVIES
import com.example.moviesapp.ui.activities.Movie

data class TrendingMovies(
    val trendingMovies: List<Movie>
): HomeFeedItem(
    viewType = VIEW_TYPE_TRENDING_MOVIES
)
