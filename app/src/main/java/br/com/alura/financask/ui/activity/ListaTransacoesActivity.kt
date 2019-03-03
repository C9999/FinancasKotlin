package br.com.alura.financask.ui.activity

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import br.com.alura.financask.R
import br.com.alura.financask.R.id.lista_transacoes_adiciona_receita
import br.com.alura.financask.extension.formataParaBrasileiro
import br.com.alura.financask.model.Tipo
import br.com.alura.financask.model.Transacao
import br.com.alura.financask.ui.ResumoView
import br.com.alura.financask.ui.adapter.ListaTransacoesAdapter
import kotlinx.android.synthetic.main.activity_lista_transacoes.*
import kotlinx.android.synthetic.main.form_transacao.view.*
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.*

class ListaTransacoesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)

        val transacoes: List<Transacao> = transacoesDeExemplo()

        configuraResumo(transacoes)

        configuraLista(transacoes)

        lista_transacoes_adiciona_receita.setOnClickListener {
            val view: View = window.decorView
            val viewCriada = LayoutInflater.from(this)
                    .inflate(
                            R.layout.form_transacao,
                            view as ViewGroup,
                            false)

            val ano = 2019
            val mes = 1
            val dia = 27

            val hoje = Calendar.getInstance()
            viewCriada.form_transacao_data.setText(hoje.formataParaBrasileiro())

            viewCriada.form_transacao_data.
                    setOnClickListener {
                        DatePickerDialog(this,
                                DatePickerDialog.OnDateSetListener { view, ano, mes, dia ->
                                    val dataSelecionada = Calendar.getInstance()
                                    dataSelecionada.set(ano, mes, dia)
                                    viewCriada.form_transacao_data.setText(dataSelecionada.formataParaBrasileiro())
                                }, ano, mes, dia).show()
                    }

            val adapter = ArrayAdapter
                    .createFromResource(this,
                            R.array.categorias_de_receita,
                            android.R.layout.simple_spinner_dropdown_item)
            viewCriada.form_transacao_categoria.adapter = adapter

            AlertDialog.Builder(this)
                    .setTitle(R.string.adiciona_receita)
                    .setPositiveButton("Adcionar",
                            DialogInterface.OnClickListener { dialogInterface, i ->
                                    val valorEmTexto = viewCriada.form_transacao_valor.text.toString()
                                    val dataEmTexto = viewCriada.form_transacao_data.text.toString()
                                    val categoriaEmTexto = viewCriada.form_transacao_categoria.selectedItem.toString()

                                    val valor = BigDecimal(valorEmTexto)

                                    val formatoBrasileiro = SimpleDateFormat("dd/MM/yyyy")
                                    val dataConvertida: Date = formatoBrasileiro.parse(dataEmTexto)
                                    val data = Calendar.getInstance()
                                    data.time = dataConvertida


                                    val transacaoCriada = Transacao(tipo = Tipo.RECEITA,
                                            valor = valor,
                                            data = data,
                                            categoria = categoriaEmTexto)
//                                    Toast.makeText(this, "${transacaoCriada.valor} - ${transacaoCriada.data.formataParaBrasileiro()} - ${transacaoCriada.categoria} - ${transacaoCriada.tipo}", Toast.LENGTH_LONG).show()
                            })
                    .setNegativeButton("Cancelar", null)
                    .setView(viewCriada)
                    .show()
        }

    }

    private fun configuraResumo(transacoes: List<Transacao>) {
        val view: View = window.decorView
        val resumoView = ResumoView(view, this, transacoes)
        resumoView.atualiza()

    }

    private fun configuraLista(transacoes: List<Transacao>) {
        lista_transacoes_listview.adapter = ListaTransacoesAdapter(transacoes, this)
    }

    private fun transacoesDeExemplo(): List<Transacao> {
        return listOf(
                Transacao(valor = BigDecimal(20.5), categoria = "Jantar", data = Calendar.getInstance(), tipo = Tipo.DESPESA),
                Transacao(valor = BigDecimal(100.0), categoria = "Economia", tipo = Tipo.RECEITA),
                Transacao(valor = BigDecimal(900.00), categoria = "Pagamento do 13", tipo = Tipo.RECEITA),
                Transacao(valor = BigDecimal(200), tipo = Tipo.DESPESA, data = Calendar.getInstance(), categoria = "Reforma"))
    }
}
//lista_transacoes_adiciona_receita.setOnClickListener { Toast.makeText(this, "clique de receita", Toast.LENGTH_LONG).show() }