package com.mikekrysan.shoppinglist.utils

import android.text.Html
import android.text.Spanned

object HtmlManager {

    //класс Spaned отвечает за передачу текста, стилей и цвета вместе
    //функция возьмет текст из БД который мы сохранили, выдаст класс Spanned который сможет прочитать наш уже editText
    fun getFromHtml(text: String): Spanned {
        //чтобы использовать функции класса Html нужно сделать проверку на версии андроид, так как не на всех версиях она будет работать одинаково
        return if(android.os.Build.VERSION.SDK_INT<=android.os.Build.VERSION_CODES.N) {
            Html.fromHtml(text)
        } else {
            Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT)
        }
    }

    //Когда мы сохраняем editText, нам нужно его сохранить в виде html, чтобы потом это все получить
    fun toHtml(text: Spanned): String {
        return if(android.os.Build.VERSION.SDK_INT<=android.os.Build.VERSION_CODES.N) {
            Html.toHtml(text)
        } else {
            Html.toHtml(text, Html.FROM_HTML_MODE_COMPACT)
        }
    }
}