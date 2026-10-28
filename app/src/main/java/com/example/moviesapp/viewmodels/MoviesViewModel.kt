package com.example.moviesapp.viewmodels

import android.util.Log
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.data.model.Categories
import com.example.moviesapp.data.model.HomeFeedItem
import com.example.moviesapp.data.model.LatestMovies
import com.example.moviesapp.data.model.NewMovies
import com.example.moviesapp.data.model.Section
import com.example.moviesapp.data.model.TrendingMovies
import com.example.moviesapp.data.model.User
import com.example.moviesapp.ui.activities.MovieUiAction
import com.example.moviesapp.ui.activities.MoviesApi
import com.example.moviesapp.ui.activities.MoviesListState
import com.example.moviesapp.ui.activities.MoviesUiEvent
import com.example.moviesapp.ui.activities.RetrofitClientBuilder
import com.example.moviesapp.ui.activities.categories
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// MVI Based: Uni Directional Flow of data
class MoviesViewModel : ViewModel(

) {
    private val TAG = "MainActivity"
    private val _moviesState = MutableStateFlow(MoviesListState())
    val moviesState = _moviesState.asStateFlow()

    private val _homeFeed = MutableStateFlow(emptyList<HomeFeedItem>())
    val homeFeed get() = _homeFeed.asStateFlow()

    private val _userName: MutableStateFlow<String> = MutableStateFlow("Anonymous")
    val userName = _userName.asStateFlow()
    private val _events = Channel<MoviesUiEvent>()
    val events = _events.receiveAsFlow()
    val api by lazy {
        RetrofitClientBuilder.create()
    }

    init {
        fetchMovies(api)
    }

    fun onUiAction(action: MovieUiAction) {
        when (action) {
            is MovieUiAction.onMovieClick -> {
                _events.trySend(MoviesUiEvent.NavigateToDetails(action.movie))
            }
            MovieUiAction.MovieClick -> {}
        }
    }

    fun onCategorySelected(categoryName: String){
        Log.d(TAG, "onCategorySelected: $categoryName")
        val categories = _moviesState.value.chips.map { category ->
            if (category.name.equals(categoryName,true)){
                category.copy(isSelected = true)
            } else {
                category.copy(isSelected = false)
            }
        }

        _moviesState.update {
            it.copy(
                selectedCategory = categoryName,
                chips = categories
            )
        }
        buildHomeFeed(_moviesState.value)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    private fun fetchMovies(
        api: MoviesApi
    ) {
        viewModelScope.launch {
            try {
                val response = api.getMoviesList()
                response.let {
                    _events.trySend(MoviesUiEvent.ListFetchedSuccessfully)
                }

                _moviesState.update {
                    it.copy(
                        movies = response.movies
                    )
                }
                buildHomeFeed(state = _moviesState.value)
                Log.d(TAG, "fetchMovies: " + response)
            } catch (e: Exception) {
                Log.d(TAG, "fetchMovies: $e")
                _events.trySend(MoviesUiEvent.ErrorFetchingList(e.printStackTrace()))

            } finally {
                _moviesState.update {
                    it.copy(
                        loading = false
                    )
                }
            }
        }
    }

    private fun buildHomeFeed(state: MoviesListState){
        val feed = buildList {
            add(
                User(
                    userName = state.username ?: "Anonymous"
                )
            )
            add(
                NewMovies(
                    movies = state.sortedByPopularity
                )
            )
            add(
                Section(
                    label = "Categories"
                )
            )
            add(
                Categories(
                    chips = state.chips
                )
            )
            add(
                Section(
                    label = "Latest Movies"
                )
            )
            add(
                LatestMovies(
                    movies = state.sortedByReleasedDate
                )
            )
            add(
                Section(
                    label = "Trending Movies"
                )
            )
            add(
                TrendingMovies(
                    trendingMovies = state.sortedByVoteCount
                )
            )
        }
        _homeFeed.value = feed
    }
}