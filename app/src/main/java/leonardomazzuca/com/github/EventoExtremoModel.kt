package leonardomazzuca.com.github

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class EventoExtremoModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nomeDoLocal: String,
    val tipoEvento: String,
    val grauImpacto: String,
    val dataEvento: String,
    val numeroEstimadoPessoas: Int
)