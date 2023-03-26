package com.mikekrysan.shoppinglist.fragments

import androidx.fragment.app.Fragment

//7.1
abstract class BaseFragment : Fragment() {
    abstract fun onClickNew()
}