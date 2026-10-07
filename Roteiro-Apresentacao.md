# Roteiro de Apresentação — As Crônicas da Terra-média

**Autoria:** Jamile Keiller
**Disciplina:** Coding: Linguagens e Técnicas — Semestre 2026.2
**Professor:** Fábio Chicout — Faculdade Senac Recife

Tempo sugerido: cerca de 10 minutos.

---

## 1. Abertura (30 segundos)

> "Meu projeto é o Micro-RPG do Meet 10: um jogo de batalha por turnos no terminal, feito em Java 21 com Programação Orientada a Objetos. Eu mantive toda a estrutura do guia, mas mudei o tema para a Terra-média, de Tolkien: o herói enfrenta o dragão Smaug na Montanha Solitária. Cada parte do jogo é uma classe com a sua própria responsabilidade."

---

## 2. Demonstração do jogo (2 minutos)

Dois cliques no `jogar.bat`. Ele compila com a regra do professor (`-Xlint:all -Werror`) e abre o jogo.

Sequência sugerida para mostrar tudo:

1. Digitar um nome e escolher o **Mago Istari** (opção 2).
2. **Turno 1:** Magias → **Bola de Fogo**. Mostrar que o Smaug **resiste** ("Dano x0,5") e que ele é **IMUNE à queimadura**, porque é um dragão de fogo.
3. **Turno 2:** Magias → **Lança de Gelo**. Mostrar o **"FRAQUEZA EXPLORADA! Dano x1,8"**.
4. Apontar o **status da vida** do herói mudando (SAUDÁVEL → ALERTA) conforme ele toma dano.
5. Digitar uma **letra** no menu e mostrar que o jogo não trava ("Entrada inválida").
6. Abrir a **mochila**, escolher o **slot 5 (vazio)** e mostrar que não trava. Depois usar o **Miruvor** para recuperar mana.
7. Usar **Defender** (opção 4) e mostrar o dano caindo pela metade.
8. Continuar com a Lança de Gelo até a **vitória**.

---

## 3. Explicação do código (5 minutos)

Seguir a ordem em que o jogo acontece:

### `Main.java` — o começo de tudo
- Lê o nome com `Scanner` e usa `.trim()`. Se ficar vazio, vira "Guardião Anônimo".
- Usa um `switch` com `->` para criar o herói conforme a classe escolhida (Guerreiro Anão, Mago Istari ou Hobbit Ladrão).
- Coloca 3 itens na mochila (Pão de Lembas, Folhas de Athelas e Miruvor), cria o Smaug e chama `batalha.iniciar()`.
- O `try (...)` fecha o `Scanner` sozinho no final.

### `Heroi.java` — o jogador
- **Encapsulamento:** todos os atributos são `private`. Ninguém de fora consegue fazer `vida = -500`.
- **`final`:** os atributos que nunca mudam (nome, vida máxima, força). Vida e mana atuais não são `final`, porque mudam na batalha.
- **Construtor com `this`:** diferencia o atributo (`this.nome`) do parâmetro (`nome`).
- **Regras de vida:**
  - `Math.max(0, ...)` faz a vida nunca ficar negativa;
  - `Math.min(vidaMaxima, ...)` faz a cura nunca passar do máximo.
- **Mochila:** `Item[] mochila = new Item[5]`. Antes de usar um item, o código verifica se ele é `null` para evitar o `NullPointerException`.
- **Só getters, nenhum setter:** o estado só muda pelos métodos com regras (`receberDano`, `curarVida`).

### `Inimigo.java` — o monstro
- **`Random`:** o dano é sorteado entre o mínimo e o máximo, com `nextInt((max - min) + 1) + min`.
- **Fraquezas com `switch`:** o Smaug é de FOGO, então recebe ×1.8 de GELO e só ×0.5 de FOGO.
- **Queimadura:** o monstro perde 5 de vida por turno durante 3 turnos. Um `if` verifica o elemento: monstros de FOGO são imunes.

### `Item.java` — os itens (Lembas, Athelas, Miruvor)
- O `aplicar(heroi)` usa um `switch`: `CURA_HP` cura a vida e `CURA_MP` cura a mana.
- Mostra **objetos colaborando**: o item chama um método do herói.

### `Batalha.java` — o árbitro
- **Laço principal:** `while (heroi.isVivo() && inimigo.isVivo())`.
- **Turnos:** primeiro o herói, depois verifica a vitória, depois o monstro, depois verifica a derrota.
- **Menu que não perde o turno:** se o jogador volta do submenu ou não tem mana, o menu aparece de novo, com `while (!acaoConcluida)`.
- **`lerInteiro()`:** usa `hasNextInt()` para recusar letras e `nextLine()` para limpar o buffer.

