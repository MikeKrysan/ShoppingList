package com.mikekrysan.shoppinglist.fragments

import androidx.appcompat.app.AppCompatActivity
import com.mikekrysan.shoppinglist.R

//7.2
object FragmentManager {
    var currentFrag: BaseFragment? = null

    fun setFragment(newFrag: BaseFragment, activity: AppCompatActivity) {
        val transaction = activity.supportFragmentManager.beginTransaction()    //7.2.1 получаем специальный класс, с помощью которого можем удалять, заменять и т.д.
        transaction.replace(R.id.placeHolder, newFrag)  //7.2.2 в данном случае заменяем на месте placeHolder если там что-то есть на новый фрагмент
        transaction.commit()
        currentFrag = newFrag
    }
}