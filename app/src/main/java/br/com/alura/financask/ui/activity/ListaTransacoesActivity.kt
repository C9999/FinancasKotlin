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
import android.widget.Toast
import br.com.alura.financask.R
import br.com.alura.financask.delegate.TransacaoDelegate
import br.com.alura.financask.extension.formataParaBrasileiro
import br.com.alura.financask.model.Tipo
import br.com.alura.financask.model.Transacao
import br.com.alura.financask.ui.ResumoView
import br.com.alura.financask.ui.adapter.ListaTransacoesAdapter
import br.com.alura.financask.ui.dialog.AdicionaTransacaoDialog
import kotlinx.android.synthetic.main.activity_lista_transacoes.*
import kotlinx.android.synthetic.main.form_transacao.view.*
import java.lang.NumberFormatException
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.*

class ListaTransacoesActivity : AppCompatActivity() {

    private val transacoes: MutableList<Transacao> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)

        configuraResumo()

        configuraLista()

        lista_transacoes_adiciona_receita.setOnClickListener {
            AdicionaTransacaoDialog(window.decorView as ViewGroup, this)
                    .configuraDialog(Tipo.RECEITA, object : TransacaoDelegate {
                        override fun delegate(transacao: Transacao) {
                            atualizaTrasacoes(transacao)
                            lista_transacoes_adiciona_menu.close(true)
                        }

                    })
        }
        lista_transacoes_adiciona_despesa.setOnClickListener {
            AdicionaTransacaoDialog(window.decorView as ViewGroup, this)
                    .configuraDialog(Tipo.DESPESA, object : TransacaoDelegate {
                        override fun delegate(transacao: Transacao) {
                            atualizaTrasacoes(transacao)
                            lista_transacoes_adiciona_menu.close(true)
                        }

                    })
        }
    }


    private fun atualizaTrasacoes(transacao: Transacao) {
        transacoes.add(transacao)
        configuraLista()
        configuraResumo()
    }

    private fun configuraResumo() {
        val view: View = window.decorView
        val resumoView = ResumoView(view, this, transacoes) //
        resumoView.atualiza()

    }

    private fun configuraLista() {
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