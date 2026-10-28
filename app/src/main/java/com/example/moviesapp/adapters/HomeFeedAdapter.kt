package com.example.moviesapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.moviesapp.data.model.Categories
import com.example.moviesapp.data.model.HomeFeedItem
import com.example.moviesapp.data.model.LatestMovies
import com.example.moviesapp.data.model.NewMovies
import com.example.moviesapp.data.model.Section
import com.example.moviesapp.data.model.TrendingMovies
import com.example.moviesapp.data.model.User
import com.example.moviesapp.databinding.ItemCategoryChipsBinding
import com.example.moviesapp.databinding.ItemHomeSectionBinding
import com.example.moviesapp.databinding.ItemLatestMoviesSectionBinding
import com.example.moviesapp.databinding.ItemNewMoviesBinding
import com.example.moviesapp.databinding.ItemTopbarViewBinding
import com.example.moviesapp.databinding.ItemTrendingMoviesSectionBinding
import com.example.moviesapp.ui.activities.categories
import com.example.moviesapp.viewmodels.MoviesViewModel

const val VIEW_TYPE_TOB_BAR = 1
const val VIEW_TYPE_NEW_MOVIES = 2
const val VIEW_TYPE_SECTION_HEADER = 3
const val VIEW_TYPE_CATEGORIES = 4
const val VIEW_TYPE_LATEST_MOVIES = 5
const val VIEW_TYPE_TRENDING_MOVIES = 6

class HomeFeedListAdapter(private val viewModel: MoviesViewModel): ListAdapter<HomeFeedItem, ViewHolder>(
    DiffUtilCallback
){

    override fun getItemViewType(position: Int): Int {
        val currentItem = getItem(position)
        return currentItem.viewType
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return when (viewType) {
            VIEW_TYPE_TOB_BAR -> {
                TopBarViewHolder(
                    binding = ItemTopbarViewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
                )
            }
            VIEW_TYPE_NEW_MOVIES -> {
                NewMoviesViewHolder(
                    binding = ItemNewMoviesBinding.inflate(LayoutInflater.from(parent.context),parent,false)
                )
            }
            VIEW_TYPE_SECTION_HEADER -> {
                SectionHeaderViewHolder(
                    binding = ItemHomeSectionBinding.inflate(LayoutInflater.from(parent.context),parent,false)
                )
            }
            VIEW_TYPE_CATEGORIES -> {
                CategoriesViewHolder(
                    binding = ItemCategoryChipsBinding.inflate(LayoutInflater.from(parent.context),parent,false),
                    viewModel = viewModel
                )
            }
            VIEW_TYPE_LATEST_MOVIES -> {
                LatestMoviesViewHolder(
                    binding = ItemLatestMoviesSectionBinding.inflate(LayoutInflater.from(parent.context),parent,false),
                    viewModel = viewModel
                )
            }
            VIEW_TYPE_TRENDING_MOVIES -> {
                TrendingMoviesViewHolder(
                    binding = ItemTrendingMoviesSectionBinding.inflate(LayoutInflater.from(parent.context),parent,false),
                    viewModel = viewModel
                )
            }

            else -> error("ViewType Not Supported")
        }
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val item = getItem(position)
        val itemViewType = item.viewType
        when(itemViewType){
            VIEW_TYPE_TOB_BAR -> (holder as? TopBarViewHolder)?.bind(item as User)
            VIEW_TYPE_NEW_MOVIES -> (holder as? NewMoviesViewHolder)?.bind(item)
            VIEW_TYPE_SECTION_HEADER -> (holder as? SectionHeaderViewHolder)?.bind(item)
            VIEW_TYPE_CATEGORIES ->  (holder as? CategoriesViewHolder)?.bind(item)
            VIEW_TYPE_LATEST_MOVIES -> (holder as? LatestMoviesViewHolder)?.bind(item)
            VIEW_TYPE_TRENDING_MOVIES -> (holder as? TrendingMoviesViewHolder)?.bind(item)
        }
    }
}

object DiffUtilCallback: DiffUtil.ItemCallback<HomeFeedItem>() {

    // All Chip -> isSelected = false VS All Chips -> isSelected = true
    override fun areItemsTheSame(
        oldItem: HomeFeedItem,
        newItem: HomeFeedItem
    ): Boolean {
        // Categories -> "All" -> false -> "-categories"
        // Categories -> "All" -> true -> "All-categories"
        val oldItemId = if (oldItem is Categories){
            oldItem.id
        } else {
            null
        }

        val newItemId = if (newItem is Categories){
            newItem.id
        } else {
            null
        }
       // require(oldItem is Categories)
        //val itemId = (oldItem as? Categories)?.id
       // val newItemId = (oldItem as? Categories)?.id
        return oldItem.viewType == newItem.viewType && oldItemId == newItemId
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(
        oldItem: HomeFeedItem,
        newItem: HomeFeedItem
    ): Boolean {
        return oldItem == newItem
    }
}


class HomeFeedAdapter(private val viewModel: MoviesViewModel) : RecyclerView.Adapter<ViewHolder>(){

