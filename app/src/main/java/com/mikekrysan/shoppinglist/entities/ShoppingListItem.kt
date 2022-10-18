package com.mikekrysan.shoppinglist.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shop_list_item")
data class ShoppingListItem(    //3.1
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "itemInfo") //добавление любой информации (вес, цвет и т.д.)
    val itemInfo:String?,
    @ColumnInfo(name = "itemChecked")  //продукт куплен
    val itemChecked: Int = 0,   //изначально продукт не куплен, когда будет куплен, запишем 1
    @ColumnInfo(name = "listId")  //идентификатор продукта
    val listId: Int,
    @ColumnInfo(name = "itemType")        //библиотека, которая будет использовать уже написанные ранне продукты пользователем
    val itemType: String = "item"
)
