package br.com.alura.financask.delegate

import br.com.alura.financask.model.Transacao

interface TransacaoDelegate {

    fun delegate(transacao: Transacao)
}