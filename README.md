# AgendaFácil

App de agenda para clínica pequena: mostra os atendimentos do dia, marca novos atendimentos e
avisa quando o horário já está ocupado.

Feito em Kotlin Multiplatform com Compose, para Android e desktop.

| Nome | Matrícula |
|---|---|
| Vítor Augusto Burratto Dias | 20240062444 |

- Vídeo Sprint 0: *link aqui*
- Vídeo Sprint 1: *link aqui*
- [Proposta](docs/proposta.md)
- [Uso de IA](docs/uso-de-ia.md)

## Como rodar

```sh
./gradlew :composeApp:run            # abre no computador
./gradlew :composeApp:installDebug   # instala no celular ou emulador
```

Antes de enviar:

```sh
./gradlew ktlintCheck detekt allTests
```

## Telas

- **Agenda:** atendimentos do dia, com botões para ir para o dia anterior e o próximo.
- **Atendimento:** os dados de um atendimento.
- **Marcar:** formulário que só libera o botão quando tudo está certo.

Com a janela larga (tablet, celular deitado ou computador), a agenda e o atendimento aparecem lado a lado.

## Link direto

`agendafacil://atendimento/2` abre o atendimento 2. Para testar no emulador:

```sh
adb shell am start -a android.intent.action.VIEW -d "agendafacil://atendimento/2"
```

## Acessibilidade

- Os títulos das telas são lidos como título pelo leitor de tela.
- Os botões ◀ ▶ ← têm nome: "Dia anterior", "Próximo dia" e "Voltar".
- Cada atendimento da lista é lido numa frase só.
- Os erros do formulário são lidos em voz alta.
- As cores vêm do tema, nos modos claro e escuro.

Teste com o TalkBack: *anotar aqui o que foi testado.*
