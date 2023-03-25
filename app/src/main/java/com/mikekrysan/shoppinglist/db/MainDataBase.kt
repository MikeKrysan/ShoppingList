package com.mikekrysan.shoppinglist.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mikekrysan.shoppinglist.entities.LibraryItem
import com.mikekrysan.shoppinglist.entities.NoteItem
import com.mikekrysan.shoppinglist.entities.ShoppingListItem
import com.mikekrysan.shoppinglist.entities.ShoppingListNames

@Database (entities = [LibraryItem::class, NoteItem::class, ShoppingListItem::class, ShoppingListNames::class], version = 1)
abstract class MainDataBase : RoomDatabase() {
    abstract fun getDao(): Dao

    companion object {
        @Volatile
        private var INSTANCE: MainDataBase? = null
        fun getDataBase(context: Context): MainDataBase {
            //synchronized - обеспечивает выполнение кода если несколько потоков пытаются его запустить одновременно один раз
            return INSTANCE ?: synchronized(this) {
                //запускается создание базы данных
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MainDataBase::class.java,
                    "shopping_list.db"
                ).build()
                instance
            }
        }
    }
}