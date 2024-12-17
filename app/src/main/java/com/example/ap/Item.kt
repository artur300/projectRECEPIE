package com.example.ap

data class Item (
    val foodName: String,            // שם המתכון
    val authorName: String,          // שם המחבר
    val description: String,         // תיאור המתכון
    val ingredients: String,         // רכיבים
    val imageUri: String? = null     // כתובת התמונה (רשות)
)


// ItemManager: מנהל רשימה זמנית של פריטים בזיכרון האפליקציה.
// מאפשר להוסיף ולהסיר פריטים בזמן ריצה בלבד. הנתונים לא נשמרים לאחר סגירת האפליקציה.

object ItemManager {

    val items: MutableList<Item> = mutableListOf()

    fun add(item: Item) {
        items.add(item)
    }

    fun remove(index: Int) {
        items.removeAt(index)
    }
}
