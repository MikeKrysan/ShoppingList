package com.mikekrysan.shoppinglist.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.fragment.app.activityViewModels
import com.mikekrysan.shoppinglist.activities.MainApp
import com.mikekrysan.shoppinglist.activities.NewNoteActivity
import com.mikekrysan.shoppinglist.databinding.FragmentNoteBinding
import com.mikekrysan.shoppinglist.db.MainViewModel

//9
class NoteFragment : BaseFragment() {

    private lateinit var binding: FragmentNoteBinding
    private lateinit var editLauncher: ActivityResultLauncher<Intent>   //12.1 Мы нажимаем на кнопку New в BottomNavView и мы должны получить результат, когда закрываем активити и переходим на фрагмент

    //10.5 Мы передаем наш класс, только инициализируем через класс MainViewModelFactory
    //12.7 В NoteFragment мы уже можем записать сохранненный заметку из NewNoteActivity, так как у нас есть здесь уже mainViewModel с помощью которого мы можем сделать insert, то-есть вставить это все в БД, в таблицу
    private val mainViewModel: MainViewModel by activityViewModels {
        //БД еще не создана, откуда брать БД? БД в классе MainApp инициализирована:
        MainViewModel.MainViewModelFactory((context?.applicationContext as MainApp).database)
    }

    //при нажатии на кнопку "Добавить новую заметку" сдесь будет запускатся логика для добавления новой заметки в базу данных
    override fun onClickNew() {
        editLauncher.launch(Intent(activity, NewNoteActivity::class.java))  //12.5
    }

    //10.6
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onEditResult()

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    //12.2 Инициализируем ActivityResultLauncher(editLauncher). В этой функции мы будем ждать результата который прийдет к нам с активити, который мы запустили
    private fun onEditResult() {
        //регистрируем:
        editLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            //Проверяем, что мы вернулись с результатом и этот результат можем обработать
            if(it.resultCode == Activity.RESULT_OK) {
                Log.d("MyLog", "title: ${it.data?.getStringExtra(TITLE_KEY)}")
                Log.d("MyLog", "description: ${it.data?.getStringExtra(DESC_KEY)}")
            }
        }
    }

    //companion object - он нужен для того, чтобы сделать singltone - одна инстанция фрагмента, если мы несколько раз пытаемся ее запустить
    companion object {
        const val TITLE_KEY = "title_key"   //12.3
        const val DESC_KEY = "description_key"  //12.4
        @JvmStatic
        fun newInstance() = NoteFragment()
    }
}