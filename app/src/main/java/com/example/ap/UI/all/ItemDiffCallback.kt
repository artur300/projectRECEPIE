package com.example.ap.UI.all

import androidx.recyclerview.widget.DiffUtil
import com.example.ap.data.model.Item

class ItemDiffCallback : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        // השוואת ה-ID של האובייקטים
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        // השוואה מלאה של התוכן
        return oldItem == newItem
    }
}

//בשביל ליצור אנימציה של מחיקת כרטיסיות