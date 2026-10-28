package com.example.moviesapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp.ui.activities.Category
import com.example.moviesapp.databinding.ItemCategoryChipBinding
import com.example.moviesapp.viewmodels.MoviesViewModel

class ChipGroupAdapter(
    private val viewModel: MoviesViewModel
): RecyclerView.Adapter<ChipGroupAdapter.ChipGroupHolder>() {
    private var chipList: List<Category> = emptyList()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ChipGroupHolder {
        val binding = ItemCategoryChipBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChipGroupHolder(binding,viewModel)
    }

    override fun onBindViewHolder(
        holder: ChipGroupHolder,
        position: Int
    ) {
        val category = chipList[position]
        holder.bind(category)
    }

    fun submitList(newList: List<Category>){
        chipList = newList
       // notifyDataSetChanged()
    }


    override fun getItemCount(): Int {
        return chipList.size
    }

    class ChipGroupHolder(val binding: ItemCategoryChipBinding,val viewModel: MoviesViewModel) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.viewModel = viewModel
        }
        fun bind(category: Category){
            binding.category = category
           // binding.viewModel = viewModel
//            if(position == selectedPosition){
//                binding.tvCategoryName.setBackgroundResource(R.drawable.bg_category_chip_selected)
//            }else{
//                binding.tvCategoryName.setBackgroundResource(R.drawable.bg_category_chip_unselected)
//            }
//
//            binding.root.setOnClickListener {
//                val previousPosition = selectedPosition // 1
//                selectedPosition = bindingAdapterPosition // 1212121
//                notifyItemChanged(previousPosition)
//                notifyItemChanged(selectedPosition)
//
//            }
//        binding.root.setOnClickListener {
//            onCategoryClick(category)
//        }
        }
    }

}