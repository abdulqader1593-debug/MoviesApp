package com.example.moviesapp

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import coil.load
import com.example.moviesapp.adapters.ChipGroupAdapter
import com.example.moviesapp.adapters.HomeFeedListAdapter
import com.example.moviesapp.adapters.LatestMovieAdapter
import com.example.moviesapp.adapters.ViewPagerAdapter
import com.example.moviesapp.data.model.Categories
import com.example.moviesapp.data.model.HomeFeedItem
import com.example.moviesapp.ui.activities.Category
import com.example.moviesapp.ui.activities.Movie

@BindingAdapter("categoryBackground")
fun setCategoryBackground(view: TextView, category: Category?) {
    category?.let {
        view.setBackgroundResource(it.background)
    }
}

@BindingAdapter("latestMovieImage")
fun setLatestMovies(view: ImageView, posterPath: String?){
   posterPath?.let {
       view.load(it){
           crossfade(true)
       }
   }
}

@BindingAdapter("categoriesSection")
fun RecyclerView.setCategoriesSection(categories: Categories){
    val chipAdapter = this.adapter as? ChipGroupAdapter ?: return
    chipAdapter.submitList(categories.chips)
    chipAdapter.notifyItemChanged(categories.selectedItemPosition)
}

@BindingAdapter("categories")
fun RecyclerView.setCategories(categories: List<Category>){
    val chipAdapter = this.adapter as? ChipGroupAdapter ?: return
    chipAdapter.submitList(categories)
}

@BindingAdapter("latestMovies")
fun RecyclerView.setLatestMovies(movies: List<Movie>){
    val latestMovieAdapter = (this.adapter as? LatestMovieAdapter)
    latestMovieAdapter?.submitList(movies)
}


@BindingAdapter("newMovies")
fun ViewPager2.setNewMovies(movies: List<Movie>){
    val newMoviesAdapter = this.adapter as? ViewPagerAdapter ?:return
    newMoviesAdapter.submitList(movies)
}

@BindingAdapter("feedItems")
fun RecyclerView.setFeedItems(feedItems: List<HomeFeedItem>){
    val feedAdapter = (this.adapter as? HomeFeedListAdapter)
   // val feedAdapter = (this.adapter as? HomeFeedAdapter)
    feedAdapter?.submitList(feedItems)
}

//@BindingAdapter("profilePic")
//fun setProfilePic(view: ImageView, url:String){
//    url.let {
//        view.load(
//
//        )
//    }
//}
//
//@BindingAdapter("onCategorySelected")
//fun setCategory(view: Chip, onClickAction: OnClickAction){
//    onClickAction.let {
//        view.
//    }
//}


