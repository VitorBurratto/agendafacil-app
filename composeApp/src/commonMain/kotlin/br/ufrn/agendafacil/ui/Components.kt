package br.ufrn.agendafacil.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.error
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import br.ufrn.agendafacil.model.Atendimento
import br.ufrn.agendafacil.model.Data

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(titulo: String, onBack: (() -> Unit)? = null) {
    TopAppBar(
        title = { Text(titulo, modifier = Modifier.semantics { heading() }) },
        navigationIcon = {
            if (onBack != null) {
                IconButton(onClick = onBack, modifier = Modifier.semantics { contentDescription = "Voltar" }) {
                    Text("←", style = MaterialTheme.typography.titleLarge, modifier = Modifier.clearAndSetSemantics {})
                }
            }
        },
    )
}

@Composable
fun AtendimentoCard(atendimento: Atendimento, onClick: () -> Unit, modifier: Modifier = Modifier) {
    val texto = "${atendimento.inicio} às ${atendimento.fim}, ${atendimento.paciente}, com ${atendimento.profissional}"
    Card(onClick = onClick, modifier = modifier.fillMaxWidth().semantics { contentDescription = texto }) {
        Row(
            modifier = Modifier.padding(16.dp).clearAndSetSemantics {},
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Column {
                Text(atendimento.inicio, style = MaterialTheme.typography.titleMedium)
                Text(atendimento.fim, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Column {
                Text(
                    atendimento.paciente,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(atendimento.profissional, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 1)
            }
        }
    }
}

@Composable
fun DiaSelector(dia: Data, onDiaChange: (Data) -> Unit, modifier: Modifier = Modifier) {
    Row(modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        IconButton(
            onClick = { onDiaChange(dia.diaAnterior()) },
            modifier = Modifier.semantics { contentDescription = "Dia anterior" },
        ) {
            Text("◀", modifier = Modifier.clearAndSetSemantics {})
        }
        Text(
            dia.formatar(),
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f),
        )
        IconButton(
            onClick = { onDiaChange(dia.proximoDia()) },
            modifier = Modifier.semantics { contentDescription = "Próximo dia" },
        ) {
            Text("▶", modifier = Modifier.clearAndSetSemantics {})
        }
    }
}

@Composable
fun InputField(
    label: String,
    value: String,
    onChange: (String) -> Unit,
    erro: String?,
    keyboard: KeyboardType = KeyboardType.Text,
    dica: String? = null,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        label = { Text(label) },
        placeholder = dica?.let { { Text(it) } },
        isError = erro != null,
        supportingText = erro?.let { { Text(it) } },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = keyboard),
        modifier = Modifier.fillMaxWidth().semantics { if (erro != null) error(erro) },
    )
}

@Composable
fun EmptyState(texto: String, modifier: Modifier = Modifier) {
    Box(modifier.fillMaxSize().padding(24.dp), contentAlignment = Alignment.Center) {
        Text(texto, color = MaterialTheme.colorScheme.onSurfaceVariant, textAlign = TextAlign.Center)
    }
}

@Composable
fun InfoRow(label: String, valor: String) {
    Column(Modifier.fillMaxWidth().semantics(mergeDescendants = true) {}) {
        Text(label, style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(valor, style = MaterialTheme.typography.bodyLarge)
    }
}
