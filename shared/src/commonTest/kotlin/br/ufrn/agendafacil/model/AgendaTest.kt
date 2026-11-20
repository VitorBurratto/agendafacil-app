package br.ufrn.agendafacil.model

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull

class AgendaTest {

    private val dia = Data(2026, 10, 5)

    @Test
    fun marcaAtendimento() {
        val agenda = Agenda().marcar("Ananda Ribeiro", "Dra. Camila Duarte", dia, 9, 30)
        assertEquals(1, agenda.atendimentos.size)
        assertEquals("09:30", agenda.atendimentos.first().fim)
    }

    @Test
    fun naoDeixaMarcarNoMesmoHorario() {
        val agenda = Agenda().marcar("Ananda Ribeiro", "Dra. Camila Duarte", dia, 9, 30)
        assertFailsWith<HorarioOcupadoException> {
            agenda.marcar("Vítor Almeida", "Dra. Camila Duarte", dia, 9, 30)
        }
    }

    @Test
    fun naoDeixaPassarDas18h() {
        assertFailsWith<IllegalArgumentException> {
            Agenda().marcar("Ananda Ribeiro", "Dra. Camila Duarte", dia, 17, 90)
        }
    }

    @Test
    fun recusaDataErrada() {
        assertNull(Data.parse("2026-02-30"))
        assertNull(Data.parse("05/10/2026"))
        assertEquals(Data(2028, 2, 29), Data.parse("2028-02-29"))
    }

    @Test
    fun passaDeAno() {
        assertEquals(Data(2027, 1, 1), Data(2026, 12, 31).proximoDia())
        assertEquals(Data(2026, 12, 31), Data(2027, 1, 1).diaAnterior())
    }
}
