package com.example.moviesapp.data.model


data class MoviesState(
    private val loading: Boolean = false,
    private val success: List<Movies> = emptyList(),
    private val error: String ?= null,
    val username: String? = null
)
