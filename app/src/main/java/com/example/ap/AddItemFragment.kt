package com.example.ap

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.ap.databinding.AddRecipeBinding


class AddItemFragment : Fragment() {

    private var _binding : AddRecipeBinding?=null
    private  val binding get() =_binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding=AddRecipeBinding.inflate(inflater,container,false)
        //מהוספת פריט בחזרה לרשימה ראשית
        binding.btnBack.setOnClickListener(){
            findNavController().navigate(R.id.action_addItemFragment_to_allItemsFragment)
        }

        binding.btnAddFood.setOnClickListener(){
            val bundle = bundleOf(
                "title" to binding.foodNameInput.text.toString(),           // שם האוכל
                "author" to binding.authorNameInput.text.toString(),       // שם המחבר
                "description" to binding.foodDescriptionInput.text.toString(), // תיאור
                "ingredients" to binding.ingredientsDescriptionInput.text.toString() // רכיבים
            )
            findNavController().navigate(R.id.action_addItemFragment_to_allItemsFragment)
        }


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }



}