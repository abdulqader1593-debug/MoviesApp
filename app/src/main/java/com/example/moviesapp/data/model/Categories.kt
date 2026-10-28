package com.example.moviesapp.data.model

import com.example.moviesapp.adapters.VIEW_TYPE_CATEGORIES
import com.example.moviesapp.ui.activities.Category
import com.google.android.material.chip.Chip

data class Categories(
    val chips: List<Category>,
): HomeFeedItem(
    viewType = VIEW_TYPE_CATEGORIES
){
    val id get() =
        "${chips.find { it.isSelected }?.name}-categories"

    val selectedItemPosition get() =
        chips.indexOfFirst { it.isSelected }
}
