package br.ufrn.agendafacil.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.ufrn.agendafacil.agendaInicial
import br.ufrn.agendafacil.model.Atendimento

@Composable
fun DetalheScreen(
    atendimento: Atendimento?,
    onBack: (() -> Unit)?,
    modifier: Modifier = Modifier,
    textoVazio: String = "Atendimento não encontrado.",
) {
    Scaffold(modifier = modifier, topBar = { TopBar("Atendimento", onBack) }) { padding ->
        if (atendimento == null) {
            EmptyState(textoVazio, Modifier.padding(padding))
        } else {
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                InfoRow("Paciente", atendimento.paciente)
                InfoRow("Profissional", atendimento.profissional)
                InfoRow("Data", atendimento.data.formatar())
                InfoRow("Horário", "${atendimento.inicio} às ${atendimento.fim}")
                InfoRow("Duração", "${atendimento.duracao} minutos")
            }
        }
    }
}

@Preview
@Composable
fun DetalhePreview() {
    AgendaTheme { DetalheScreen(agendaInicial.buscar(1), onBack = {}) }
}

@Preview
@Composable
fun DetalheVazioPreview() {
    AgendaTheme { DetalheScreen(null, onBack = {}) }
}
