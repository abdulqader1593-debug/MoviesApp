package com.example.moviesapp.data.model

import com.example.moviesapp.adapters.VIEW_TYPE_SECTION_HEADER

data class Section(
    val label: String
): HomeFeedItem(
    viewType = VIEW_TYPE_SECTION_HEADER
)
