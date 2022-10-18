package com.mikekrysan.shoppinglist.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Library")
data class LibraryItem( //3.3
    @PrimaryKey (autoGenerate = true)
    val id: Int?,
    @ColumnInfo (name = "name")
    val name: String
)
