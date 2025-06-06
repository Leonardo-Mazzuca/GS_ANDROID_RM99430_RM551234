package leonardomazzuca.com.github

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface EventoDAO {

    @Query("SELECT * FROM EventoExtremoModel")
    fun getAll(): LiveData<List<EventoExtremoModel>>

    @Insert
    fun insert(item: EventoExtremoModel)

    @Delete
    fun delete(item: EventoExtremoModel)
}