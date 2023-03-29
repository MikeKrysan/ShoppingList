package com.mikekrysan.shoppinglist.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.mikekrysan.shoppinglist.R
import com.mikekrysan.shoppinglist.databinding.ActivityMainBinding
import com.mikekrysan.shoppinglist.fragments.FragmentManager
import com.mikekrysan.shoppinglist.fragments.NoteFragment

/*
Урок1 - создание зависимостей. Room persistance library
Урок2 - Entities
Урок3 - Entities 2
Урок4 - MainDataBase class
Урок5 - Создаем MainApplication класс
Урок6 - Создаем нижнее меню BottomNavigationView
Урок7 - Создаем FragmentManager класс и BaseFragment
Урок8 - Создаем адаптер для RecyclerView
Урок9 - Начинаем создавать NoteFragment
Урок10 -  Создаем ViewModel класс. Архитектура MVVM
Урок11 - NewNoteActivity
Урок12 - Cоздаем ActivityResultLauncher для NoteFragment
Урок13 - Делаем сохранение заметок
    Пришло время проверить как записывается и считывается информация в БД. Для этого нам нужно сделать следующее:
    13.1 В NoteFragment добавляем обсервер который следит за изменениями и обновляет наш адаптер
    13.2 Нам нужно добавить recyclerView на нашу разметку. RecyclerView - это такой view, в котором мы будем показывать элементы из списка
    13.3 Нужно инициализировать адаптер

Урок 14. Делаем удаление заметок
    Создали кнопку удаления в note_list_fragment.xml . Поскольку мы работаем через архитектуру MVVM, все и удаление в т.ч. будем делать через MainViewModel
    viewModel class находится во фрагменте, адаптер же  - отдельный класс, поэтому нам нужно будет добавить в адаптер интерфейс, с помощью которого если мы жмем в адаптере на элемент,
    то у нас срабатывает в NoteFragment этот интерфейс-функция. И в этой функции мы уже запускаем функцию для удаления.
    Т.о я жму на элемент в адаптере, но команда дублируется и срабатывает в NoteFragment и в этом фрагменте в функции deleteItem() мы уже запустим функцию для удаления
    Для этого мы создали интерфейс, чтобы перенести действие удаления на фрагмент, чтобы там все срабатывало
    14.1 Создали interface Listener в NoteAdapter
    14.2 Добавили классу NoteFragment наследование от NoteAdapter.Listener
    14.3 Создали функцию deleteItem в NoteFragment (необходимо реализовать все функции наследованного интерфейса)
    14.4 Добавляем слушателя listener: Listener в setData<-ItemHolder<-NoteAdapter
    14.5 NoteAdapter->ItemHolder->setData: imDelete.setOnClickListener { listener.deleteItem(note.id!!) }
    14.6 NoteAdapter -> onBindViewHolder:  - добавляем в принимаемые параметры функции listener
    14.7 MainViewModel: создаем функцию deleteNote() в которой из интервейса Dao удаляем заметку по id: dao.deleteNote(id)
    14.8 Чтобы добраться до Dao, в интерфейсе Dao пишем:    @Query ("DELETE FROM note_list WHERE id IS :id")
                                                            suspend fun deleteNote(id: Int)
    14.9 NoteFragment -> deleteItem: mainViewModel.deleteNote(id).

Урок15. Делаем редактирование заметок
    Нужно сделать слушателя нажатий на весь элемент NoteFragment с помощью интерфейса.
    15.1 В NoteAdapter в interface Listener добавляем еще одну функцию: onClidkItem
    15.2 Чтобы добавить слушателя на весь элемент, нужно добавить itemView.setOnClickListener в setData->ItemHolder->NoteAdapter
    15.3 В NoteFragment имплементируем еще одну функцию
    15.4 В NewNoteActivity нужно сделать несколько проверок. Если мы получим нашу заметку, она будет не null, это значит что мы зашли на это активити для редактирования
        Если в intent мы попытаемся взять эту заметку и будет null - это значит, что мы ничего не передали, значит мы открыли активити для создания новой заметки (мы нажали onClickNew в NoteFragment)
        15.4.1 Создаем переменную note в NewNoteItem
        //15.4.2 Инициализируем редактируемую заметку с помощью функции getNote
        //15.4.3 Функция для проверки, новая заметка или редактироваие созданной
    15.5 Пытаемся сделать проверку функции fillNote в getNote   -> NewNoteActivity
    15.6 Пытаемся сделать проверку функции getNote в onCreate-> NewNoteActivity
    15.7 В Dao создаем возможность update заметки
    15.8 Во MainViewModel создаем функцию funUpdate
    15.9 в NewNoteActivity создаем функцию updateNote
    15.10 Делаем проверку в setMainResult>NewNoteActivity, так как мы не создаем новую заметку, а отправляем копию старой
    15.11 Когда мы вернемся на NoteFragment в функции onEditResult нужно будет определить, какие данные мы возвращаем для того чтобы сделать insert либо update. Для этого мы должны передать данные
        putExtra в setMainResult -> NewNoteActivity
    15.12 В NoteFragment создадим константу EDIT_STATE_KEY
    15.13 Возвращаемся на onEditResult->NoteFragment и делаем проверку
    15.14 В getNote->NewNoteActivity Когда мы заходим для создания новой заметки мы не можем превратить null в  сериализейбл (в NoteItem).
        Поэтому прежде чем превращать в NoteItem сделаем следующую проверку
 */

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setBottomNavListener()
    }

    //функция добавления слушателя на нижнее меню
    private fun setBottomNavListener() {
        binding.bNav.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.settings -> {
                    Log.d("MyLog", "Settings")
                }
                R.id.notes -> {
                   FragmentManager.setFragment(NoteFragment.newInstance(), this)    //9.1
                }
                R.id.shop_list -> {
                    Log.d("MyLog", "List")
                }
                R.id.new_item -> {
                    FragmentManager.currentFrag?.onClickNew()   //11.1
                }
            }
            true
        }
    }
}

/*
Архитектура MVVM - не будет прямого доступа из veiw-элементов к бизнес логике. Мы хотим добавить посредника.
 */