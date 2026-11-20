package br.ufrn.agendafacil.model

data class Atendimento(
    val id: Long,
    val paciente: String,
    val profissional: String,
    val data: Data,
    val hora: Int,
    val duracao: Int,
) {
    val inicio: String get() = horario(hora * 60)

    val fim: String get() = horario(hora * 60 + duracao)

    private fun horario(minutos: Int): String {
        val h = (minutos / 60).toString().padStart(2, '0')
        val m = (minutos % 60).toString().padStart(2, '0')
        return "$h:$m"
    }
}
