package com.mikekrysan.shoppinglist.activities

/**
 * 1. Добавляем зависимости в build.gradle(Modules) и настраиваем build.gradle(Project)
 * 2. Entities #1. В roomPersistensLibrary. Создаем папку entities,  в ней dataClass ShoppinListNames
 * 3. Entities #2. Создаем ShoppingListItem и блокнот с заметками NoteItem(), а также библиотеку LibraryItem
 * 4. MainDatabase class - самый основной класс. Будет либо создавать базу данных, либо давать доступ к базе данных
 *      Для инициализации и получения доступа к базе данных создаем класс activities->MainApp
 *      В манифесте добавляем строку android:name=".activities.MainApp" для того чтобы AS знала, что когда запускается приложение, запускается класс MainApp, который унаследован от общего
 *      класса Application()
 */

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mikekrysan.shoppinglist.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}