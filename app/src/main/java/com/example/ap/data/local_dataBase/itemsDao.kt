@file:Suppress("SpellCheckingInspection")

package com.example.ap.data.local_dataBase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.ap.data.model.Item


@Dao
interface itemsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addItem(item: Item)

    @Delete
    fun deleteItem(vararg item: Item)

    @Update
    fun updateItem(item: Item)

    @Query("SELECT * FROM itemsRecipe ORDER BY Recipe_name")
    fun getItems(): LiveData<List<Item>>

    @Query(value = "SELECT * FROM itemsRecipe WHERE id = :id")
    fun getItem(id: Int): Item
}

