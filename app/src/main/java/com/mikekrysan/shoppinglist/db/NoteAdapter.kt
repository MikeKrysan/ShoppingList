package com.mikekrysan.shoppinglist.db

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

import androidx.recyclerview.widget.RecyclerView
import com.mikekrysan.shoppinglist.R
import com.mikekrysan.shoppinglist.databinding.NoteListItemBinding
import com.mikekrysan.shoppinglist.entities.NoteItem
import com.mikekrysan.shoppinglist.utils.HtmlManager

//8
//В треугольных скобках указываем, что за элемент будет в списке- мы будем показывать в одном элементе одну заметку:
//DiffUtilClass отвечает за то, чтобы сравнивать элементы между собой старого списка и нового и за нас будет делать работу: что обновлять, как обновлять, какую функцию запустить чтобы сделать обновление
class NoteAdapter(private val listener: Listener) : ListAdapter<NoteItem, NoteAdapter.ItemHolder>(ItemComparator()) {

    //8.4 здесь создаем наш холдер. Функция будет создавать для каждой заметки свой собственный ItemHolder, который в себе будет создавать эту разметку для каждого элемента
    //Можно и сразу создать здесь функцию create(), но лучше разделять логику. В функции onCreateViewHolder нужно создать и вернуть ViewHolder, поэтому мы делаем функцию create() которая его создаст
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder.create(parent)
    }

    //8.5 сразу после того как создается разметка, она сразу заполняется
    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        //у holder есть функция setData. Заметки из базы данных ListAdapter берем из скрытого массива (в RecyclerViewAdapter мы можем сами создать массив который берем из базы данных и оттуда можем взять заметку)
        holder.setData(getItem(position), listener)     //14.6
    }

    //8.1 Создаем класс, который будет создавать и содержать разметку, которую мы создали в note_list_item.xml. Нужно создать собственный класс наследуясь от ViewHolder
    class ItemHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = NoteListItemBinding.bind(view)    //подключаем view к нашему классу NoteListItemBinding
        //8.2 В эту функцию нужно будет передавать по-очереди NoteItem и с помощью этих NoteItem мы и будем заполнять
        //14.4 Добавляем слушателя listener: Listener
        fun setData(note: NoteItem, listener: Listener) = with(binding) {
            tvTitle.text = note.title
//            tvDescription.text = note.content
            tvDescription.text = HtmlManager.getFromHtml(note.content).trim()  //17.5   trim() - убирает пробелы, которые появляются внизу
            tvTime.text = note.time
            //15.2:
            itemView.setOnClickListener{
                listener.onClidkItem(note)
            }
            //14.5:
            imDelete.setOnClickListener{
                listener.deleteItem(note.id!!)
            }
        }
        //статическая функция, с помощью которой мы инициализируем наш класс
        companion object {
            //Чтобы использовать LayoutInflater для того чтобы надуть разметку, загрузить ее в память и получить ссылку на эту разметку нужно будет использовать контекст, потому что LayoutInflater есть только у активити. А это у нас не активити. Откуда можно взять контекст?:из ViewGroup
            //функция create выдает уже инициализированный класс ItemHolder который в себе хранит ссылку на загруженной в память разметку
            fun create(parent: ViewGroup): ItemHolder{
                //надуваем разметку с помощью LayoutInflater
                return ItemHolder(LayoutInflater.from(parent.context).inflate(R.layout.note_list_item, parent, false))
            }
        }
    }

    //8.3 В адаптере будут идти NoteItem, поэтому в скобках указываем NoteItem
    class ItemComparator : DiffUtil.ItemCallback<NoteItem>() {
        // функция сравинвает, оба элемента равны
        override fun areItemsTheSame(oldItem: NoteItem, newItem: NoteItem): Boolean {
            //будем сравнивать по уникальному идентификатору
            return oldItem.id == newItem.id
        }

        //функция сравнивает весь контент внутри элемента
        override fun areContentsTheSame(oldItem: NoteItem, newItem: NoteItem): Boolean {
            return oldItem == newItem
        }

    }

    //14.1
    interface Listener {
        fun deleteItem(id: Int)
        fun onClidkItem(note: NoteItem)   //15.1
    }

}