---

## 4. O que adicionei além do básico (1 minuto)

| Novidade | De onde veio |
|---|---|
| **Status de vida** (SAUDÁVEL / ALERTA / CRÍTICO) | Lógica do meu exercício 3.4 (`if / else if`), transformada no método `statusVida()` |
| **Queimadura**: o fogo causa 5 de dano por turno durante 3 turnos | Desafio extra nº 3 do PDF, usando a lógica de dano contínuo do meu exercício 4.3. Monstros de FOGO, como o Smaug, são imunes |
| **Tema da Terra-média** | Troquei só os textos (nomes, classes, itens e o chefe). As regras e a estrutura são as do guia |
| **Código mais simples** | `switch` com `->`, `if` sempre com chaves e comentários em cada parte |

**Atenção ao checklist do PDF:** o guia testa "Golpe de Fogo na Gárgula de Gelo = ×2.0". No meu jogo, o teste equivalente é **"Lança de Gelo no Smaug (FOGO) = ×1.8"**. A regra de fraquezas é a mesma do guia, só o chefe mudou.

---

## 5. Dificuldades que tive (1 minuto)

*(Ajuste com as suas palavras. Fale do que foi verdade para você.)*

1. **Começar do zero em Java.** No início eu não entendia o formato dos exercícios ("Objetivo" e "Saída Esperada"). Aprendi a rodar o programa e comparar a saída com a do PDF.
2. **Configurar o ambiente.** Tive que instalar o Java 21 e a extensão do VS Code. Também entendi que o nome do arquivo precisa ser igual ao da classe.
3. **Símbolos aparecendo como `??????`.** A barra de vida (`█░`) saía errada. Descobri que era a codificação do terminal e configurei o UTF-8.
4. **Diferenciar erro de aviso.** O VS Code mostrava muitas linhas amarelas e eu achava que eram erros. Aprendi que eram só dicas (*hints*) e que o programa rodava normalmente.
5. **`==` vs `.equals()`.** Entendi na prática que `==` compara se é o mesmo objeto na memória, e não o texto. Por isso, para String, sempre uso `.equals()`.
6. **O botão Run não deixava digitar.** O jogo precisa ler o teclado, e o Debug Console não aceitava. A solução foi rodar pelo terminal, com o `jogar.bat`.
7. **Arquivos `.class`.** Entendi que são o código compilado, e passei a guardá-los na pasta `bin`.

**Ferramentas que usei:** *(se você usou ajuda de IA ou de colegas para entender o PDF, configurar o ambiente ou revisar o código, vale citar aqui. Ser transparente costuma contar a favor.)*

---

## 6. Perguntas que o professor pode fazer

**Por que os atributos são `private`?**
Para proteger o estado do objeto. A vida só muda pelos métodos com regras, então nunca fica negativa nem passa do máximo.

**O que acontece se eu tirar o `if (mochila[slot] == null)`?**
Usar um slot vazio travaria o jogo com `NullPointerException`.

**Por que `(double) vidaAtual / vidaMaxima`?**
Porque inteiro dividido por inteiro corta as casas decimais: 45 / 120 daria 0.

**Para que serve o `leitor.nextLine()` depois do `nextInt()`?**
O `nextInt()` deixa o Enter "sobrando" no buffer. O `nextLine()` limpa esse Enter, senão a próxima leitura de texto viria vazia.

**Por que o turno não acaba quando falta mana?**
O `gastarMana()` devolve `false`. A magia então devolve `false`, o `acaoConcluida` continua falso e o menu aparece de novo.

**Por que o `Scanner` é passado para a `Batalha` em vez de ela criar outro?**
Porque fechar um `Scanner(System.in)` fecha o teclado para o programa inteiro. Por isso existe um único `Scanner`, criado no `Main` e compartilhado.

**Por que a Bola de Fogo não queima o Smaug?**
No método `aplicarQueimadura()`, um `if (elemento.equals("FOGO"))` sai do método antes de ligar a queimadura. Um dragão de fogo não pega fogo.

**Qual a diferença entre o `switch` com `->` e o com `:`?**
O `switch` com `:` precisa de `break` em cada caso. Sem o `break`, ele continua executando os casos de baixo (*fall-through*). O `switch` com `->` não tem esse problema.

---

## 7. Antes de apresentar

- [ ] Rodar o `jogar.bat` uma vez para confirmar que compila e abre.
- [ ] Ler cada arquivo `.java` com calma, começando pelo `Main.java`.
- [ ] Treinar a sequência da demonstração (seção 2).
- [ ] Revisar as respostas da seção 6 **com as suas palavras**.
