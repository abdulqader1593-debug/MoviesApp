package com.example.moviesapp.ui.activities

import android.content.Intent
import android.os.Bundle
import android.os.Parcel
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.moviesapp.ui.activities.MoviesUiEvent.*
import com.example.moviesapp.ui.theme.MoviesAppTheme
import com.google.gson.annotations.SerializedName

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import android.os.Parcelable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.moviesapp.R
import com.example.moviesapp.adapters.VIEW_TYPE_LATEST_MOVIES
import com.example.moviesapp.data.model.HomeFeedItem
import com.example.moviesapp.data.model.User
import com.example.moviesapp.viewmodels.MoviesViewModel
import retrofit2.http.GET


sealed interface MovieUiAction {
    object MovieClick : MovieUiAction
    data class onMovieClick(
        val movie: Movie
    ) : MovieUiAction
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val moviesViewModel by viewModels<MoviesViewModel>()

        setContent {
            LaunchedEffect(Unit) {
                moviesViewModel.events.collect { event ->
                    when (event) {
                        is NavigateToDetails -> {
                            val intent = Intent(this@MainActivity, MovieDetails::class.java).apply {
                                putExtra("movie", event.movie)
                            }
                            startActivity(intent)
                        }
                        else -> {}
                    }
                }
            }
            MoviesAppTheme {
                val state by moviesViewModel.moviesState.collectAsState()
                MoviesListComponent(state.movies, onMovieClick = { selectedMovie ->
                    moviesViewModel.onUiAction(MovieUiAction.onMovieClick(selectedMovie))
                })
            }
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MoviesListComponent(
        movies: List<Movie>,
        onMovieClick: (Movie) -> Unit
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Column() {
                            Text(
                                text = "Hello",
                                color = Color.White,
                                style = MaterialTheme.typography.titleSmall
                            )
                            Text(
                                text = "Floyd Miles",
                                color = Color.White,
                                style = MaterialTheme.typography.titleLarge
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF0E121B)),
                    actions = {
                        MoviesAppIconButton(
                            icon = R.drawable.outline_notifications_unread_24
                        )
                        MoviesAppIconButton(
                            icon = R.drawable.outline_menu_24,
                            onClick = {
                                //viewMode.onUiAction(MenuClicked)
                                // navContolller.navigate()
                                // traker.trakc(notification)
                            }
                        )
                    },
                    navigationIcon = {
                        MoviesAppIconButton(
                            icon = R.drawable.outline_person_24
                        )
                    }
                )
            }, containerColor = Color(0xFF0E121B)
        ) { innerPadding ->
            Column(modifier = Modifier.fillMaxWidth().padding(innerPadding)) {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    items(
                        items = movies,
                        key = { movie -> movie.id ?: movie.movieId ?: movie.hashCode() }
                    ) { movie ->
                        MovieCard(movie = movie, onMovieClick)
                    }
                }
            }
        }
    }

    @Composable
    private fun MoviesAppIconButton(
        icon: Int,
        onClick: () -> Unit = {},
        containerColor: Color = Color.White
    ) {
        IconButton(
            onClick = {onClick()},
            colors = IconButtonDefaults.iconButtonColors(containerColor = containerColor)
        ) {
            Icon(
                painter = painterResource(icon),
                contentDescription = null
            )
        }
    }

    @Composable
    fun MovieCard(
        movie: Movie,
        onMovieClick: (Movie) -> Unit,
        modifier: Modifier = Modifier
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = modifier
                    .width(100.dp)
                    .aspectRatio(0.7f),
                elevation = CardDefaults.cardElevation(defaultElevation = 16.dp)
            ) {
                AsyncImage(
                    model = movie.posterPath,
                    contentDescription = movie.id,
                    modifier = Modifier.fillMaxSize(),

                    )
                Spacer(modifier = Modifier.height(10.dp))

            }
            movie.originalTitle?.let { title ->
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(8.dp)
                )
            }

        }
    }
}

private fun MainActivity.IconButton(
    shape: Any,
    onClick: Nothing,
    modifier: Nothing,
    enabled: Nothing,
    colors: Nothing,
    interactionSource: Nothing,
    content: Nothing
) {
}

//fun navigateToMovieDetailsScreen(context: Context){
//  val intent = Intent(context, MovieDetails::class.java)
//    context.startActivity(intent)
//}




data class MoviesResponse(
    @SerializedName("data")
    val movies: List<Movie>
)

data class Movie(
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
    val casts: List<Cast>
) : HomeFeedItem(VIEW_TYPE_LATEST_MOVIES), Parcelable {

    constructor(parcel: Parcel) : this(
        id = parcel.readString(),
        movieId = parcel.readInt(),
        originalTitle = parcel.readString(),
        originalLanguage = parcel.readString(),
        overview = parcel.readString(),
        popularity = parcel.readDouble(),
        posterPath = parcel.readString(),
        backdropPath = parcel.readString(),
        releaseDate = parcel.readString(),
        voteAverage = parcel.readDouble(),
        voteCount = parcel.readInt(),
        adult = parcel.readInt(),
        casts = emptyList()


    )

    override fun describeContents(): Int = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(originalTitle)
        dest.writeString(id)
        dest.writeString(posterPath)
        dest.writeString(backdropPath)
        dest.writeString(releaseDate)
    }

    companion object CREATOR : Parcelable.Creator<Movie> {
        override fun createFromParcel(parcel: Parcel): Movie {
            return Movie(parcel)
        }

        override fun newArray(size: Int): Array<Movie?> {
            return arrayOfNulls(size)
        }
    }
}

data class Cast(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("movie_id")
    val movieId: Int? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("original_name")
    val originalName: String? = null,
    @SerializedName("popularity")
    val popularity: String? = null,
    @SerializedName("profile_path")
    val profilePath: String? = null,
    @SerializedName("character")
    val character: String? = null,
)

interface MoviesApi {
    @GET("/movies/infinite-scroll")
    suspend fun getMoviesList(): MoviesResponse
}

object RetrofitClientBuilder {
    fun create(): MoviesApi {
        return Retrofit.Builder()
            .baseUrl("https://jsonfakery.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create<MoviesApi>()
    }
}

data class HomeFeedState(
    val state: MoviesListState
){
    val homeFeedState get() = listOf<HomeFeedItem>(
        User(
            userName = state.username ?: "Anonymous",
            profilePic = null
        ),
    )
}

data class MoviesListState(
    val loading: Boolean = false,
    val movies: List<Movie> = emptyList(),
    val error: String? = null,
    val username: String? = null,
    val greeting: String  = "Hello",
    val selectedCategory: String = "",
    val chips: List<Category> = categories
){
    val latestMovies get() =
        movies.sortedBy { it.releaseDate }

    val sortedByPopularity get() =
        movies.sortedBy { it.popularity }

    val sortedByReleasedDate get() =
        movies.sortedBy { it.releaseDate }

    val sortedByVoteCount get() =
        movies.sortedBy { it.voteCount }
}


sealed interface MoviesUiEvent {
    object ListFetchedSuccessfully : MoviesUiEvent
    data class ErrorFetchingList(
        val message: Unit
    ) : MoviesUiEvent

    data class NavigateToDetails(
        val movie: Movie
    ) : MoviesUiEvent
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MoviesAppTheme {
        Greeting("Android")
    }
}