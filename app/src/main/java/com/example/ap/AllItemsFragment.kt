package com.example.ap

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ap.databinding.AllRecipeListBinding
import com.example.menu.utils.add_button_animation

class AllItemsFragment : Fragment() {

private var _binding : AllRecipeListBinding?=null
    private  val binding get() =_binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding=AllRecipeListBinding.inflate(inflater,container,false)
        //מעבר מתפריט ראשי להוספת פריט
        binding.menuIcon.setOnClickListener { view ->
            add_button_animation.scaleView(view) {
                // ניווט לאחר סיום האנימציה
                findNavController().navigate(R.id.action_allItemsFragment_to_addItemFragment)
            }
        }







        return binding.root

    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)





        binding.recyclerView.adapter = itemAdapter(ItemManager.items)
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 1)

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}