    private var items = emptyList<HomeFeedItem>()

    override fun getItemViewType(position: Int): Int {
        val currentItem = items[position]
        return currentItem.viewType
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return when (viewType) {
            VIEW_TYPE_TOB_BAR -> {
                TopBarViewHolder(
                    binding = ItemTopbarViewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
                )
            }
            VIEW_TYPE_NEW_MOVIES -> {
                NewMoviesViewHolder(
                    binding = ItemNewMoviesBinding.inflate(LayoutInflater.from(parent.context),parent,false)
                )
            }
            VIEW_TYPE_SECTION_HEADER -> {
                SectionHeaderViewHolder(
                    binding = ItemHomeSectionBinding.inflate(LayoutInflater.from(parent.context),parent,false)
                )
            }
            VIEW_TYPE_CATEGORIES -> {
                CategoriesViewHolder(
                    binding = ItemCategoryChipsBinding.inflate(LayoutInflater.from(parent.context),parent,false),
                    viewModel = viewModel
                )
            }
            VIEW_TYPE_LATEST_MOVIES -> {
                LatestMoviesViewHolder(
                    binding = ItemLatestMoviesSectionBinding.inflate(LayoutInflater.from(parent.context),parent,false),
                    viewModel = viewModel
                )
            }
            VIEW_TYPE_TRENDING_MOVIES -> {
                TrendingMoviesViewHolder(
                    binding = ItemTrendingMoviesSectionBinding.inflate(LayoutInflater.from(parent.context),parent,false),
                    viewModel = viewModel
                )
            }

            else -> error("ViewType Not Supported")
        }
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val item = items[position]
        val itemViewType = item.viewType
        when(itemViewType){
            VIEW_TYPE_TOB_BAR -> (holder as? TopBarViewHolder)?.bind(item as User)
            VIEW_TYPE_NEW_MOVIES -> (holder as? NewMoviesViewHolder)?.bind(item)
            VIEW_TYPE_SECTION_HEADER -> (holder as? SectionHeaderViewHolder)?.bind(item)
            VIEW_TYPE_CATEGORIES ->  (holder as? CategoriesViewHolder)?.bind(item)
            VIEW_TYPE_LATEST_MOVIES -> (holder as? LatestMoviesViewHolder)?.bind(item)
            VIEW_TYPE_TRENDING_MOVIES -> (holder as? TrendingMoviesViewHolder)?.bind(item)
        }
    }

    override fun getItemCount() = items.size

    fun submitList(items: List<HomeFeedItem>){
        this.items = items
        notifyDataSetChanged()
    }
}


class TopBarViewHolder(
    private val binding: ItemTopbarViewBinding
): ViewHolder(binding.root){

    fun bind(section: HomeFeedItem){
        require(section is User)
        binding.user = section
        binding.executePendingBindings()
    }
}

class NewMoviesViewHolder(
    private val binding: ItemNewMoviesBinding
): ViewHolder(binding.root){

    fun bind(item: HomeFeedItem){
        binding.movies = item as NewMovies
        binding.movieViewPager.adapter = ViewPagerAdapter()
        binding.executePendingBindings()
    }
}

class SectionHeaderViewHolder(
    private val binding: ItemHomeSectionBinding
): ViewHolder(binding.root){

    fun bind(section: HomeFeedItem){
        require(section is Section)
        binding.section = section
        binding.executePendingBindings()
    }
}

class CategoriesViewHolder(
    private val binding: ItemCategoryChipsBinding,
    private val viewModel: MoviesViewModel
): ViewHolder(binding.root){

    init {
        binding.rvCategories.adapter = ChipGroupAdapter(viewModel)
    }
    fun bind(section: HomeFeedItem){
        require(section is Categories)
        binding.categories = section
    }
}


class LatestMoviesViewHolder(
    private val binding: ItemLatestMoviesSectionBinding,
    private val viewModel: MoviesViewModel
): ViewHolder(binding.root){

    fun bind(section: HomeFeedItem){
        require(section is LatestMovies)
        binding.rvLatestMovieSection.adapter = LatestMovieAdapter(viewModel)
        binding.latestMovies = section
        binding.executePendingBindings()
    }
}

class TrendingMoviesViewHolder(
    private val binding: ItemTrendingMoviesSectionBinding,
    private val viewModel: MoviesViewModel
): ViewHolder(binding.root){

    fun bind(section: HomeFeedItem){
        require(section is TrendingMovies)
        binding.rvLatestMovieSection.adapter = LatestMovieAdapter(viewModel)
        binding.trendingMovies = section
        binding.executePendingBindings()
    }
}



