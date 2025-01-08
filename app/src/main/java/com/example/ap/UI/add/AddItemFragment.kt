@file:Suppress("SpellCheckingInspection") // ביטול בדיקת שגיאות כתיב בקובץ

package com.example.ap.UI.add
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.ap.databinding.AddRecipeBinding
import androidx.fragment.app.activityViewModels
import com.example.ap.data.model.Item
import com.example.ap.R
import com.example.ap.UI.itemViewModel
import com.example.ap.utils.ValidationUtils
import com.bumptech.glide.Glide
import android.widget.Toast


class AddItemFragment : Fragment() {

    private var _binding: AddRecipeBinding? = null
    private val binding get() = _binding!!
    private var imageUri: Uri? = null
    private var itemToEdit: Item? = null
    private val viewModel: itemViewModel by activityViewModels()
    private val pickImageLauncher: ActivityResultLauncher<Array<String>> =
        registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri: Uri? ->
            uri?.let {
                Glide.with(this)
                    .load(it)
                    .into(binding.foodImagePreview)

                requireActivity().contentResolver.takePersistableUriPermission(
                    uri, Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
                imageUri = uri
            }
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = AddRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            itemToEdit = arguments?.getParcelable("item", Item::class.java)
        }

        itemToEdit?.let { item ->
            binding.foodNameInput.setText(item.foodName)
            binding.authorNameInput.setText(item.authorName)
            binding.foodDescriptionInput.setText(item.description)
            binding.ingredientsDescriptionInput.setText(item.ingredients)
            imageUri = item.imageUri?.let { Uri.parse(it) }
            binding.foodImagePreview.setImageURI(imageUri)
        }

        binding.btnAddFood.text = if (itemToEdit == null) getString(R.string.add_recipe) else getString(R.string.update_recipe)
        binding.btnAddFood.setOnClickListener {
            if (ValidationUtils.validateFields(requireContext(), binding, imageUri)) {
                val newItem = Item(
                    binding.foodNameInput.text.toString(),
                    binding.authorNameInput.text.toString(),
                    binding.foodDescriptionInput.text.toString(),
                    binding.ingredientsDescriptionInput.text.toString(),
                    imageUri?.toString()
                )

                if (itemToEdit == null) {
                    viewModel.addItem(newItem)
                    Toast.makeText(requireContext(), getString(R.string.item_added), Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.deleteItem(itemToEdit!!)
                    viewModel.addItem(newItem)
                    Toast.makeText(requireContext(), getString(R.string.item_updated), Toast.LENGTH_SHORT).show()
                }

                findNavController().navigate(R.id.action_addItemFragment_to_allItemsFragment)
            }
        }

        binding.btnPickImage.setOnClickListener {
            pickImageLauncher.launch(arrayOf("image/*"))
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_addItemFragment_to_allItemsFragment)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
