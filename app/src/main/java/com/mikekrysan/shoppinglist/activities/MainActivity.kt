package com.mikekrysan.shoppinglist.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.mikekrysan.shoppinglist.R
import com.mikekrysan.shoppinglist.databinding.ActivityMainBinding

/*
Урок1 - создание зависимостей. Room persistance library
Урок2 - Entities
Урок3 - Entities 2
Урок4 - MainDataBase class
Урок5 - Создаем MainApplication класс
Урок6 - Создаем нижнее меню BottomNavigationView
Урок7 - Создаем FragmentManager класс и BaseFragment
 */

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setBottomNavListener()
    }

    //функция добавления слушателя на нижнее меню
    private fun setBottomNavListener() {
        binding.bNav.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.settings -> {
                    Log.d("MyLog", "Settings")
                }
                R.id.notes -> {
                    Log.d("MyLog", "Notes")
                }
                R.id.shop_list -> {
                    Log.d("MyLog", "List")
                }
                R.id.new_item -> {
                    Log.d("MyLog", "New")
                }
            }
            true
        }
    }
}