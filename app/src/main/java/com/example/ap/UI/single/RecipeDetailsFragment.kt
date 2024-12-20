package com.example.ap.UI.single

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.ap.data.model.Item
import com.example.ap.R
import com.example.ap.UI.itemViewModel
import com.example.ap.databinding.RecipeDetailsBinding

@Suppress("DEPRECATION")
class RecipeDetailsFragment : Fragment() {

    private var _binding: RecipeDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel : itemViewModel by activityViewModels()

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




        binding.btnDelete.setOnClickListener {
            item?.let {
                viewModel.deleteItem(it)
                findNavController().navigate(R.id.action_recipeDetailsFragment2_to_allItemsFragment)
            }
        }

        binding.btnEditDetails.setOnClickListener {
            val bundle = Bundle().apply { putParcelable("item", item) }
            findNavController().navigate(R.id.action_recipeDetailsFragment2_to_addItemFragment, bundle)
        }



        binding.btnBackToCard.setOnClickListener(){
            findNavController().navigate(R.id.action_recipeDetailsFragment2_to_allItemsFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

