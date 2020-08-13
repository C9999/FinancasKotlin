package br.com.alura.financask.ui

import android.support.v4.content.ContextCompat
import android.view.View
import br.com.alura.financask.R
import br.com.alura.financask.extension.formataParaBrasileiro
import br.com.alura.financask.model.Resumo
import br.com.alura.financask.model.Transacao
import br.com.alura.financask.ui.activity.ListaTransacoesActivity
import kotlinx.android.synthetic.main.resumo_card.view.*
import java.math.BigDecimal

class ResumoView(private val view: View?,
                 private val context: ListaTransacoesActivity,
                 transacoes: List<Transacao>) {

    private val resumo: Resumo = Resumo(transacoes)
    private val corReceita = ContextCompat.getColor(context, R.color.receita)
    private val corDespesa = ContextCompat.getColor(context, R.color.despesa)

    fun atualiza(){
        adicionaReceita()
        adicionaDespesa()
        adicionaTotal()
    }

    private fun adicionaReceita() {
        val totalReceita = resumo.receita()
        view?.let{
            with(it.resumo_card_receita){
                setTextColor(corReceita)
                text = totalReceita.formataParaBrasileiro()
            }
        }
    }

    private fun adicionaDespesa() {
        val totalDespesa = resumo.despesa()
        view?.let{
            with(view.resumo_card_despesa){
                setTextColor(corDespesa)
                text = totalDespesa.formataParaBrasileiro()
            }
        }
    }

    private fun adicionaTotal() {
        val total = resumo.total()
        val cor = corPor(total)
        view?.let{
            with(it.resumo_card_total){
                setTextColor(cor)
                text = total.formataParaBrasileiro()
            }
        }
    }

    private fun corPor(valor: BigDecimal): Int {
        if (valor >= BigDecimal.ZERO) {
            return corReceita
        } else {
            return corDespesa
        }
    }
}