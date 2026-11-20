package br.ufrn.agendafacil.model

class HorarioOcupadoException(message: String) : Exception(message)

object Validador {

    fun erroTexto(valor: String, campo: String): String? = if (valor.isBlank()) "$campo é obrigatório" else null

    fun erroData(texto: String): String? = if (Data.parse(texto) == null) "Data inválida, use AAAA-MM-DD" else null

    fun erroHora(hora: Int, duracao: Int): String? = when {
        hora < 8 || hora > 17 -> "A hora deve ser entre 8 e 17"
        hora * 60 + duracao > 18 * 60 -> "O atendimento termina depois das 18h"
        else -> null
    }

    fun erroDuracao(duracao: Int): String? = if (duracao < 15 || duracao > 120 || duracao % 15 != 0) {
        "Duração de 15 a 120 minutos, de 15 em 15"
    } else {
        null
    }
}

class Agenda(val atendimentos: List<Atendimento> = emptyList()) {

    fun doDia(data: Data) = atendimentos.filter { it.data == data }.sortedBy { it.hora }

    fun buscar(id: Long) = atendimentos.find { it.id == id }

    fun marcar(paciente: String, profissional: String, data: Data, hora: Int, duracao: Int): Agenda {
        val erro = Validador.erroTexto(paciente, "Paciente")
            ?: Validador.erroTexto(profissional, "Profissional")
            ?: Validador.erroDuracao(duracao)
            ?: Validador.erroHora(hora, duracao)
        require(erro == null) { erro.toString() }

        val nomeProfissional = profissional.trim()
        val ocupado = atendimentos.any { it.profissional == nomeProfissional && it.data == data && it.hora == hora }
        if (ocupado) {
            throw HorarioOcupadoException("$nomeProfissional já tem atendimento em ${data.formatar()} às ${hora}h")
        }

        val novoId = (atendimentos.maxOfOrNull { it.id } ?: 0) + 1
        val novo = Atendimento(novoId, paciente.trim(), nomeProfissional, data, hora, duracao)
        return Agenda(atendimentos + novo)
    }
}
