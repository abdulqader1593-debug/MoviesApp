package com.example.moviesapp.data.model

import com.example.moviesapp.adapters.VIEW_TYPE_TOB_BAR

data class User(
    val userName:String,
    val profilePic : String ?= null
): HomeFeedItem(
    viewType = VIEW_TYPE_TOB_BAR
)
