# As Crônicas da Terra-média — Micro-RPG em Java

Jogo de batalha por turnos no terminal, feito em **Java 21** com **Programação Orientada a Objetos**. O herói enfrenta **Smaug, o Dourado**, o dragão de fogo da Montanha Solitária.

Projeto acadêmico da disciplina **Coding: Linguagens e Técnicas** (Semestre 2026.2), com o professor Fábio Chicout, na Faculdade Senac Recife. Foi baseado no *Guia Prático de Construção de Software: Micro-RPG* (Meet 10).

> Inspirado na obra de J.R.R. Tolkien. Projeto acadêmico, sem fins comerciais.

## Como jogar

### Requisitos

- [Java 21](https://learn.microsoft.com/java/openjdk/download) (JDK)
- [Maven](https://maven.apache.org/) (opcional: sem ele, o jogo compila direto com `javac`)

### Windows

Dê dois cliques no **`jogar.bat`**. Ele compila o projeto e abre o jogo no terminal.

### Pelo terminal (com Maven)

```bash
mvn clean compile
mvn exec:java
```

### Pelo terminal (sem Maven)

```bash
javac -Xlint:all -Werror -encoding UTF-8 -d target/classes src/main/java/edu/pe/senac/br/rpg/*.java
java -Dstdout.encoding=UTF-8 -cp target/classes edu.pe.senac.br.rpg.Main
```

> O jogo lê o teclado, então rode pelo terminal. O botão *Run* do VS Code abre o Debug Console, que não aceita digitação.

## O jogo

### Classes de herói

| Classe | HP | MP | Força |
|---|---|---|---|
| Guerreiro Anão | 130 | 30 | 20 |
| Mago Istari | 90 | 70 | 14 |
| Hobbit Ladrão | 105 | 45 | 18 |

### Ações em cada turno

1. **Ataque básico:** dano igual à força do herói.
2. **Magias** (gastam MP):
   - **Bola de Fogo** (15 MP): força × 1,6, e deixa o inimigo em chamas.
   - **Lança de Gelo** (20 MP): força × 1,8.
3. **Abrir mochila:** usa um item.
4. **Defender:** reduz pela metade o próximo dano recebido.

### Itens da mochila

| Item | Efeito |
|---|---|
| Pão de Lembas | Recupera 40 de HP |
| Folhas de Athelas | Recupera 80 de HP |
| Miruvor | Recupera 35 de MP |

### Fraquezas elementais

Smaug é de **FOGO**:
- **Gelo** causa dano × 1,8 (*fraqueza explorada!*);
- **Fogo** causa só dano × 0,5, e ele é **imune à queimadura**.

O código também tem regras para inimigos de **GELO** (fraco contra fogo) e de **TREVAS** (fraco contra sagrado).

### Extras

- **Status de vida:** SAUDÁVEL (70% ou mais), ALERTA (30% ou mais) ou CRÍTICO, com barras de HP e MP (`██████░░░░`).
- **Queimadura:** a Bola de Fogo tira 5 de vida por turno, durante 3 turnos, de inimigos que não são de fogo.
- **Entrada segura:** se você digitar letras no lugar de números ou escolher um slot vazio da mochila, o jogo não trava.
- **Menu sem perder o turno:** se faltar mana ou você voltar de um submenu, o menu aparece de novo.

## Estrutura do código

```
src/main/java/edu/pe/senac/br/rpg/
├── Main.java      # Ponto de entrada: cria o herói, a mochila e o chefe
├── Heroi.java     # O jogador: vida, mana, ataques, magias e mochila
├── Inimigo.java   # O monstro: elemento, fraquezas, queimadura e dano aleatório
├── Item.java      # Itens que curam vida (CURA_HP) ou mana (CURA_MP)
└── Batalha.java   # O árbitro: turnos, menus e leitura do teclado
```

### Conceitos de POO aplicados

- **Encapsulamento:** todos os atributos são `private`, e o estado só muda por métodos com regras. Por exemplo, a vida nunca fica negativa nem passa do máximo.
- **Imutabilidade com `final`:** nome, vida máxima e força não mudam depois de criados.
- **Objetos colaborando:** o `Item` age sobre o `Heroi`, e a `Batalha` coordena o herói e o inimigo.
- **Arrays e `null`:** a mochila é um `Item[5]`, e o código verifica se o slot está vazio antes de usar o item.
- **`switch` com `->`:** usado na escolha de classe, nos menus, nas fraquezas e nos itens.

O projeto compila com `-Xlint:all -Werror`, ou seja, qualquer aviso do compilador é tratado como erro.

## Autoria

Desenvolvido por **Jamile Keiller**, Faculdade Senac Recife, 2026.2.
