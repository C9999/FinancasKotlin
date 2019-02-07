package br.com.alura.financask.ui

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.View
import br.com.alura.financask.R
import br.com.alura.financask.extension.formataParaBrasileiro
import br.com.alura.financask.model.Resumo
import br.com.alura.financask.model.Transacao
import kotlinx.android.synthetic.main.resumo_card.view.*
import java.math.BigDecimal

class ResumoView(private val view: View,
                 private val context: Context,
                 transacoes: List<Transacao>) {

    private val resumo: Resumo = Resumo(transacoes)

    fun adicionaReceita() {
        val totalReceita = resumo.receita()
        view.resumo_card_receita.setTextColor(ContextCompat.getColor(context, R.color.receita))
        view.resumo_card_receita.text = totalReceita.formataParaBrasileiro()
    }

    fun adicionaDespesa() {
        val totalDespesa = resumo.despesa()
        view.resumo_card_despesa.setTextColor(ContextCompat.getColor(context, R.color.despesa))
        view.resumo_card_despesa.text = totalDespesa.formataParaBrasileiro()
    }

    fun adicionaTotal() {
        val total = resumo.total()

        if(total.compareTo(BigDecimal.ZERO) >= 0){
            view.resumo_card_total.setTextColor(ContextCompat.getColor(context, R.color.receita))
        }else{
            view.resumo_card_total.setTextColor(ContextCompat.getColor(context, R.color.despesa))
        }
        view.resumo_card_total.text = total.formataParaBrasileiro()


    }
}