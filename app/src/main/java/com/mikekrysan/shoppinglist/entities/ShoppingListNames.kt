package com.mikekrysan.shoppinglist.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

//Модель, которая отвечает за список покупок (например, воскресные покупки, рождественские покупки):

@Entity (tableName = "shopping_list_names")
data class ShoppingListNames(   //2
    @PrimaryKey (autoGenerate = true)
    val id: Int?,

    @ColumnInfo (name = "name")
    val name: String,

    @ColumnInfo (name = "time")
    val time: String,

    @ColumnInfo (name = "allItemCounter")   //Сколько уже выбранных элементов есть на данный момент в этом списке
    val allItemCounter: Int,

    @ColumnInfo (name = "checkedItemsCounter")  //отмеченные элементы, которые пользователь купил
    val checkedItemCounter: Int,

    @ColumnInfo (name = "itemsIds") //записиваются все идентификаторы всех элементов списка, чтобы мы после могли их получить и работать с ними **
    val itemsIds: String,
): Serializable  //2.1*

//* Этот класс будем передавать из одного активити в другое активити с помощью intent, и чтобы мы могли передать весь класс, его нужно сделать serializable, иначе пришлось бы передавать переменные по отдельности
//** Две разные таблици связаны между собой с помощью того, что каждый элемент из первой таблицы будет в себе хранить идентификатор элементов из второй таблицы