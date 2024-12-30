@file:Suppress("SpellCheckingInspection") // ביטול בדיקת שגיאות כתיב בקובץ

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
import androidx.fragment.app.activityViewModels
import com.example.ap.data.model.Item
import com.example.ap.R
import com.example.ap.UI.itemViewModel
import com.example.ap.utils.ValidationUtils
import com.bumptech.glide.Glide
import android.widget.Toast


// AddItemFragment: מסך שמאפשר למשתמש להוסיף או לערוך מתכון.
class AddItemFragment : Fragment() {

    // מחזיק את האובייקט של עיצוב המסך (View Binding).
    private var _binding: AddRecipeBinding? = null
    private val binding get() = _binding!!

    // משתנים לשמירת כתובת התמונה והפריט שנערך.
    private var imageUri: Uri? = null
    private var itemToEdit: Item? = null

    // ViewModel לשמירת המידע הקשור לפריטים.
    private val viewModel: itemViewModel by activityViewModels()

    // משגר בחירת תמונה (ActivityResultLauncher) עבור בחירת תמונה מהגלריה.
    val pickImageLauncher: ActivityResultLauncher<Array<String>> =
        registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri: Uri? ->
            uri?.let {
                val fileSize = requireActivity().contentResolver.openAssetFileDescriptor(it, "r")?.length ?: 0
                val maxSize = 2 * 1024 * 1024 // גודל תמונה מקסימלי: 2MB.

                if (fileSize > maxSize) {
                    // קיבוץ התמונה בעזרת Glide
                    Glide.with(this)
                        .asBitmap()
                        .load(it)
                        .override(500, 500) // התאמה לגודל מרבי
                        .into(binding.foodImagePreview)

                    // שמירת כתובת התמונה
                    requireActivity().contentResolver.takePersistableUriPermission(
                        uri, Intent.FLAG_GRANT_READ_URI_PERMISSION
                    )
                    imageUri = uri
                } else {
                    // הצגת התמונה בתצוגה ושמירת הכתובת שלה.
                    binding.foodImagePreview.setImageURI(uri)
                    requireActivity().contentResolver.takePersistableUriPermission(
                        uri, Intent.FLAG_GRANT_READ_URI_PERMISSION
                    )
                    imageUri = uri
                }
            }
        }


    // יצירת עיצוב המסך.
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = AddRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    // לוגיקה שמופעלת לאחר יצירת המסך.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // אם מדובר בעריכת פריט, מילוי השדות במידע הקיים.
        itemToEdit = arguments?.getParcelable("item")
        itemToEdit?.let { item ->
            binding.foodNameInput.setText(item.foodName)
            binding.authorNameInput.setText(item.authorName)
            binding.foodDescriptionInput.setText(item.description)
            binding.ingredientsDescriptionInput.setText(item.ingredients)
            imageUri = item.imageUri?.let { Uri.parse(it) }
            binding.foodImagePreview.setImageURI(imageUri)
        }

        // שינוי הטקסט של הכפתור לפי מצב (הוספה או עריכה).
        binding.btnAddFood.text = if (itemToEdit == null) getString(R.string.add_recipe) else getString(R.string.update_recipe)

        // לוגיקה ללחיצה על כפתור הוספה/עדכון.
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
                    // הוספת מתכון חדש.
                    viewModel.addItem(newItem)
                    Toast.makeText(requireContext(), getString(R.string.item_added), Toast.LENGTH_SHORT).show()
                } else {
                    // מחיקת המתכון הישן והוספת המתכון החדש.
                    viewModel.deleteItem(itemToEdit!!)
                    viewModel.addItem(newItem)
                    Toast.makeText(requireContext(), getString(R.string.item_updated), Toast.LENGTH_SHORT).show()
                }

                // מעבר למסך שמציג את כל המתכונים.
                findNavController().navigate(R.id.action_addItemFragment_to_allItemsFragment)
            }
        }

        // לוגיקה ללחיצה על כפתור בחירת תמונה.
        binding.btnPickImage.setOnClickListener {
            pickImageLauncher.launch(arrayOf("image/*"))
        }

        // לוגיקה ללחיצה על כפתור חזרה.
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_addItemFragment_to_allItemsFragment)
        }
    }

    // ניקוי ה-Binding כשמסיימים עם המסך.
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
