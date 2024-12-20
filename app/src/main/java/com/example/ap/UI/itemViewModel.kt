package com.example.ap.UI

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.ap.data.model.Item
import com.example.ap.data.repository.ItemRepository

//---------------temp
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
//--------------temp


class itemViewModel(application: Application): AndroidViewModel(application) {

    private  val repository=ItemRepository(application)

    val items : LiveData<List<Item>>?=repository.getItems()

    fun addItem(item: Item) {
        //---temp
        viewModelScope.launch(Dispatchers.IO)
        //--------

        {
            repository.addItem(item)
        }
    }


    fun deleteItem(item: Item){
        repository.deleteItem(item)
    }


}