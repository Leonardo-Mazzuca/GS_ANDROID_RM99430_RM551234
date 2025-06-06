import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import leonardomazzuca.com.github.EventoDAO
import leonardomazzuca.com.github.EventoDatabase
import leonardomazzuca.com.github.EventoExtremoModel

class EventoViewModel(application: Application) : AndroidViewModel(application) {

    private val itemDao: EventoDAO
    val itemsLiveData: LiveData<List<EventoExtremoModel>>

    init {
        val database = Room.databaseBuilder(
            getApplication(),
            EventoDatabase::class.java,
            "eventos_database"
        ).build()


        itemDao = database.eventoDao()
        itemsLiveData = itemDao.getAll()
    }


    fun addItem(newItem: EventoExtremoModel) {
        viewModelScope.launch(Dispatchers.IO) {
            itemDao.insert(newItem)
        }
    }

    fun removeItem(item: EventoExtremoModel) {
        viewModelScope.launch(Dispatchers.IO) {
            itemDao.delete(item)
        }

    }
}