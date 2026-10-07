/*
 * As Crônicas da Terra-média - Micro-RPG em Java
 * Autoria: Jamile Keiller
 * Disciplina: Coding: Linguagens e Técnicas - Semestre 2026.2
 * Professor: Fábio Chicout - Faculdade Senac Recife
 * Baseado no "Guia Prático de Construção de Software: Micro-RPG" (Meet 10)
 * Inspirado na obra de J.R.R. Tolkien (projeto acadêmico, sem fins comerciais)
 */

package edu.pe.senac.br.rpg;

import java.util.Scanner;

// O árbitro: controla os turnos, mostra os menus e lê o teclado.
public class Batalha {

    private final Heroi heroi;
    private final Inimigo inimigo;
    private final Scanner leitor;

    private int turno = 1;
    private boolean heroiEmGuarda = false;

    public Batalha(Heroi heroi, Inimigo inimigo, Scanner leitor) {
        this.heroi = heroi;
        this.inimigo = inimigo;
        this.leitor = leitor;
    }

    // Laço principal: repete os turnos enquanto os dois estiverem vivos.
    public void iniciar() {
        System.out.printf("%n=== A BATALHA COMEÇOU: %s VS %s! ===%n", heroi.getNome(), inimigo.getNome());

        while (heroi.isVivo() && inimigo.isVivo()) {
            System.out.printf("%n----- TURNO %d -----%n", turno);

            // Queimadura: o monstro perde vida no começo do turno, se estiver em chamas.
            inimigo.sofrerQueimadura();
            if (!inimigo.isVivo()) {
                mostrarVitoria();
                break;
            }

            heroi.exibirStatus();
            inimigo.exibirStatus();

            executarTurnoHeroi();
            if (!inimigo.isVivo()) {
                mostrarVitoria();
                break;
            }

            executarTurnoInimigo();
            if (!heroi.isVivo()) {
                System.out.printf("%n*** DERROTA... %s foi vencido por %s. ***%n", heroi.getNome(), inimigo.getNome());
                break;
            }

            turno++;
        }
    }

    private void mostrarVitoria() {
        System.out.printf("%n*** VITÓRIA! %s derrotou %s! ***%n", heroi.getNome(), inimigo.getNome());
    }

    // Repete o menu até o jogador fazer uma ação válida.
    private void executarTurnoHeroi() {
        heroiEmGuarda = false;
        boolean acaoConcluida = false;

        while (!acaoConcluida) {
            System.out.println("\nEscolha sua ação:");
            System.out.println("1. Ataque básico");
            System.out.println("2. Magias (gasta MP)");
            System.out.println("3. Abrir mochila");
            System.out.println("4. Defender (reduz 50% do próximo dano)");
            System.out.print("Opção: ");

            int opcao = lerInteiro();

            switch (opcao) {
                case 1 -> {
                    heroi.atacarFisico(inimigo);
                    acaoConcluida = true;
                }
                case 2 -> acaoConcluida = menuHabilidades();
                case 3 -> acaoConcluida = menuInventario();
                case 4 -> {
                    heroiEmGuarda = true;
                    System.out.printf("   %s se defende!%n", heroi.getNome());
                    acaoConcluida = true;
                }
                default -> System.out.println("   Opção inválida! Escolha de 1 a 4.");
            }
        }
    }

    // Devolve true se uma magia foi usada (o turno acabou).
    private boolean menuHabilidades() {
        System.out.println("\n--- MAGIAS ---");
        System.out.println("1. Bola de Fogo (15 MP)");
        System.out.println("2. Lança de Gelo (20 MP)");
        System.out.println("0. Voltar");
        System.out.print("Escolha: ");

        int opcao = lerInteiro();
        boolean usou = false;

        switch (opcao) {
            case 1 -> usou = heroi.usarGolpeFogo(inimigo);
            case 2 -> usou = heroi.usarLancaGelo(inimigo);
            case 0 -> System.out.println("   Voltando...");
            default -> System.out.println("   Magia inválida.");
        }
        return usou;
    }

    // Devolve true se um item foi usado (o turno acabou).
    private boolean menuInventario() {
        heroi.exibirMochila();
        System.out.print("Número do slot (1 a 5) ou 0 para voltar: ");

        int slot = lerInteiro();
        if (slot == 0) {
            return false;
        }
        return heroi.usarItem(slot - 1); // o jogador digita 1-5, o array usa 0-4
    }

    private void executarTurnoInimigo() {
        System.out.printf("%n   Vez de %s...%n", inimigo.getNome());
        int dano = inimigo.calcularAtaque();

        if (heroiEmGuarda) {
            dano = dano / 2;
            System.out.println("   A defesa absorveu metade do dano!");
        }
        heroi.receberDano(dano);
    }

    // Só aceita números: se digitar texto, pede de novo.
    private int lerInteiro() {
        while (!leitor.hasNextInt()) {
            System.out.print("Entrada inválida! Digite um número: ");
            leitor.next(); // descarta o que foi digitado errado
        }
        int valor = leitor.nextInt();
        leitor.nextLine(); // limpa o Enter que sobrou
        return valor;
    }
}
