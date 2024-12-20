package com.example.ap.UI.all

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ap.databinding.RecipeCardBinding
import com.bumptech.glide.Glide
import com.example.ap.data.model.Item


class ItemAdapter(
    private val items: List<Item>,
    private val onEdit: (Item) -> Unit,
    private val onDelete: (Item) -> Unit,
    private val onDetails: (Item) -> Unit
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    class ItemViewHolder(private val binding: RecipeCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Item, onEdit: (Item) -> Unit, onDelete: (Item) -> Unit, onDetails: (Item) -> Unit) {
            binding.foodName.text = item.foodName
            binding.authorName.text = item.authorName

            Glide.with(binding.root.context)
                .load(item.imageUri)
                .into(binding.foodImage)

            binding.btnEdit.setOnClickListener {
                onEdit(item)
            }

            binding.btnDelete.setOnClickListener {
                onDelete(item)
            }

            binding.btnViewDetails.setOnClickListener {
                onDetails(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ItemViewHolder(
            RecipeCardBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position], onEdit, onDelete, onDetails)
    }

    override fun getItemCount() = items.size
}
