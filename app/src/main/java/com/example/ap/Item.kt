package com.example.ap

import android.net.Uri
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class Item(
    val foodName: String,
    val authorName: String,
    val description: String,
    val ingredients: String,
    val imageUri: Uri? = null
) : Parcelable


// ItemManager: מנהל רשימה זמנית של פריטים בזיכרון האפליקציה.
// מאפשר להוסיף ולהסיר פריטים בזמן ריצה בלבד. הנתונים לא נשמרים לאחר סגירת האפליקציה.

object ItemManager {

    val items: MutableList<Item> = mutableListOf()

    fun add(item: Item) {
        items.add(item)
    }

    fun remove(item: Item) {
        items.remove(item)
    }
}
