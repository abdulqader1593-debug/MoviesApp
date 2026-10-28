package com.example.moviesapp.data.model

import com.google.gson.annotations.SerializedName

data class Movies(
@SerializedName("id")
val id: String? = null,
@SerializedName("movie_id")
val movieId: Int? = null,
@SerializedName("original_title")
val originalTitle: String? = null,
@SerializedName("original_language")
val originalLanguage: String? = null,
@SerializedName("overview")
val overview: String? = null,
@SerializedName("popularity")
//horizontal pager
val popularity: Double? = null,
@SerializedName("poster_path")
val posterPath: String? = null,
@SerializedName("backdrop_path")
val backdropPath: String? = null,
@SerializedName("release_date")
//latest movie
val releaseDate: String? = null,
@SerializedName("vote_average")
val voteAverage: Double? = null,
@SerializedName("vote_count")
//trending now
val voteCount: Int? = null,
@SerializedName("adult")
val adult: Int? = null,
@SerializedName("casts")
val casts: List<Cast1>
)
