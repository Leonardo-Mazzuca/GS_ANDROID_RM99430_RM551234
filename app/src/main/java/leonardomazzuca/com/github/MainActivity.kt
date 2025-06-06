package leonardomazzuca.com.github

import EventoViewModel
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: EventoViewModel

    private val tiposEvento = listOf("Chuva", "Onda de calor", "Chuva intensa")
    private val grausImpacto = listOf("Leve", "Moderado", "Severo")

    fun validarCampoVazio(editText: EditText, mensagemErro: String): Boolean {
        return if (editText.text.toString().isBlank()) {
            editText.error = mensagemErro
            true
        } else {
            editText.error = null
            false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_button -> {
                val intent = Intent(this, RmActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
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
        val dataEvento = findViewById<EditText>(R.id.dataEvento)
        val numPessoasText = findViewById<EditText>(R.id.numPessoas)

        val tiposEventoAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_dropdown_item_1line,
            tiposEvento
        )

        val grausImpactoAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_dropdown_item_1line,
            grausImpacto
        )

        val tipoEventoInput = findViewById<AutoCompleteTextView>(R.id.tipoEvento)
        tipoEventoInput.setAdapter(tiposEventoAdapter)

        val grauImpactoInput = findViewById<AutoCompleteTextView>(R.id.grauImpacto)
        grauImpactoInput.setAdapter(grausImpactoAdapter)

        tipoEventoInput.setOnClickListener {
            tipoEventoInput.showDropDown()
        }

        grauImpactoInput.setOnClickListener {
            grauImpactoInput.showDropDown()
        }

        button.setOnClickListener {

            val temErro = listOf(
                validarCampoVazio(nomeLocalText, "O nome do local é obrigatório!"),
                validarCampoVazio(tipoEventoInput, "O tipo de evento é obrigatório!"),
                validarCampoVazio(grauImpactoInput, "O grau de impacto é obrigatório!"),
                validarCampoVazio(dataEvento, "A data de evento é obrigatória!"),
                validarCampoVazio(numPessoasText, "O número de pessoas estimado é obrigatório!")
            ).any { it }

            if (temErro) return@setOnClickListener

            val newItem = EventoExtremoModel(
                nomeDoLocal = nomeLocalText.text.toString(),
                tipoEvento =  tipoEventoInput.text.toString(),
                grauImpacto = grauImpactoInput.text.toString(),
                dataEvento = dataEvento.text.toString(),
                numeroEstimadoPessoas = numPessoasText.text.toString().toIntOrNull() ?: 0
            )

            viewModel.addItem(newItem)
            nomeLocalText.text.clear()
            tipoEventoInput.text.clear()
            grauImpactoInput.text.clear()
            dataEvento.text.clear()
            numPessoasText.text.clear()

        }

        val viewModelFactory = ItemsViewModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(EventoViewModel::class.java)

        viewModel.itemsLiveData.observe(this) { items ->
            itemsAdapter.updateItems(items)
        }
    }
}