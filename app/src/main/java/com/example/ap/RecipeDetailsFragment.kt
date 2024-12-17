package com.example.ap

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.ap.databinding.RecipeDetailsBinding
import androidx.navigation.fragment.findNavController

class RecipeDetailsFragment : Fragment() {

    private var _binding: RecipeDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RecipeDetailsBinding.inflate(inflater, container, false)

        // קבלת המידע מה-Bundle
        arguments?.let {
            binding.foodName.text = it.getString("foodName")
            binding.authorName.text = it.getString("authorName")
            binding.foodDescription.setText(it.getString("description"))
            binding.ingredients.setText(it.getString("ingredients"))

            val imageUriString = it.getString("imageUri")
            val imageUri = imageUriString?.let { Uri.parse(it) }
            if (imageUri != null) {
                Glide.with(this)
                    .load(imageUri)
                    .into(binding.foodImage)
            }
        }

        // כפתור חזרה
        binding.btnBackToCard.setOnClickListener(){
            findNavController().navigate(R.id.action_recipeDetailsFragment2_to_allItemsFragment)}


            return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}




