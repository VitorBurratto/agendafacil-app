package br.ufrn.agendafacil.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.ufrn.agendafacil.agendaInicial
import br.ufrn.agendafacil.diaInicial
import br.ufrn.agendafacil.model.Agenda
import br.ufrn.agendafacil.model.Atendimento
import br.ufrn.agendafacil.model.Data

@Composable
fun AgendaScreen(
    dia: Data,
    atendimentos: List<Atendimento>,
    onDiaChange: (Data) -> Unit,
    onAbrir: (Long) -> Unit,
    onMarcar: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = { TopBar("Agenda") },
        floatingActionButton = {
            ExtendedFloatingActionButton(onClick = onMarcar) { Text("Marcar atendimento") }
        },
    ) { padding ->
        Column(Modifier.padding(padding).fillMaxSize()) {
            DiaSelector(dia, onDiaChange, Modifier.padding(horizontal = 8.dp))
            if (atendimentos.isEmpty()) {
                EmptyState("Nenhum atendimento neste dia.")
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 96.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(atendimentos, key = { it.id }) { atendimento ->
                        AtendimentoCard(atendimento, onClick = { onAbrir(atendimento.id) })
                    }
                }
            }
        }
    }
}

@Composable
fun AgendaDetalheScreen(
    agenda: Agenda,
    dia: Data,
    selecionado: Long?,
    onDiaChange: (Data) -> Unit,
    onAbrir: (Long) -> Unit,
    onMarcar: () -> Unit,
) {
    Row(Modifier.fillMaxSize()) {
        AgendaScreen(dia, agenda.doDia(dia), onDiaChange, onAbrir, onMarcar, Modifier.weight(0.45f))
        VerticalDivider()
        DetalheScreen(
            atendimento = selecionado?.let { agenda.buscar(it) },
            onBack = null,
            textoVazio = "Selecione um atendimento na lista.",
            modifier = Modifier.weight(0.55f).fillMaxHeight(),
        )
    }
}

@Preview
@Composable
fun AgendaPreview() {
    AgendaTheme {
        AgendaScreen(diaInicial, agendaInicial.doDia(diaInicial), {}, {}, {})
    }
}

@Preview
@Composable
fun AgendaVaziaPreview() {
    AgendaTheme {
        AgendaScreen(diaInicial, emptyList(), {}, {}, {})
    }
}

@Preview
@Composable
fun AgendaEscuraPreview() {
    AgendaTheme(isDark = true) {
        AgendaScreen(diaInicial, agendaInicial.doDia(diaInicial), {}, {}, {})
    }
}

@Preview(widthDp = 900, heightDp = 600)
@Composable
fun AgendaDetalhePreview() {
    AgendaTheme {
        AgendaDetalheScreen(agendaInicial, diaInicial, 2, {}, {}, {})
    }
}
