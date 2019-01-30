package br.com.alura.financask.model

import java.math.BigDecimal
import java.util.Calendar

class Transacao (val valor: BigDecimal,
                 val tipo: Tipo,
                 val categoria: String = "Indefinida",
                 val data: Calendar = Calendar.getInstance())