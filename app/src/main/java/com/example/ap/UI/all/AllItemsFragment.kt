@file:Suppress("SpellCheckingInspection") // ביטול בדיקת שגיאות כתיב בקובץ

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

// AllItemsFragment: מסך שמציג את כל הפריטים (רשימת המתכונים).
class AllItemsFragment : Fragment() {

    // משתנה שמחזיק את העיצוב של המסך.
    private var _binding: AllRecipeListBinding? = null
    private val binding get() = _binding!!

    // ViewModel לשמירה ולעדכון המידע של הפריטים (LiveData).
    private val viewModel: itemViewModel by activityViewModels()

    // יצירת עיצוב המסך (Binding).
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // אינפלציה של ה-Layout וקישור ה-Binding.
        _binding = AllRecipeListBinding.inflate(inflater, container, false)

        // לחיצה על כפתור התפריט (menuIcon) שמבצעת אנימציה ומעבירה למסך הוספת מתכון.
        binding.menuIcon.setOnClickListener { view ->
            add_button_animation.scaleView(view) {
                findNavController().navigate(R.id.action_allItemsFragment_to_addItemFragment)
            }
        }

        return binding.root
    }

    // לוגיקה שמופעלת אחרי שהמסך נוצר.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // יצירת מתאם (Adapter) לרשימה עם פעולות עריכה, מחיקה וצפייה בפרטים.
        val adapter = ItemAdapter(
            onEdit = { item ->
                // מעבר למסך העריכה עם הפריט שנבחר.
                val bundle = Bundle().apply { putParcelable("item", item) }
                findNavController().navigate(R.id.action_allItemsFragment_to_addItemFragment, bundle)
            },
            onDelete = { item ->
                // מחיקת פריט מהרשימה.
                viewModel.deleteItem(item) // הרשימה מתעדכנת אוטומטית דרך ה-LiveData.
            },
            onDetails = { item ->
                // מעבר למסך פרטי הפריט.
                val bundle = Bundle().apply { putParcelable("item", item) }
                findNavController().navigate(R.id.action_allItemsFragment_to_recipeDetailsFragment2, bundle)
            }
        )

        // הגדרת המתאם והעיצוב לרשימה (RecyclerView).
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 1) // עיצוב טור אחד.

        // צפייה ב-LiveData של הפריטים והעברתם למתאם.
        viewModel.items?.observe(viewLifecycleOwner) { itemList ->
            adapter.submitList(itemList) // מעביר את הרשימה ל-Adapter.
        }
    }

    // ניקוי ה-Binding כדי למנוע דליפות זיכרון.
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

