package com.mikekrysan.shoppinglist.db

import androidx.lifecycle.*
import com.mikekrysan.shoppinglist.entities.NoteItem
import kotlinx.coroutines.launch

//10. Когда создаем класс MainViewModel он будет передавать базу данных. С помощью этой бд мы будем получать интерфейс DAO, с помощью которого мы будем записывать и считывать
//Это и есть наш посредник, здесь мы будем записывать и считывать данные
class MainViewModel(database: MainDataBase) : ViewModel() {
    //10.1 создаем интерфейс
    val dao = database.getDao()
    //10.2 как мы будем считывать все наши заметки из бд чтобы поазать их в списке?
    //Для этого создаем одну переменную, LiveData - хранит всякие данные, а в нашем случае это будет список. Если он обновляется, то обновляется и там где мы подключили обсервер. Мы его еще не подключили.
    //Когда список NoteItem будет менятся, LiveData будет обновлятся. Обсервер будет следить за изменениями, когда будут изменения, будет запускаться функция и будет выдававать новый список изменений. Этот
    //список мы будет передавать в адаптер и адаптер будет постоянно обновляться если есть какие-либо изменения в базе данных, а именно в наших заметках
    val allNotes: LiveData<List<NoteItem>> = dao.getAllNotes().asLiveData()    //достаем список один раз из базы данных, потому что мы подключили flow. Это у нас Flow, а ожидает LiveData, поэтому пишем asLiveData

    //10.3 мы заполняем класс NoteItem и передаем в функцию. Мы это будем делать в корутинах, потому что это трудоемкая операция
    // С помощью корутины мы сможем записать на второстепенном потоке данные, и это не будет захламлять основной поток, на котором рисуется весь пользовательский интерфейс
    fun insertNote(note: NoteItem) = viewModelScope.launch {
        dao.insertNote(note)    //т.о. мы напрямую DAO не используем. Veiw напрямую не использует бизнес-логику
    }

    //10.4 Создаем класс, который инициализирует MainViewModel. Используем ViewModelProvider.Factory, как и рекомендует google
    class MainViewModelFactory(val database: MainDataBase) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(database) as T //as T потому что жереник
            }
            throw IllegalArgumentException("Unknown ViewModelClass")
        }
    }
}