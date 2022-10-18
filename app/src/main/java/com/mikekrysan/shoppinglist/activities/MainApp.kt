package com.mikekrysan.shoppinglist.activities

import android.app.Application
import com.mikekrysan.shoppinglist.db.MainDataBase

class MainApp: Application() {  //4.2
    val database by lazy{MainDataBase.getDataBase(this)}    //*
}

// * - когда database пуст, блок запустится один раз, выдаст инстанцию базы данных, потому как getDataBase возвращает инициализированную базу данных MainDataBase; если в следующий раз
// если мы еще раз запускаем этот код по какой-нибудь причине, то этот блок еще раз не запускается, потому что переменная database уже содержит в себе инстанцию базы данных