package com.example.ap.UI.add

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.ap.databinding.AddRecipeBinding
import android.widget.Toast
import com.example.ap.data.model.Item
import com.example.ap.data.model.ItemManager
import com.example.ap.R


class AddItemFragment : Fragment() {

    private var _binding: AddRecipeBinding? = null
    private val binding get() = _binding!!
    private var imageUri: Uri? = null
    private var itemToEdit: Item? = null

    val pickImageLauncher: ActivityResultLauncher<Array<String>> =
        registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri: Uri? ->
            uri?.let {
                val fileSize = requireActivity().contentResolver.openAssetFileDescriptor(it, "r")?.length ?: 0

                val maxSize = 2 * 1024 * 1024
                if (fileSize > maxSize) {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.image_too_large),
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    binding.foodImagePreview.setImageURI(uri)
                    requireActivity().contentResolver.takePersistableUriPermission(
                        uri, Intent.FLAG_GRANT_READ_URI_PERMISSION
                    )
                    imageUri = uri
                }
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

        // קבלת הפריט במצב עריכה
        itemToEdit = arguments?.getParcelable("item")
        itemToEdit?.let { item ->
            binding.foodNameInput.setText(item.foodName)
            binding.authorNameInput.setText(item.authorName)
            binding.foodDescriptionInput.setText(item.description)
            binding.ingredientsDescriptionInput.setText(item.ingredients)
            imageUri = item.imageUri
            binding.foodImagePreview.setImageURI(item.imageUri)
        }

        binding.btnAddFood.text = if (itemToEdit == null) "הוסף מתכון" else "עדכן מתכון"

        // שמירת השינויים או הוספה
        binding.btnAddFood.setOnClickListener {
            val newItem = Item(
                binding.foodNameInput.text.toString(),
                binding.authorNameInput.text.toString(),
                binding.foodDescriptionInput.text.toString(),
                binding.ingredientsDescriptionInput.text.toString(),
                imageUri
            )

            if (itemToEdit == null) {
                // מצב הוספה
                ItemManager.add(newItem)
            } else {
                // מצב עדכון
                val index = ItemManager.items.indexOf(itemToEdit)
                if (index != -1) {
                    ItemManager.items[index] = newItem
                }
            }

            findNavController().navigate(R.id.action_addItemFragment_to_allItemsFragment)
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
