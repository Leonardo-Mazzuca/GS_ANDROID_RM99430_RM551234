package leonardomazzuca.com.github

import EventoViewModel
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: EventoViewModel

    fun validarCampoVazio(editText: EditText, mensagemErro: String): Boolean {
        return if (editText.text.toString().isBlank()) {
            editText.error = mensagemErro
            true
        } else {
            editText.error = null
            false
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Plantão Lauzane Da Pátria"

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val itemsAdapter = EventoAdapter { item ->
            viewModel.removeItem(item)
        }
        recyclerView.adapter = itemsAdapter

        val button = findViewById<Button>(R.id.button)
        val nomeLocalText = findViewById<EditText>(R.id.nomeDoLocal)
        val tipoEventoText = findViewById<EditText>(R.id.tipoEvento)
        val grauImpactoText = findViewById<EditText>(R.id.grauImpacto)
        val dataEvento = findViewById<EditText>(R.id.dataEvento)
        val numPessoasText = findViewById<EditText>(R.id.numPessoas)

        button.setOnClickListener {

            val temErro = listOf(
                validarCampoVazio(nomeLocalText, "O nome do local é obrigatório!"),
                validarCampoVazio(tipoEventoText, "O tipo de evento é obrigatório!"),
                validarCampoVazio(grauImpactoText, "O grau de impacto é obrigatório!"),
                validarCampoVazio(dataEvento, "A data de evento é obrigatória!"),
                validarCampoVazio(numPessoasText, "O número de pessoas estimado é obrigatório!")
            ).any { it }

            if (temErro) return@setOnClickListener

            val newItem = EventoExtremoModel(
                nomeDoLocal = nomeLocalText.text.toString(),
                tipoEvento = tipoEventoText.text.toString(),
                grauImpacto = grauImpactoText.text.toString(),
                dataEvento = dataEvento.text.toString(),
                numeroEstimadoPessoas = numPessoasText.text.toString().toIntOrNull() ?: 0
            )

            viewModel.addItem(newItem)
            nomeLocalText.text.clear()

        }

        val viewModelFactory = ItemsViewModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(EventoViewModel::class.java)

        viewModel.itemsLiveData.observe(this) { items ->
            itemsAdapter.updateItems(items)
        }
    }
}