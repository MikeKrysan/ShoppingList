package com.mikekrysan.shoppinglist.activities

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.style.StyleSpan
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
    private var note: NoteItem? = null   //15.4.1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        actionBarSettings()
        getNote()   //15.6
    }

    //15.4.2
    private fun getNote(){
        //15.14:
        val sNote = intent.getSerializableExtra(NoteFragment.NEW_NOTE_KEY)  //Получаем как сериализайбл и проверяем
        if(sNote != null) {

            note = sNote as NoteItem   //Когда мы заходим для создания новой заметки мы не можем превратить null в  сериализейбл (в NoteItem)
            fillNote()  //15.5 пытаемся сделать проверку
        }

    }

    //15.4.3 Эта функция у нас запустится только в том случае, если note не равен null
    private fun fillNote() = with(binding) {
        edTitle.setText(note?.title)
        edDescription.setText(note?.content)
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
        //16.2
        else if(item.itemId == R.id.id_bold) {
            setBoldForSelectedText()    //16.3
        }
        return super.onOptionsItemSelected(item)
    }

    //16.3
    private fun setBoldForSelectedText() = with(binding) {
        //Когда мы выбираем, нам нужно знать, откуда мы выбрали текст и докуда, чтобы можно его выделить. Для этого создаем переменные которые будут хранить в себе позицию:
        val startPos =  edDescription.selectionStart
        val endPos =  edDescription.selectionEnd
        //Сначала нужно проверить, есть ли уже у выбранного отрезка текста уже какой-нибудь стиль:
        val styles = edDescription.text.getSpans(startPos, endPos, StyleSpan::class.java)
        var boldStyle: StyleSpan? = null
        if(styles.isNotEmpty()) {
            edDescription.text.removeSpan(styles[0])
        }   else {
            boldStyle = StyleSpan(Typeface.BOLD)
        }
        edDescription.text.setSpan(boldStyle, startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE) //SPAN_EXCLUSIVE_EXCLUSIVE - тип добавления
        ////чтобы избежать добавления пробелов между словами в редактируемом тексте:
        edDescription.text.trim()
        //Делаем так, чтобы курсор был вначале слова которое мы выбрали:
        edDescription.setSelection(startPos)
    }

    //12.6 Функция для результата, когда мы жмем на кнопку "save". В классе NoteFragment уже все подготовлено
    private fun setMainResult() {
        var editState = "new"    //15.11
        //15.10:
        val tempNote:NoteItem?
        if(note == null) {
            tempNote = createNewNote()
        } else {
            editState = "update"    //15.11.1
            tempNote = updateNote()
        }
        //создаем намерение:
        val i = Intent().apply {
            //указываем, что в нашем intent. Передаем значение,которое хотим передать. Берем из binding класса
//            putExtra(NoteFragment.TITLE_KEY, binding.edTitle.text.toString())
//            putExtra(NoteFragment.DESC_KEY, binding.edDescription.text.toString())
            //Как мы отправляем NoteItem в NoteFragment?:
//            putExtra(NoteFragment.NEW_NOTE_KEY, binding.edTitle.text.toString())
            //13.10 в putExtra мы создаем и заполяняем класс NoteItem() и его отправляем назад на наш NoteFragment (onEditResult() -> получаем с помощью getSerializableExtra)
            putExtra(NoteFragment.NEW_NOTE_KEY, tempNote)
            putExtra(NoteFragment.EDIT_STATE_KEY, editState)   //15.11.2

        }
        setResult(RESULT_OK, i)
        finish()
    }

    //15.9:
    private fun updateNote() : NoteItem? = with(binding) {
        //Мы не создаем новую заметку, а остается все что было; делаем копию.
        return note?.copy(title = edTitle.text.toString(),
                    content = edDescription.text.toString())
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