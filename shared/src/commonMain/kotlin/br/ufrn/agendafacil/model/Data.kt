package br.ufrn.agendafacil.model

data class Data(val ano: Int, val mes: Int, val dia: Int) {

    override fun toString() = "$ano-${doisDigitos(mes)}-${doisDigitos(dia)}"

    fun formatar() = "${doisDigitos(dia)}/${doisDigitos(mes)}/$ano"

    fun proximoDia(): Data = when {
        dia < diasNoMes(ano, mes) -> Data(ano, mes, dia + 1)
        mes < 12 -> Data(ano, mes + 1, 1)
        else -> Data(ano + 1, 1, 1)
    }

    fun diaAnterior(): Data = when {
        dia > 1 -> Data(ano, mes, dia - 1)
        mes > 1 -> Data(ano, mes - 1, diasNoMes(ano, mes - 1))
        else -> Data(ano - 1, 12, 31)
    }

    companion object {
        fun parse(texto: String): Data? {
            val partes = Regex("""(\d{4})-(\d{2})-(\d{2})""").matchEntire(texto.trim()) ?: return null
            val ano = partes.groupValues[1].toInt()
            val mes = partes.groupValues[2].toInt()
            val dia = partes.groupValues[3].toInt()
            return if (mes in 1..12 && dia in 1..diasNoMes(ano, mes)) Data(ano, mes, dia) else null
        }

        private fun diasNoMes(ano: Int, mes: Int): Int = when (mes) {
            2 -> if (isBissexto(ano)) 29 else 28
            4, 6, 9, 11 -> 30
            else -> 31
        }

        private fun isBissexto(ano: Int) = ano % 4 == 0 && (ano % 100 != 0 || ano % 400 == 0)
    }
}

private fun doisDigitos(numero: Int) = numero.toString().padStart(2, '0')
