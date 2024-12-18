package com.example.ap

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.ap.databinding.RecipeDetailsBinding

@Suppress("DEPRECATION")
class RecipeDetailsFragment : Fragment() {

    private var _binding: RecipeDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RecipeDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // קבלת האובייקט שהועבר
        //@Suppress("DEPRECATION") on this fragment!!!!!
        val item: Item? = arguments?.getParcelable("item")

        item?.let {
            binding.foodName.text = it.foodName
            binding.authorName.text = it.authorName
            binding.ingredients.setText(it.ingredients)
            binding.foodDescription.setText(it.description)
            Glide.with(this)
                .load(it.imageUri)
                .into(binding.foodImage)
        }


        // מחיקת הפריט
        binding.btnDelete.setOnClickListener {
            item?.let { currentItem ->
                ItemManager.items.remove(currentItem)  // מסיר את האובייקט מהרשימה
                findNavController().navigate(R.id.action_recipeDetailsFragment2_to_allItemsFragment)
            }

        }


        // כפתור חזרה אחורה
        binding.btnBackToCard.setOnClickListener(){
            findNavController().navigate(R.id.action_recipeDetailsFragment2_to_allItemsFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

