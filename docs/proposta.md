# Proposta — AgendaFácil

DIM0524 — Sistemas para Dispositivos Móveis · UFRN · 2026.2

## 1. Visão do produto

```
Para       recepcionistas de clínicas pequenas
Que        anotam a agenda no caderno ou no WhatsApp e só descobrem que marcaram
           dois pacientes no mesmo horário quando os dois chegam
O          AgendaFácil é um app de agenda de atendimentos
Que        mostra os atendimentos do dia e não deixa marcar horário ocupado
           ou fora do expediente
Diferente  do Google Agenda e de planilhas, que aceitam qualquer horário
Nosso      produto já vem com as regras da clínica: expediente das 8h às 18h,
produto    duração de 15 a 120 minutos e um paciente por vez para cada profissional
```

## 2. MVP

Como a equipe tem só uma pessoa, o MVP é pequeno: três telas.

| Entra no MVP | Fica de fora |
|---|---|
| Ver os atendimentos do dia e trocar de dia | Remarcar e cancelar |
| Marcar atendimento, com os campos validados | Login |
| Avisar quando o horário já está ocupado | Lembrete por WhatsApp |
| Ver os dados de um atendimento | Filtro por profissional |
| Guardar a agenda no celular | Usar em vários celulares ao mesmo tempo |

**Hipótese de valor:** acreditamos que recepcionistas vão usar o AgendaFácil para marcar os
atendimentos porque ele avisa na hora quando o horário está ocupado.

## 3. Backlog

Quadro no GitHub Projects: *link aqui*

| Prioridade | Pontos | História |
|---|---|---|
| P1 | 3 | Como recepcionista, quero ver os atendimentos de um dia para saber quem vem |
| P1 | 5 | Como recepcionista, quero marcar um atendimento para reservar o horário do paciente |
| P1 | 2 | Como recepcionista, quero ver os dados de um atendimento para confirmar com o paciente |
| P2 | 3 | Como recepcionista, quero que a agenda continue salva quando eu fechar o app |
| P3 | 3 | Como recepcionista, quero remarcar um atendimento quando o paciente pedir |

## 4. Plataforma: Android

- A maioria das recepcionistas usa celular Android, que é o mais comum no Brasil.
- Muitas clínicas usam um tablet Android barato no balcão, por isso o app também funciona em tela larga.
- O app pode ser instalado direto pelo APK, sem pagar loja.
- Dá para desenvolver no Windows. Para iOS seria preciso um Mac.

**Descartado:** iOS (menos usado por esse público e precisa de Mac) e site (o link direto para um
atendimento e o uso sem internet ficam mais simples num app).

## 5. Backend: dados salvos no próprio celular

- A clínica pequena marca tudo de um aparelho só, então não precisa de servidor.
- Funciona sem internet, e a recepção não para quando a internet cai.
- Os dados dos pacientes ficam na clínica, sem ir para um serviço de fora.
- Não tem custo.

**Descartado:** Firebase e Supabase (só fariam sentido com vários aparelhos ao mesmo tempo, o que
está fora do MVP) e API de Web II (não curso essa disciplina).

## 6. Equipe

| Nome | Matrícula | Papel |
|---|---|---|
| Vítor Augusto Burratto Dias | 20240062444 | Tudo (equipe de uma pessoa) |

## 7. Apresentação e integração

- Apresentação online, pelo Google Meet.
- Sem integração com outra disciplina.
