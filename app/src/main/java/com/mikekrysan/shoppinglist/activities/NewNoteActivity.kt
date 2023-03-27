package com.mikekrysan.shoppinglist.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.mikekrysan.shoppinglist.R
import com.mikekrysan.shoppinglist.databinding.ActivityNewNoteBinding
import com.mikekrysan.shoppinglist.entities.NoteItem
import com.mikekrysan.shoppinglist.fragments.NoteFragment
import java.text.SimpleDateFormat
import java.util.*

//11:
class NewNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        actionBarSettings()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.new_note_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    //Добавляем слушателя нажатий
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //Условие которое проверяе: мы нажали на homebutton или на кнопку save
        if(item.itemId == R.id.id_save) {
            setMainResult()
        } else if(item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    //12.6 Функция для результата, когда мы жмем на кнопку "save". В классе NoteFragment уже все подготовлено
    private fun setMainResult() {
        //создаем намерение:
        val i = Intent().apply {
            //указываем, что в нашем intent. Передаем значение,которое хотим передать. Берем из binding класса
//            putExtra(NoteFragment.TITLE_KEY, binding.edTitle.text.toString())
//            putExtra(NoteFragment.DESC_KEY, binding.edDescription.text.toString())
            //Как мы отправляем NoteItem в NoteFragment?:
//            putExtra(NoteFragment.NEW_NOTE_KEY, binding.edTitle.text.toString())
            //13.10 в putExtra мы создаем и заполяняем класс NoteItem() и его отправляем назад на наш NoteFragment (onEditResult() -> получаем с помощью getSerializableExtra)
            putExtra(NoteFragment.NEW_NOTE_KEY, createNewNote())

        }
        setResult(RESULT_OK, i)
        finish()
    }

    //13.9 Функция будет собирать все воедино и заполянть класс NoteItem. И этот класс NoteItem будем возвращать заполенным который мы и передадим на наш фрагмент, откуда мы его и запрашиваем
    private fun createNewNote(): NoteItem {
        return NoteItem(
            null,
            binding.edTitle.text.toString(),
            binding.edDescription.text.toString(),
            getCurrentTime(),
            ""
        )
    }


    //13.8 Создаем функцию которая будет брать настоящее время. Потому что мы хотим записывать время
    private fun getCurrentTime(): String {
        val formatter = SimpleDateFormat("hh:mm:ss - yyyy/MM/dd", Locale.getDefault())
        return formatter.format(Calendar.getInstance().time)
    }

    //активируем стрелку "назад"
    private fun actionBarSettings() {
        val ab = supportActionBar
        ab?.setDisplayHomeAsUpEnabled(true)
    }

}