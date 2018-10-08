package br.com.alura.financask.model

import java.math.BigDecimal
import java.util.Calendar

class Transacao (valor: BigDecimal,
                 categoria: String,
                 data: Calendar){

    private val valor: BigDecimal = valor
    private val categoria: String = categoria
    private val data: Calendar = data

    fun getValor () : BigDecimal{
        return valor
    }
}