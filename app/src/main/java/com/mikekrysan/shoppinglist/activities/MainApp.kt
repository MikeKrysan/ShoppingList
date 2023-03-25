package com.mikekrysan.shoppinglist.activities

import android.app.Application
import com.mikekrysan.shoppinglist.db.MainDataBase

class MainApp: Application() {
    val database by lazy {MainDataBase.getDataBase(this)}
}