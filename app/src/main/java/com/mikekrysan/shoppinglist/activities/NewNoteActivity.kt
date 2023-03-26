package com.mikekrysan.shoppinglist.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.mikekrysan.shoppinglist.R
import com.mikekrysan.shoppinglist.databinding.ActivityNewNoteBinding

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
            finish()
        } else if(item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    //активируем стрелку "назад"
    private fun actionBarSettings() {
        val ab = supportActionBar
        ab?.setDisplayHomeAsUpEnabled(true)
    }

}