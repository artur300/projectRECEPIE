package com.example.ap.data.local_dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ap.data.model.Item

@Database(entities = arrayOf(Item::class), version = 1, exportSchema = false)
abstract class itemDataBase : RoomDatabase() {

    abstract  fun ItemsDao():itemsDao

    companion object {
        @Volatile
        private var instance: itemDataBase? = null

        fun getDatabase(context: Context): itemDataBase? {
            if (instance == null) {
                synchronized(itemDataBase::class.java) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        itemDataBase::class.java,
                        "items_database"
                    ).build()
                }
            }
            return instance
        }
    }


}