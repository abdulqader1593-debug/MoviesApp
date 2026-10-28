package com.example.moviesapp.data.model

import com.example.moviesapp.adapters.VIEW_TYPE_NEW_MOVIES
import com.example.moviesapp.ui.activities.Movie

data class NewMovies(
    val movies: List<Movie>
): HomeFeedItem(
    viewType = VIEW_TYPE_NEW_MOVIES
)
