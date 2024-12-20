package com.example.ap.UI

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.ap.data.model.Item
import com.example.ap.data.repository.ItemRepository

class itemViewModel(application: Application): AndroidViewModel(application) {

    private  val repository=ItemRepository(application)

    val items : LiveData<List<Item>>?=repository.getItems()

    fun addItem(item: Item){
        repository.addItem(item)
    }

    fun deleteItem(item: Item){
        repository.deleteItem(item)
    }


}