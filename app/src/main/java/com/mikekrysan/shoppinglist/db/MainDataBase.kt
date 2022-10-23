package com.mikekrysan.shoppinglist.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mikekrysan.shoppinglist.entities.LibraryItem
import com.mikekrysan.shoppinglist.entities.NoteItem
import com.mikekrysan.shoppinglist.entities.ShoppingListItem
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

@Database (entities = [LibraryItem::class, NoteItem::class, ShoppingListItem::class], version = 1)  //***
abstract class MainDataBase : RoomDatabase() {  //4.1
    abstract fun getDao(): Dao  //5.2

    companion object {  //4.1 companion object дает возможность все что мы напишем внутри (специальную функцию) использовать без инициализации класса
        @Volatile   //поле, которому мы присваиваем аннотацию @Volatile будет инстанцией класса:
        private var INSTANCE: MainDataBase? = null  //*
        @OptIn(InternalCoroutinesApi::class)
        fun getDataBase(context: Context): MainDataBase {   //функция будет возвращать класс MainDataBase
            return INSTANCE ?: synchronized(this) { //Оператор Элвис говорит: если слева null, то выдать что находится справа, если не null, то выдаст то что справа находится / **
                val instance = Room.databaseBuilder(
                    context.applicationContext,     //будем использовать инстанцию базы данных в любом активити
                    MainDataBase::class.java,
                    "shopping_list.db"  //дали название базе данных
                ).build()
                instance
            }
        }
    }
}

//*Все что мы будем записывать в переменную INSTANCE (инициализированный класс MainDataBase) мгновенно стает доступным для остальных потоков
//** synchronized() используется для того, чтобы обеспечить выполнение кода, если несколько потоков пытаются его запустить одновременно один раз
//*** version = 1 : Когда работа над приложением будет закончена, и если необходимо будет добавить к какой-нибудь таблице еще несколько столбцов, то чтобы не удалились все данные пользователя когда
// мы будем заменять базу данных, нужно делать миграцию. Тогда данные мигрируют с одной таблицы в другую, не потеряв то что было