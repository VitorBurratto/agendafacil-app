package br.ufrn.agendafacil.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.ufrn.agendafacil.model.Agenda
import br.ufrn.agendafacil.model.Data
import br.ufrn.agendafacil.model.Validador

data class FormMarcar(
    val paciente: String = "",
    val profissional: String = "",
    val data: String = "",
    val hora: String = "",
    val duracao: String = "30",
)

data class ErrosForm(
    val paciente: String?,
    val profissional: String?,
    val data: String?,
    val hora: String?,
    val duracao: String?,
) {
    val isValido: Boolean get() = listOf(paciente, profissional, data, hora, duracao).all { it == null }
}

fun validar(form: FormMarcar): ErrosForm {
    val hora = form.hora.trim().toIntOrNull()
    val duracao = form.duracao.trim().toIntOrNull()
    return ErrosForm(
        paciente = Validador.erroTexto(form.paciente, "Paciente"),
        profissional = Validador.erroTexto(form.profissional, "Profissional"),
        data = Validador.erroData(form.data),
        hora = if (hora == null) "Digite a hora" else Validador.erroHora(hora, duracao ?: 0),
        duracao = if (duracao == null) "Digite a duração" else Validador.erroDuracao(duracao),
    )
}

fun salvar(agenda: Agenda, form: FormMarcar): Agenda = agenda.marcar(
    paciente = form.paciente,
    profissional = form.profissional,
    data = Data.parse(form.data) ?: error("Data inválida"),
    hora = form.hora.trim().toInt(),
    duracao = form.duracao.trim().toInt(),
)

private fun mostrar(valor: String, erro: String?) = if (valor.isBlank()) null else erro

@Composable
fun MarcarScreen(
    form: FormMarcar,
    onChange: (FormMarcar) -> Unit,
    erro: String?,
    onSave: () -> Unit,
    onBack: () -> Unit,
) {
    val erros = validar(form)
    Scaffold(topBar = { TopBar("Marcar atendimento", onBack) }) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
                .widthIn(max = 560.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            InputField(
                "Paciente",
                form.paciente,
                { onChange(form.copy(paciente = it)) },
                mostrar(form.paciente, erros.paciente),
            )
            InputField(
                "Profissional",
                form.profissional,
                { onChange(form.copy(profissional = it)) },
                mostrar(form.profissional, erros.profissional),
            )
            InputField(
                "Data",
                form.data,
                { onChange(form.copy(data = it)) },
                mostrar(form.data, erros.data),
                dica = "AAAA-MM-DD",
            )
            InputField(
                "Hora de início",
                form.hora,
                { onChange(form.copy(hora = it)) },
                mostrar(form.hora, erros.hora),
                KeyboardType.Number,
                "8 a 17",
            )
            InputField(
                "Duração (minutos)",
                form.duracao,
                { onChange(form.copy(duracao = it)) },
                mostrar(form.duracao, erros.duracao),
                KeyboardType.Number,
                "15 a 120",
            )

            if (erro != null) {
                Text(
                    erro,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.semantics { liveRegion = LiveRegionMode.Polite },
                )
            }
            if (!erros.isValido) {
                Text("Preencha todos os campos corretamente.", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Button(onClick = onSave, enabled = erros.isValido, modifier = Modifier.fillMaxWidth()) {
                Text("Marcar")
            }
        }
    }
}

@Preview
@Composable
fun MarcarPreview() {
    AgendaTheme { MarcarScreen(FormMarcar(), {}, null, {}, {}) }
}

@Preview
@Composable
fun MarcarComErroPreview() {
    AgendaTheme {
        MarcarScreen(
            FormMarcar("Ananda Ribeiro", "Dra. Camila Duarte", "2026-02-30", "19", "20"),
            {},
            "Dra. Camila Duarte já tem atendimento em 18/11/2026 às 9h",
            {},
            {},
        )
    }
}
