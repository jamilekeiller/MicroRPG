/*
 * As Crônicas da Terra-média - Micro-RPG em Java
 * Autoria: Jamile Keiller
 * Disciplina: Coding: Linguagens e Técnicas - Semestre 2026.2
 * Professor: Fábio Chicout - Faculdade Senac Recife
 * Baseado no "Guia Prático de Construção de Software: Micro-RPG" (Meet 10)
 * Inspirado na obra de J.R.R. Tolkien (projeto acadêmico, sem fins comerciais)
 */

package edu.pe.senac.br.rpg;

// O jogador: guarda vida, mana, força e a mochila de itens.
public class Heroi {

    // Atributos privados: só a própria classe pode mexer neles (encapsulamento).
    // "final" = o valor é definido no construtor e nunca mais muda.
    private final String nome;
    private final String classeHeroi;
    private final int vidaMaxima;
    private final int manaMaxima;
    private final int forcaAtaque;
    private final Item[] mochila = new Item[5]; // 5 slots, todos começam vazios (null)

    // Estes mudam durante a batalha, por isso NÃO são final.
    private int vidaAtual;
    private int manaAtual;

    // Construtor: cria o herói com vida e mana cheias.
    public Heroi(String nome, String classeHeroi, int vidaMaxima, int manaMaxima, int forcaAtaque) {
        this.nome = nome;
        this.classeHeroi = classeHeroi;
        this.vidaMaxima = vidaMaxima;
        this.manaMaxima = manaMaxima;
        this.forcaAtaque = forcaAtaque;
        this.vidaAtual = vidaMaxima;
        this.manaAtual = manaMaxima;
    }

    // ---------- VIDA ----------

    public void receberDano(int dano) {
        if (dano < 0) {
            dano = 0; // dano negativo não pode virar cura
        }
        vidaAtual = Math.max(0, vidaAtual - dano); // a vida nunca fica abaixo de 0
        System.out.printf("   %s sofreu %d de dano! (HP: %d/%d)%n", nome, dano, vidaAtual, vidaMaxima);
    }

    public void curarVida(int quantidade) {
        if (quantidade <= 0) {
            return;
        }
        int vidaAntes = vidaAtual;
        vidaAtual = Math.min(vidaMaxima, vidaAtual + quantidade); // a vida nunca passa do máximo
        int curaReal = vidaAtual - vidaAntes;
        System.out.printf("   %s recuperou %d de vida! (HP: %d/%d)%n", nome, curaReal, vidaAtual, vidaMaxima);
    }

    public boolean isVivo() {
        return vidaAtual > 0;
    }

    // ---------- MANA ----------

    public void restaurarMana(int quantidade) {
        if (quantidade <= 0) {
            return;
        }
        manaAtual = Math.min(manaMaxima, manaAtual + quantidade);
        System.out.printf("   %s recuperou %d de mana! (MP: %d/%d)%n", nome, quantidade, manaAtual, manaMaxima);
    }

    // Devolve true se tinha mana suficiente (e gasta), ou false se não tinha.
    public boolean gastarMana(int custo) {
        if (manaAtual < custo) {
            System.out.println("   Mana insuficiente para esta habilidade!");
            return false;
        }
        manaAtual = manaAtual - custo;
        return true;
    }

    // ---------- ATAQUES ----------

    public void atacarFisico(Inimigo inimigo) {
        System.out.printf("   %s ataca com a arma!%n", nome);
        inimigo.receberDano(forcaAtaque, "FISICO");
    }

    // Custa 15 de mana. Dano = força x 1.6, elemento FOGO.
    public boolean usarGolpeFogo(Inimigo inimigo) {
        if (!gastarMana(15)) {
            return false;
        }
        int dano = (int) (forcaAtaque * 1.6);
        System.out.printf("   %s conjura: BOLA DE FOGO!%n", nome);
        inimigo.receberDano(dano, "FOGO");
        inimigo.aplicarQueimadura(); // desafio extra: o fogo deixa o monstro em chamas
        return true;
    }

    // Custa 20 de mana. Dano = força x 1.8, elemento GELO.
    public boolean usarLancaGelo(Inimigo inimigo) {
        if (!gastarMana(20)) {
            return false;
        }
        int dano = (int) (forcaAtaque * 1.8);
        System.out.printf("   %s conjura: LANÇA DE GELO!%n", nome);
        inimigo.receberDano(dano, "GELO");
        return true;
    }

    // ---------- MOCHILA ----------

    // Guarda o item no primeiro slot vazio que encontrar.
    public boolean adicionarItem(Item item) {
        for (int i = 0; i < mochila.length; i++) {
            if (mochila[i] == null) {
                mochila[i] = item;
                System.out.printf("   '%s' guardado no slot [%d].%n", item.getNome(), i + 1);
                return true;
            }
        }
        System.out.println("   Mochila cheia!");
        return false;
    }

    public void exibirMochila() {
        System.out.println("\n--- MOCHILA ---");
        for (int i = 0; i < mochila.length; i++) {
            if (mochila[i] == null) {
                System.out.printf("[%d] (vazio)%n", i + 1);
            } else {
                System.out.printf("[%d] %s (%s +%d)%n", i + 1, mochila[i].getNome(), mochila[i].getTipo(), mochila[i].getPoderEfeito());
            }
        }
        System.out.println("---------------");
    }

    // Usa o item do slot (0 a 4) e esvazia o slot.
    public boolean usarItem(int slot) {
        if (slot < 0 || slot >= mochila.length) {
            System.out.println("   Slot inválido!");
            return false;
        }
        if (mochila[slot] == null) { // checagem de null: evita NullPointerException
            System.out.println("   Não há nenhum item neste slot!");
            return false;
        }
        Item item = mochila[slot];
        System.out.printf("   %s usou '%s'!%n", nome, item.getNome());
        item.aplicar(this);
        mochila[slot] = null; // o item foi consumido
        return true;
    }

    // ---------- TELA ----------

    public void exibirStatus() {
        System.out.println("\n" + "=".repeat(40));
        System.out.printf("   HERÓI: %s | Classe: %s%n", nome, classeHeroi);
        System.out.printf("   HP: [%s] %d/%d - %s%n", barra(vidaAtual, vidaMaxima), vidaAtual, vidaMaxima, statusVida());
        System.out.printf("   MP: [%s] %d/%d%n", barra(manaAtual, manaMaxima), manaAtual, manaMaxima);
        System.out.printf("   Força: %d%n", forcaAtaque);
        System.out.println("=".repeat(40));
    }

    // Classifica a vida pela porcentagem (ideia do exercicio14).
    private String statusVida() {
        double porcentagem = (double) vidaAtual / vidaMaxima * 100;
        if (porcentagem >= 70) {
            return "SAUDÁVEL";
        } else if (porcentagem >= 30) {
            return "ALERTA";
        } else {
            return "CRÍTICO (procure cura!)";
        }
    }

    // Monta uma barra de 10 blocos, ex: ██████░░░░
    private String barra(int atual, int maximo) {
        int cheios = (int) Math.round((double) atual / maximo * 10);
        return "█".repeat(cheios) + "░".repeat(10 - cheios);
    }

    // ---------- GETTERS (só leitura, sem setters) ----------

    public String getNome() {
        return nome;
    }

    public int getVidaAtual() {
        return vidaAtual;
    }
}
