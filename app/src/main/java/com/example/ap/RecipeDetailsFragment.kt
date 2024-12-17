package com.example.ap

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.ap.databinding.AddRecipeBinding
import com.example.ap.databinding.RecipeDetailsBinding

class RecipeDetailsFragment: Fragment() {

    private var _binding : RecipeDetailsBinding?=null
    private  val binding get() =_binding!!
    private var imageUri: Uri?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=RecipeDetailsBinding.inflate(inflater,container,false)
        //מהוספת פריט בחזרה לרשימה ראשית
        binding.btnBackToCard.setOnClickListener(){
            findNavController().navigate(R.id.action_recipeDetailsFragment2_to_allItemsFragment)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    override fun onDestroy() {
        super.onDestroy()

    }



}