package com.mikekrysan.shoppinglist

/**
 * 1. Добавляем зависимости в build.gradle(Modules) и настраиваем build.gradle(Project)
 * 2. Entities #1. В roomPersistensLibrary. Создаем папку entities,  в ней dataClass ShoppinListNames
 * 3 Entities #2. Создаем ShoppingListItem и блокнот с заметками NoteItem(), а также библиотеку LibraryItem
 */

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}