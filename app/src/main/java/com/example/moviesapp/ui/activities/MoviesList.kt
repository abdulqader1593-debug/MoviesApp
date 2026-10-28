package com.example.moviesapp.ui.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.moviesapp.R
import com.example.moviesapp.adapters.ChipGroupAdapter
import com.example.moviesapp.adapters.HomeFeedAdapter
import com.example.moviesapp.adapters.HomeFeedListAdapter
import com.example.moviesapp.adapters.LatestMovieAdapter
import com.example.moviesapp.adapters.VIEW_TYPE_CATEGORIES
import com.example.moviesapp.adapters.ViewPagerAdapter
import com.example.moviesapp.data.model.HomeFeedItem

import com.example.moviesapp.databinding.ActivityMoviesListBinding
import com.example.moviesapp.databinding.ActivityMoviesRecyclerViewBinding
import com.example.moviesapp.viewmodels.MoviesViewModel
import kotlinx.coroutines.launch


class MoviesList : AppCompatActivity() {

    private val viewModel by viewModels<MoviesViewModel>()
    private lateinit var binding: ActivityMoviesRecyclerViewBinding
    private val categoryAdapter by lazy { ChipGroupAdapter(viewModel) }
    private val latestMovieAdapter by lazy { LatestMovieAdapter(viewModel) }
    private val feedMovieAdapter by lazy { HomeFeedAdapter(viewModel) }
    private val feedMovieListAdapter by lazy { HomeFeedListAdapter(viewModel) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this,R.layout.activity_movies_recycler_view)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        binding.rvHomeFeed.adapter = feedMovieListAdapter
//        binding.rvCategories.adapter = categoryAdapter
//        binding.rvLatestMovies.adapter = latestMovieAdapter
//        lifecycleScope.launch {
//                viewModel.moviesState.collect { list ->
//                        val adapter = ViewPagerAdapter(list)
//                        adapter.submit(list)
//                        //binding.movieViewPager.adapter = adapter
//                }
//        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onStart() {
        super.onStart()
    }
}

sealed interface MoviesUiEvent1{
    object ListFetchedSuccess: MoviesUiEvent1
    data class ErrorFetchingList(val message: Unit): MoviesUiEvent1
}

data class Category(
    val id:Int?,
    val name:String?,
    val isSelected: Boolean = false
): HomeFeedItem(VIEW_TYPE_CATEGORIES){
    val background get() =
        if (isSelected) R.drawable.bg_category_chip_selected else R.drawable.bg_category_chip_unselected
}

val categories = listOf(
    Category(1,"All"),
    Category(2,"Action"),
    Category(2,"Comedy"),
    Category(2,"Drama"),
    Category(2,"Romance"),
)




