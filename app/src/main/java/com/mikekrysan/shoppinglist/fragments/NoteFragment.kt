package com.mikekrysan.shoppinglist.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mikekrysan.shoppinglist.databinding.FragmentNoteBinding

//9
class NoteFragment : BaseFragment() {
    private lateinit var binding: FragmentNoteBinding

    //при нажатии на кнопку "Добавить новую заметку" сдесь будет запускатся логика для добавления новой заметки в базу данных
    override fun onClickNew() {

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