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

// Ponto de entrada: cria o herói, a mochila, o monstro e começa a batalha.
public class Main {
    public static void main(String[] args) {

        // try (...) fecha o Scanner sozinho quando o jogo termina.
        try (Scanner leitor = new Scanner(System.in)) {

            System.out.println("==============================================");
            System.out.println("   AS CRÔNICAS DA TERRA-MÉDIA");
            System.out.println("   O Dragão da Montanha Solitária");
            System.out.println("   Desenvolvido por Jamile Keiller");
            System.out.println("==============================================");

            // 1. Nome do herói
            System.out.print("Digite o nome do seu herói: ");
            String nome = leitor.nextLine().trim();
            if (nome.isEmpty()) {
                nome = "Viajante da Terra-média";
            }

            // 2. Classe do herói
            System.out.println("\nEscolha sua classe:");
            System.out.println("1. Guerreiro Anão  (130 HP, 30 MP, Força 20)");
            System.out.println("2. Mago Istari     ( 90 HP, 70 MP, Força 14)");
            System.out.println("3. Hobbit Ladrão   (105 HP, 45 MP, Força 18)");
            System.out.print("Opção: ");

            int opcao = 1; // se digitar algo inválido, vira Guerreiro Anão
            if (leitor.hasNextInt()) {
                opcao = leitor.nextInt();
            }
            leitor.nextLine(); // limpa o Enter que sobrou

            Heroi heroi = switch (opcao) {
                case 2 -> new Heroi(nome, "Mago Istari", 90, 70, 14);
                case 3 -> new Heroi(nome, "Hobbit Ladrão", 105, 45, 18);
                default -> new Heroi(nome, "Guerreiro Anão", 130, 30, 20);
            };

            // 3. Mochila inicial
            System.out.println("\nGuardando suprimentos na mochila...");
            heroi.adicionarItem(new Item("Pão de Lembas", "CURA_HP", 40));
            heroi.adicionarItem(new Item("Folhas de Athelas", "CURA_HP", 80));
            heroi.adicionarItem(new Item("Miruvor", "CURA_MP", 35));

            // 4. O chefe final
            Inimigo chefe = new Inimigo("Smaug, o Dourado", "FOGO", 160, 12, 24);
            System.out.println("\nO ouro de Erebor estremece... Smaug desperta na Montanha Solitária!");
            System.out.println("DICA: Smaug é de FOGO. Magias de GELO causam dano extra!");
            System.out.println("      Mas cuidado: o fogo quase não o fere.");
            System.out.println("\nAperte ENTER para lutar...");
            leitor.nextLine();

            // 5. A batalha
            Batalha batalha = new Batalha(heroi, chefe, leitor);
            batalha.iniciar();

            System.out.println("\nObrigado por jogar!");
            System.out.println("Créditos: Jamile Keiller - Faculdade Senac Recife, 2026.2");
        }
    }
}
