package com.mikekrysan.shoppinglist

/**
 * 1. Добавляем зависимости в build.gradle(Modules) и настраиваем build.gradle(Project)
 * 2.
 */

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}