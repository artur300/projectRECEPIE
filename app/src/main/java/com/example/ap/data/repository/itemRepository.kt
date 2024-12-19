package com.example.ap.data.repository

import android.app.Application
import com.example.ap.data.local_dataBase.itemDataBase
import com.example.ap.data.local_dataBase.itemsDao
import com.example.ap.data.model.Item

class ItemRepository(application: Application) {

    private var itemDao: itemsDao?

    init {
        val db = itemDataBase.getDatabase(application.applicationContext)
        itemDao = db?.ItemsDao()
    }

    fun getItems() = itemDao?.getItems()

    fun addItem(item: Item) {
        itemDao?.addItem(item)
    }

    fun deleteItem(item: Item) {
        itemDao?.deleteItem(item)
    }

    fun getItem(id: Int) = itemDao?.getItem(id)
}
