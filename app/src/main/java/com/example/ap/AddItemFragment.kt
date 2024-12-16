package com.example.ap

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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