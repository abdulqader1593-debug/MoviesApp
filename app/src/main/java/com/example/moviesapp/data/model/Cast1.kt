package com.example.moviesapp.data.model

import com.google.gson.annotations.SerializedName

data class Cast1(
@SerializedName("id")
val id:String,
@SerializedName("movieId")
val movieId:Int,
@SerializedName("name")
val name: String,
@SerializedName("original_name")
val originalName:String,
@SerializedName("popularity")
val popularity: String,
@SerializedName("profile_path")
val profilePath:String,
@SerializedName("character")
val character: String,
)
