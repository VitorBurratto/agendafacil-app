package br.ufrn.agendafacil

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Surface
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import androidx.navigation.toRoute
import androidx.window.core.layout.WindowSizeClass
import br.ufrn.agendafacil.model.Agenda
import br.ufrn.agendafacil.model.Atendimento
import br.ufrn.agendafacil.model.Data
import br.ufrn.agendafacil.model.HorarioOcupadoException
import br.ufrn.agendafacil.ui.AgendaDetalheScreen
import br.ufrn.agendafacil.ui.AgendaScreen
import br.ufrn.agendafacil.ui.AgendaTheme
import br.ufrn.agendafacil.ui.DetalheScreen
import br.ufrn.agendafacil.ui.FormMarcar
import br.ufrn.agendafacil.ui.MarcarScreen
import br.ufrn.agendafacil.ui.salvar
import kotlinx.serialization.Serializable

@Serializable
object Home

@Serializable
data class Detalhe(val id: Long)

@Serializable
object Marcar

val diaInicial = Data(2026, 11, 18)

val agendaInicial = Agenda(
    listOf(
        Atendimento(1, "Ananda Ribeiro", "Dra. Camila Duarte", Data(2026, 11, 18), 9, 30),
        Atendimento(2, "Vítor Almeida", "Dra. Camila Duarte", Data(2026, 11, 18), 10, 60),
        Atendimento(3, "Juliana Pereira", "Dr. Paulo Mendes", Data(2026, 11, 18), 14, 60),
        Atendimento(4, "Rafael Gomes", "Dr. Paulo Mendes", Data(2026, 11, 18), 16, 45),
        Atendimento(5, "Mariana Lopes", "Dra. Camila Duarte", Data(2026, 11, 19), 8, 30),
    ),
)

@Composable
fun App() {
    AgendaTheme {
        Surface(Modifier.fillMaxSize().safeDrawingPadding()) {
            val tamanho = currentWindowAdaptiveInfo().windowSizeClass
            AppContent(isLargo = tamanho.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND))
        }
    }
}

@Composable
fun AppContent(isLargo: Boolean) {
    val navController = rememberNavController()
    var agenda by remember { mutableStateOf(agendaInicial) }
    var diaTexto by rememberSaveable { mutableStateOf(diaInicial.toString()) }
    var selecionado by rememberSaveable { mutableStateOf<Long?>(null) }
    val dia = Data.parse(diaTexto) ?: diaInicial

    NavHost(navController, startDestination = Home) {
        composable<Home> {
            if (isLargo) {
                AgendaDetalheScreen(
                    agenda = agenda,
                    dia = dia,
                    selecionado = selecionado,
                    onDiaChange = { diaTexto = it.toString() },
                    onAbrir = { selecionado = it },
                    onMarcar = { navController.navigate(Marcar) },
                )
            } else {
                AgendaScreen(
                    dia = dia,
                    atendimentos = agenda.doDia(dia),
                    onDiaChange = { diaTexto = it.toString() },
                    onAbrir = { navController.navigate(Detalhe(it)) },
                    onMarcar = { navController.navigate(Marcar) },
                )
            }
        }

        composable<Detalhe>(deepLinks = listOf(navDeepLink<Detalhe>(basePath = "agendafacil://atendimento"))) {
            val id = it.toRoute<Detalhe>().id
            DetalheScreen(agenda.buscar(id), onBack = { navController.popBackStack() })
        }

        composable<Marcar> {
            var form by remember { mutableStateOf(FormMarcar(data = diaTexto)) }
            var erro by remember { mutableStateOf<String?>(null) }
            MarcarScreen(
                form = form,
                onChange = {
                    form = it
                    erro = null
                },
                erro = erro,
                onSave = {
                    try {
                        agenda = salvar(agenda, form)
                        diaTexto = form.data.trim()
                        navController.popBackStack()
                    } catch (e: HorarioOcupadoException) {
                        erro = e.message
                    }
                },
                onBack = { navController.popBackStack() },
            )
        }
    }
}
