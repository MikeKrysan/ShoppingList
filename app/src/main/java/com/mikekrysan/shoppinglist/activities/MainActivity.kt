package com.mikekrysan.shoppinglist.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.mikekrysan.shoppinglist.R
import com.mikekrysan.shoppinglist.databinding.ActivityMainBinding
import com.mikekrysan.shoppinglist.fragments.FragmentManager
import com.mikekrysan.shoppinglist.fragments.NoteFragment

/*
Урок1 - создание зависимостей. Room persistance library
Урок2 - Entities
Урок3 - Entities 2
Урок4 - MainDataBase class
Урок5 - Создаем MainApplication класс
Урок6 - Создаем нижнее меню BottomNavigationView
Урок7 - Создаем FragmentManager класс и BaseFragment
Урок8 - Создаем адаптер для RecyclerView
Урок9 - Начинаем создавать NoteFragment
Урок10 -  Создаем ViewModel класс. Архитектура MVVM
Урок11 - NewNoteActivity
Урок12 - Cоздаем ActivityResultLauncher для NoteFragment
Урок13 - Делаем сохранение заметок
    Пришло время проверить как записывается и считывается информация в БД. Для этого нам нужно сделать следующее:
    13.1 В NoteFragment добавляем обсервер который следит за изменениями и обновляет наш адаптер
    13.2 Нам нужно добавить recyclerView на нашу разметку. RecyclerView - это такой view, в котором мы будем показывать элементы из списка
    13.3 Нужно инициализировать адаптер
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
                   FragmentManager.setFragment(NoteFragment.newInstance(), this)    //9.1
                }
                R.id.shop_list -> {
                    Log.d("MyLog", "List")
                }
                R.id.new_item -> {
                    FragmentManager.currentFrag?.onClickNew()   //11.1
                }
            }
            true
        }
    }
}

/*
Архитектура MVVM - не будет прямого доступа из veiw-элементов к бизнес логике. Мы хотим добавить посредника.
 */