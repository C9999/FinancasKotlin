package br.com.alura.financask.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import br.com.alura.financask.R
import br.com.alura.financask.model.Transacao
import br.com.alura.financask.ui.adapter.ListaTransacoesAdapter
import kotlinx.android.synthetic.main.activity_lista_transacoes.*
import java.math.BigDecimal
import java.util.Calendar

class ListaTransacoesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)

        val transacoes = listOf(Transacao(BigDecimal(20.5),
                "Comida", Calendar.getInstance()),
                Transacao(BigDecimal(100.0),
                        "Economia", Calendar.getInstance()))

        val arrayAdapter = ArrayAdapter(this,
            android.R.layout.simple_expandable_list_item_1, transacoes)

        lista_transacoes_listview.setAdapter(ListaTransacoesAdapter(transacoes, this))
    }
}