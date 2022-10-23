package com.mikekrysan.shoppinglist.db

import androidx.room.Insert
import androidx.room.Query
import com.mikekrysan.shoppinglist.entities.NoteItem
import kotlinx.coroutines.flow.Flow

@androidx.room.Dao
interface Dao { //5.1
    @Query("SELECT * FROM note_list")
    fun getAllNotes(): Flow<List<NoteItem>> //С помощью этой функции мы будем получать все заметки без фильтрации / *
    @Insert
    suspend fun insertNote(note: NoteItem)  //функция для записи заметки
}

// * - функция возвращает специальный класс в корутинах Flow - специальный класс, который будет подключать базу данных к нашему списку, и автоматически все обновлять. Запускаем один раз
// функцию - подключается Flow, который начинает постоянно выдавать список, если есть изменения в базе данных