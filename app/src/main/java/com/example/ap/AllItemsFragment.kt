package com.example.ap

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
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
        binding.menuIcon.setOnClickListener { view ->
            add_button_animation.scaleView(view) {
                findNavController().navigate(R.id.action_allItemsFragment_to_addItemFragment)
            }
        }

        return binding.root

    }



    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.recyclerView.adapter = ItemAdapter(ItemManager.items){
            binding.recyclerView.adapter?.notifyDataSetChanged()
        }
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 1)

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}