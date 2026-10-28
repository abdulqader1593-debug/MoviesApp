package com.example.moviesapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp.ui.activities.Movie
import com.example.moviesapp.databinding.ItemLatestMoviesBinding
import com.example.moviesapp.viewmodels.MoviesViewModel

class LatestMovieAdapter(
    private val moviesViewModel: MoviesViewModel
): RecyclerView.Adapter<LatestMovieAdapter.LatestMovieViewHolder>() {
    private var movieList: List<Movie> = emptyList()
    class LatestMovieViewHolder( val binding: ItemLatestMoviesBinding, val viewModel: MoviesViewModel) : RecyclerView.ViewHolder(binding.root){
        init {
            binding.viewModel =  viewModel
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LatestMovieAdapter.LatestMovieViewHolder {
        val binding = ItemLatestMoviesBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return LatestMovieViewHolder(binding, viewModel = moviesViewModel)
    }

    override fun onBindViewHolder(
        holder: LatestMovieAdapter.LatestMovieViewHolder,
        position: Int
    ) {
        val movie = movieList[position]
        holder.binding.movies = movie
        holder.binding.executePendingBindings()
//        holder.binding.ivLatestMovie.load(movie.posterPath){
//            transformations(RoundedCornersTransformation(25f))
//        }
    }
    fun submitList(newList: List<Movie>){
        movieList = newList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return movieList.size
    }
}