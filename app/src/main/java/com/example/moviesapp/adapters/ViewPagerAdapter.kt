package com.example.moviesapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.moviesapp.data.model.NewMovies
import com.example.moviesapp.ui.activities.Movie
import com.example.moviesapp.databinding.ItemMoviePageBinding

class ViewPagerAdapter(
    ): RecyclerView.Adapter<ViewPagerAdapter.PageViewHolder>() {
    private var moviesList: List<Movie> = emptyList()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PageViewHolder {
        val binding = ItemMoviePageBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PageViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: PageViewHolder,
        position: Int
    ) {
        val movie = moviesList[position]
        holder.binding.ivMovieItem.load(movie.posterPath)
    }

    fun submitList(newMovies: List<Movie>){
        moviesList = newMovies
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    class PageViewHolder(val binding: ItemMoviePageBinding): RecyclerView.ViewHolder(binding.root)

}