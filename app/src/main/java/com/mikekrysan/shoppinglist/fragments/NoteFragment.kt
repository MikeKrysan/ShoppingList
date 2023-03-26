package com.mikekrysan.shoppinglist.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.mikekrysan.shoppinglist.activities.MainApp
import com.mikekrysan.shoppinglist.activities.NewNoteActivity
import com.mikekrysan.shoppinglist.databinding.FragmentNoteBinding
import com.mikekrysan.shoppinglist.db.MainViewModel

//9
class NoteFragment : BaseFragment() {

    private lateinit var binding: FragmentNoteBinding
    //10.5 Мы передаем наш класс, только инициализируем через класс MainViewModelFactory
    private val mainViewModel: MainViewModel by activityViewModels {
        //БД еще не создана, откуда брать БД? БД в классе MainApp инициализирована:
        MainViewModel.MainViewModelFactory((context?.applicationContext as MainApp).database)
    }

    //при нажатии на кнопку "Добавить новую заметку" сдесь будет запускатся логика для добавления новой заметки в базу данных
    override fun onClickNew() {
        startActivity(Intent(activity, NewNoteActivity::class.java))
    }

    //10.6
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    //companion object - он нужен для того, чтобы сделать singltone - одна инстанция фрагмента, если мы несколько раз пытаемся ее запустить
    companion object {
        @JvmStatic
        fun newInstance() = NoteFragment()
    }
}