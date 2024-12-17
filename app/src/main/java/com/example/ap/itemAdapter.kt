package com.example.ap

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.navigation.findNavController
import com.example.ap.databinding.RecipeCardBinding
import com.bumptech.glide.Glide

class ItemAdapter(
    private val items: List<Item>
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    class ItemViewHolder(private val binding: RecipeCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Item) {
            // הצגת נתונים בכרטיסיה
            binding.foodName.text = item.foodName
            binding.authorName.text = item.authorName

            Glide.with(binding.root.context)
                .load(item.imageUri)
                .diskCacheStrategy(com.bumptech.glide.load.engine.DiskCacheStrategy.DATA)
                .into(binding.foodImage)

            // ניווט למסך הפרטים בלחיצה על הכפתור "More Details"
            binding.btnViewDetails.setOnClickListener { view ->
                val bundle = Bundle().apply {
                    putString("foodName", item.foodName)
                    putString("authorName", item.authorName)
                    putString("description", item.description)
                    putString("ingredients", item.ingredients)
                    putString("imageUri", item.imageUri?.toString())
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
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size
}
