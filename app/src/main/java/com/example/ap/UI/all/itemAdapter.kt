package com.example.ap.UI.all

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.navigation.findNavController
import com.example.ap.databinding.RecipeCardBinding
import com.bumptech.glide.Glide
import com.example.ap.data.model.Item
import com.example.ap.data.model.ItemManager
import com.example.ap.R

class ItemAdapter(
    private val items: MutableList<Item>,
    private val onItemDeleted: () -> Unit
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    class ItemViewHolder(private val binding: RecipeCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Item, onDelete: () -> Unit) {
            binding.foodName.text = item.foodName
            binding.authorName.text = item.authorName

            Glide.with(binding.root.context)
                .load(item.imageUri)
                .into(binding.foodImage)

            binding.btnDelete.setOnClickListener {
                onDelete()
            }




            binding.btnEdit.setOnClickListener { view ->
                val bundle = Bundle().apply {
                    putParcelable("item", item) // מעבירים את הפריט לעריכה
                }
                view.findNavController().navigate(
                    R.id.action_allItemsFragment_to_addItemFragment,
                    bundle
                )
            }









            binding.btnViewDetails.setOnClickListener { view ->
                val bundle = Bundle().apply {
                    putParcelable("item", item)
                }
                view.findNavController().navigate(
                    R.id.action_allItemsFragment_to_recipeDetailsFragment2,
                    bundle
                )
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
        holder.bind(items[position]) {
            val deletedItem = items[position]
            ItemManager.remove(deletedItem)
            notifyItemRemoved(position)
            onItemDeleted()
        }
    }

    override fun getItemCount() = items.size
}
