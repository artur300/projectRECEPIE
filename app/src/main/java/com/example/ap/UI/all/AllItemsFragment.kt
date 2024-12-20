package com.example.ap.UI.all

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ap.R
import com.example.ap.UI.add.add_button_animation
import com.example.ap.UI.itemViewModel
import com.example.ap.databinding.AllRecipeListBinding

class AllItemsFragment : Fragment() {

    private var _binding : AllRecipeListBinding?=null
    private  val binding get() =_binding!!
    private val viewModel : itemViewModel by activityViewModels()//new



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



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.items?.observe(viewLifecycleOwner) { itemList ->
            binding.recyclerView.adapter = ItemAdapter(
                items = itemList,
                onEdit = { item ->
                    val bundle = Bundle().apply { putParcelable("item", item) }
                    findNavController().navigate(R.id.action_allItemsFragment_to_addItemFragment, bundle)
                },
                onDelete = { item ->
                    viewModel.deleteItem(item)
                },
                onDetails = { item ->
                    val bundle = Bundle().apply { putParcelable("item", item) }
                    findNavController().navigate(R.id.action_allItemsFragment_to_recipeDetailsFragment2, bundle)
                }
            )
            binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 1)
        }
    }





    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}