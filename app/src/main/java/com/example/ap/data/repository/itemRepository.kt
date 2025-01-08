@file:Suppress("SpellCheckingInspection") // ביטול בדיקת שגיאות כתיב בקובץ
package com.example.ap.data.repository

import android.app.Application
import com.example.ap.data.local_dataBase.itemDataBase
import com.example.ap.data.local_dataBase.itemsDao
import com.example.ap.data.model.Item
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ItemRepository(application: Application) : CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    private var itemDao: itemsDao?

    init {
        val db = itemDataBase.getDatabase(application.applicationContext)
        itemDao = db?.ItemsDao()
    }

    fun getItems() = itemDao?.getItems()
    fun getItem(id: Int) = itemDao?.getItem(id)

    fun addItem(item: Item) {
        launch { itemDao?.addItem(item) }
    }

    fun deleteItem(item: Item) {
        launch { itemDao?.deleteItem(item) }
    }

}
