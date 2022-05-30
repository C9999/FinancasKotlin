package br.com.alura.financask.dao

import br.com.alura.financask.model.Transacao

class TransacaoDAO {
    val transacoes: List<Transacao> = Companion.transacoes
    companion object {
        private val transacoes: MutableList<Transacao> = mutableListOf()
    }


    fun adiciona(transacao: Transacao) {
        Companion.transacoes.add(transacao)
    }

    fun altera(transacao: Transacao, posicao: Int){
        Companion.transacoes[posicao] = transacao
    }

    fun remove (posicao: Int){
        Companion.transacoes.removeAt(posicao)
    }
}