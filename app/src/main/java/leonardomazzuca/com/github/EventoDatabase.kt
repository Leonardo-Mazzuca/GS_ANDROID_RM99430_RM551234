package leonardomazzuca.com.github

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [EventoExtremoModel::class], version = 1)
abstract class EventoDatabase : RoomDatabase() {

    abstract fun eventoDao(): EventoDAO
}