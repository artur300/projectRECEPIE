@file:Suppress("SpellCheckingInspection") // ביטול בדיקת שגיאות כתיב בקובץ

package com.example.ap.UI.single

import android.content.Intent
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
import com.example.ap.UI.add.add_button_animation
import com.example.ap.UI.itemViewModel
import com.example.ap.databinding.RecipeDetailsBinding

// RecipeDetailsFragment: מסך שמציג את הפרטים המלאים של מתכון מסוים.
@Suppress("DEPRECATION")
class RecipeDetailsFragment : Fragment() {

    // משתנה שמחזיק את ה-Binding של המסך.
    private var _binding: RecipeDetailsBinding? = null
    private val binding get() = _binding!!

    // ViewModel לגישה ולניהול המידע של הפריטים.
    private val viewModel: itemViewModel by activityViewModels()

    // יצירת עיצוב המסך (Binding).
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RecipeDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    // לוגיקה שמופעלת לאחר יצירת המסך.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // קבלת האובייקט שהועבר (פריט מתכון).
        val item: Item? = arguments?.getParcelable("item")

        // מילוי הפרטים בתצוגה אם קיים פריט.
        item?.let {
            binding.foodName.text = it.foodName

            val context = binding.root.context
            val author = item.authorName.ifEmpty { R.string.Unknown_message }
            binding.authorName.text = context.getString(R.string.author_unknown_card, author)

            binding.ingredients.setText(it.ingredients)
            binding.foodDescription.setText(it.description)

            // טעינת תמונת המתכון (אם קיימת) באמצעות Glide.
            Glide.with(this)
                .load(it.imageUri)
                .into(binding.foodImage)
        }

        // לחצן מחיקת המתכון.
        binding.btnDelete.setOnClickListener {
            item?.let {
                viewModel.deleteItem(it) // מחיקת הפריט דרך ה-ViewModel.
                findNavController().navigate(R.id.action_recipeDetailsFragment2_to_allItemsFragment) // חזרה לרשימת הפריטים.
            }
        }

        // לחצן עריכת המתכון.
        binding.btnEditDetails.setOnClickListener {
            val bundle = Bundle().apply { putParcelable("item", item) } // העברת הפריט לעריכה.
            findNavController().navigate(R.id.action_recipeDetailsFragment2_to_addItemFragment, bundle)
        }

        // לחצן חזרה לרשימת הכרטיסיות.
        binding.btnBackToCard.setOnClickListener {
            findNavController().navigate(R.id.action_recipeDetailsFragment2_to_allItemsFragment)
        }






        binding.btnShare.setOnClickListener {view ->
            add_button_animation.scaleView(view)
            item?.let {
                val shareText = """
            🥘 ${it.foodName}
            
            👨‍🍳 ${getString(R.string.author_unknown_card, it.authorName)}
            
            📋 ${getString(R.string.ingredients_view)}: ${it.ingredients}
            
            📝 ${getString(R.string.description_view)}: ${it.description}
        """.trimIndent()

                val shareIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, shareText)
                    type = "text/plain"
                }

                // הפעלת כוונת השיתוף
                startActivity(Intent.createChooser(shareIntent, getString(R.string.share)))
            }
        }






    }

    // ניקוי ה-Binding למניעת דליפות זיכרון.
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
