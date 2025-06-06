package leonardomazzuca.com.github

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EventoAdapter(private val onItemRemoved: (EventoExtremoModel) -> Unit) :
    RecyclerView.Adapter<EventoAdapter.ItemViewHolder>() {


    private var items = listOf<EventoExtremoModel>()

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val nomeView = view.findViewById<TextView>(R.id.nomeLocalView)
        val tipoView = view.findViewById<TextView>(R.id.tipoEventoView)
        val grauView = view.findViewById<TextView>(R.id.grauImpactoView)
        val dataEventoView = view.findViewById<TextView>(R.id.dataEventoView)
        val numPessoasView = view.findViewById<TextView>(R.id.numEstimadoView)

        val button = view.findViewById<ImageButton>(R.id.imageButton)

        fun bind(item: EventoExtremoModel) {
            nomeView.text = item.nomeDoLocal
            tipoView.text = item.tipoEvento
            grauView.text = item.grauImpacto
            dataEventoView.text = item.dataEvento
            numPessoasView.text = item.numeroEstimadoPessoas.toString()

            button.setOnClickListener {
                onItemRemoved(item)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.evento_layout, parent, false)
        return ItemViewHolder(view)
    }


    override fun getItemCount(): Int = items.size


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }


    fun updateItems(newItems: List<EventoExtremoModel>) {
        items = newItems
        notifyDataSetChanged()
    }
}