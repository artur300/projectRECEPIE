package com.example.ap.data.model

import android.net.Uri
import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Parcelize
@Entity(tableName = "itemsRecipe")
data class Item(
    @ColumnInfo(name = "Recipe_name")
    val foodName: String,
    @ColumnInfo(name = "Recipe_authorName")
    val authorName: String,
    @ColumnInfo(name = "Recipe_description")
    val description: String,
    @ColumnInfo(name = "Recipe_ingredients")
    val ingredients: String,
    @ColumnInfo(name = "Recipe_image")
    val imageUri: String? = null // שונה ל-String
) : Parcelable {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
