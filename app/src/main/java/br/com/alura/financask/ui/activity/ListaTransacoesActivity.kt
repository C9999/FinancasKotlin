package br.com.alura.financask.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import br.com.alura.financask.R
//import br.com.alura.financask.delegate.TransacaoDelegate
//import br.com.alura.financask.delegate.TransacaoDelegateJava
import br.com.alura.financask.model.Tipo
import br.com.alura.financask.model.Transacao
import br.com.alura.financask.ui.ResumoView
import br.com.alura.financask.ui.adapter.ListaTransacoesAdapter
import br.com.alura.financask.ui.dialog.AdicionaTransacaoDialog
import br.com.alura.financask.ui.dialog.AlteraTransacaoDialog
import kotlinx.android.synthetic.main.activity_lista_transacoes.*
import java.math.BigDecimal
import java.util.*

class ListaTransacoesActivity : AppCompatActivity() {

    private val transacoes: MutableList<Transacao> = mutableListOf()
    private val viewDaActivity by lazy {
        window.decorView
    }

    private val viewGroupDaActivity by lazy {
        viewDaActivity as ViewGroup
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)

//        viewDaActivity = window.decorView
        // inicio do capítulo 6

        configuraResumo()
        configuraLista()
        configuraFloatActionButton()
    }

    private fun configuraFloatActionButton() {
        lista_transacoes_adiciona_receita.setOnClickListener {
            chamaDialogDeAdicao(Tipo.RECEITA)
        }
        lista_transacoes_adiciona_despesa.setOnClickListener {
            chamaDialogDeAdicao(Tipo.DESPESA)
        }
    }

    private fun chamaDialogDeAdicao(tipo: Tipo) {
        AdicionaTransacaoDialog(viewGroupDaActivity, this)
                .chama(tipo)   { transacaoCriada ->
                    adiciona(transacaoCriada)
                    lista_transacoes_adiciona_menu.close(true)
                }
    }

    private fun adiciona(transacao: Transacao) {
        transacoes.add(transacao)
        atualizaTrasacoes()
    }


    private fun atualizaTrasacoes() {
        configuraLista()
        configuraResumo()
    }

    private fun configuraResumo() {
//        val view: View = viewDaActivity
        val resumoView = ResumoView(viewDaActivity, this, transacoes) //
        resumoView.atualiza()

    }

    private fun configuraLista() {
        val listaTransacoesAdapter = ListaTransacoesAdapter(transacoes, this)
        with(lista_transacoes_listview){
            adapter = listaTransacoesAdapter
            setOnItemClickListener { _, _, posicao, _ ->
                val transacao = transacoes[posicao]
                chamaDialogDeAlteracao(transacao, posicao)
            }
            setOnCreateContextMenuListener { menu, _, _ ->
                menu.add(Menu.NONE, 1, Menu.NONE, "Remover")
            }
        }
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {
        val idDoMenu = item?.itemId
        if(idDoMenu == 1){
            val adapterContextMenuInfo = item.menuInfo as AdapterView.AdapterContextMenuInfo
            val posicaoDaTransacao = adapterContextMenuInfo.position
            remove(posicaoDaTransacao)
//            Toast.makeText(this, "menu remover foi tocado", Toast.LENGTH_LONG).show()
        }
        return super.onContextItemSelected(item)
    }

    private fun remove(posicao: Int) {
        transacoes.removeAt(posicao)
        atualizaTrasacoes()
    }

    private fun chamaDialogDeAlteracao(transacao: Transacao, posicao: Int) {
        AlteraTransacaoDialog(viewGroupDaActivity, this)
                .chama(transacao) { transacaoAlterada ->
                    altera(transacaoAlterada, posicao)
                }
    }

    private fun altera(transacao: Transacao, posicao: Int) {
        transacoes[posicao] = transacao
        atualizaTrasacoes()
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