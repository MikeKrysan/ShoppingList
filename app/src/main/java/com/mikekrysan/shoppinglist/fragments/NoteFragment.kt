package com.mikekrysan.shoppinglist.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mikekrysan.shoppinglist.activities.MainApp
import com.mikekrysan.shoppinglist.activities.NewNoteActivity
import com.mikekrysan.shoppinglist.databinding.FragmentNoteBinding
import com.mikekrysan.shoppinglist.db.MainViewModel
import com.mikekrysan.shoppinglist.db.NoteAdapter
import com.mikekrysan.shoppinglist.entities.NoteItem

//9
class NoteFragment : BaseFragment(), NoteAdapter.Listener  { //14.2

    private lateinit var binding: FragmentNoteBinding
    private lateinit var editLauncher: ActivityResultLauncher<Intent>   //12.1 Мы нажимаем на кнопку New в BottomNavView и мы должны получить результат, когда закрываем активити и переходим на фрагмент
    private lateinit var adapter: NoteAdapter//13.1 Создадим переменную, в которую будет записывать наш адаптер, когда мы будем его инициализировать

    //10.5 Мы передаем наш класс, только инициализируем через класс MainViewModelFactory
    //12.7 В NoteFragment мы уже можем записать сохранненный заметку из NewNoteActivity, так как у нас есть здесь уже mainViewModel с помощью которого мы можем сделать insert, то-есть вставить это все в БД, в таблицу
    private val mainViewModel: MainViewModel by activityViewModels {
        //БД еще не создана, откуда брать БД? БД в классе MainApp инициализирована:
        MainViewModel.MainViewModelFactory((context?.applicationContext as MainApp).database)
    }

    //при нажатии на кнопку "Добавить новую заметку" сдесь будет запускатся логика для добавления новой заметки в базу данных
    override fun onClickNew() {
        editLauncher.launch(Intent(activity, NewNoteActivity::class.java))  //12.5
    }

    //10.6
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onEditResult()

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    //13.3 Запускаем функцию onViewCreated(). Она запускается, когда все view уже созданы
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcView()    //13.2.1 теперь у нас список инициализирован, адаптер инициализирован. Мы теперь можем передавать список когда мы его получим из БД в наш адаптер. И адаптер будет обновлять recyclerView, потому что все подключено
        observer()  //13.5
    }


    //13.2 Сдесь будем инициализировать наш RecyclerView и также инициализиуем здесь наш адаптер
    private fun initRcView() = with(binding){
        rcViewNote.layoutManager = LinearLayoutManager(activity) //layoutManager - у нас списки в RecyclerView элементы могут быть по-горизонтали, по-вертикали, подряд. Поэтому мы должны указать, как мы хотим, чтобы ишел наш список
        adapter = NoteAdapter(this@NoteFragment)    //Нужно передать адаптер, но для начала нужно его инициализировать   //14.5
        rcViewNote.adapter = adapter    //передаем адаптер в rcViewNote. Адаптер, который будет обновлять recyclerView - это adapter
    }


    //13.4 Функция-обсервер, которая следит за изменениями в БД
    private fun observer() {
        //обсервер будет следить за изменениями в БД и каждый раз будет выдавать измененный список.
        mainViewModel.allNotes.observe(viewLifecycleOwner, {
            //С помощью него будем обновлять адаптер
            adapter.submitList(it)
        })
    }


    //12.2 Инициализируем ActivityResultLauncher(editLauncher). В этой функции мы будем ждать результата который прийдет к нам с активити, который мы запустили
    private fun onEditResult() {
        //регистрируем:
        editLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            //Проверяем, что мы вернулись с результатом и этот результат можем обработать
            if(it.resultCode == Activity.RESULT_OK) {
//                Log.d("MyLog", "title: ${it.data?.getStringExtra(TITLE_KEY)}")
                //13.6 Когда мы вернемся с результатом, то мы берем наш mainViewModel и записываем туда нашу заметку. Заметку получим строкой выше, отправив сразу целый класс NoteItem заполненный *
                //15.13:
                val editState = it.data?.getStringExtra(EDIT_STATE_KEY) //берем константу
                if(editState == "update") {
                    mainViewModel.updateNote(it.data?.getSerializableExtra(NEW_NOTE_KEY) as NoteItem)
                } else {
                    mainViewModel.insertNote(it.data?.getSerializableExtra(NEW_NOTE_KEY) as NoteItem)   //получаем
                }
//                Log.d("MyLog", "description: ${it.data?.getStringExtra(DESC_KEY)}")
            }
        }
    }

    //14.3
    override fun deleteItem(id: Int) {
        //14.9 Когда нажмем на элемент из нашего адаптера из списка запустится интерфейс, то-есть эта функция и нам выдаст идентификатор. Поэтому теперь с помощью mainViewModel можно сделать delete по идентификатору
        mainViewModel.deleteNote(id)
    }

    //15.3
    override fun onClidkItem(note: NoteItem) {
        //В этот раз не просто запускаем NewNoteActivity а еще передаем заметку, чтобы после там могли заполнить:
        val intent = Intent(activity, NewNoteActivity::class.java).apply {
            putExtra(NEW_NOTE_KEY, note)
        }
        editLauncher.launch(intent)
    }

    //companion object - он нужен для того, чтобы сделать singltone - одна инстанция фрагмента, если мы несколько раз пытаемся ее запустить
    companion object {
        const val NEW_NOTE_KEY = "new_note_key"   //13.5
        const val EDIT_STATE_KEY = "edit_state_key"   //15.12
        //        const val TITLE_KEY = "title_key"   //12.3
//        const val DESC_KEY = "description_key"  //12.4
        @JvmStatic
        fun newInstance() = NoteFragment()
    }

}

/*
* Но мы не можем целый класс отправить, потому как через intent можно передавать либо string, либо int, boolean. Чтобы отправить целый класс, нужно его сделать serializable, то-есть отправить по байтам
 */
