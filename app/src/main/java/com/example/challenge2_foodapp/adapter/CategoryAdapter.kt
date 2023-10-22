package com.example.challenge2_foodapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.challenge2_foodapp.R
import com.example.challenge2_foodapp.data.api.Category
import com.example.challenge2_foodapp.databinding.ItemCategoryBinding

class CategoryAdapter(private val list: List<Category>) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Category)
    }

    class ViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Category) {
            Glide.with(itemView.context)
                .load(data.image_url)
                .into(binding.ivCategory)

            binding.tvCategory.text = data.nama
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding =
            ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position]
        holder.bind(data)
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(list[holder.adapterPosition]) }

        // Identifikasi apakah item ini adalah item terakhir
        val isLastItem = position == itemCount - 1

        // Set marginend berdasarkan kondisi
        val marginEnd =
            if (isLastItem) 0 else holder.itemView.context.resources.getDimensionPixelSize(
                R.dimen.category_spacing
            )

        val layoutParams = holder.itemView.layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.marginEnd = marginEnd
        holder.itemView.layoutParams = layoutParams
    }

    override fun getItemCount(): Int {
        return list.size
    }
}