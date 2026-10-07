/*
 * As Crônicas da Terra-média - Micro-RPG em Java
 * Autoria: Jamile Keiller
 * Disciplina: Coding: Linguagens e Técnicas - Semestre 2026.2
 * Professor: Fábio Chicout - Faculdade Senac Recife
 * Baseado no "Guia Prático de Construção de Software: Micro-RPG" (Meet 10)
 * Inspirado na obra de J.R.R. Tolkien (projeto acadêmico, sem fins comerciais)
 */

package edu.pe.senac.br.rpg;

import java.util.Random;

// O monstro: tem um elemento (FOGO, GELO ou TREVAS) e ataca com dano aleatório.
public class Inimigo {

    private final String nome;
    private final String elemento;
    private final int vidaMaxima;
    private final int ataqueMin;
    private final int ataqueMax;
    private final Random aleatorio = new Random();

    private int vidaAtual;
    private int turnosQueimando = 0; // 0 = não está em chamas

    public Inimigo(String nome, String elemento, int vidaMaxima, int ataqueMin, int ataqueMax) {
        this.nome = nome;
        this.elemento = elemento.toUpperCase();
        this.vidaMaxima = vidaMaxima;
        this.ataqueMin = ataqueMin;
        this.ataqueMax = ataqueMax;
        this.vidaAtual = vidaMaxima;
    }

    // Aplica o dano levando em conta as fraquezas do elemento. Devolve o dano final.
    public int receberDano(int danoBase, String elementoAtaque) {
        double multiplicador = 1.0;

        switch (elemento) {
            case "FOGO" -> {
                if (elementoAtaque.equals("GELO")) {
                    multiplicador = 1.8; // fraco contra gelo
                } else if (elementoAtaque.equals("FOGO")) {
                    multiplicador = 0.5; // resiste ao fogo
                }
            }
            case "GELO" -> {
                if (elementoAtaque.equals("FOGO")) {
                    multiplicador = 2.0; // fraco contra fogo
                } else if (elementoAtaque.equals("GELO")) {
                    multiplicador = 0.4; // resiste ao gelo
                }
            }
            case "TREVAS" -> {
                if (elementoAtaque.equals("SAGRADO")) {
                    multiplicador = 2.2; // fraco contra sagrado
                }
            }
            default -> multiplicador = 1.0;
        }

        int danoFinal = (int) Math.round(danoBase * multiplicador);
        vidaAtual = Math.max(0, vidaAtual - danoFinal);

        if (multiplicador > 1.0) {
            System.out.printf("   FRAQUEZA EXPLORADA! Dano x%.1f!%n", multiplicador);
        } else if (multiplicador < 1.0) {
            System.out.printf("   O inimigo resistiu! Dano x%.1f%n", multiplicador);
        }
        System.out.printf("   %s recebeu %d de dano! (HP: %d/%d)%n", nome, danoFinal, vidaAtual, vidaMaxima);
        return danoFinal;
    }

    // ---------- QUEIMADURA (desafio extra 3 do PDF) ----------

    // Deixa o monstro em chamas por 3 turnos (se já estiver, reinicia a contagem).
    public void aplicarQueimadura() {
        if (!isVivo()) {
            return;
        }
        if (elemento.equals("FOGO")) {
            System.out.printf("   %s é feito de chamas: é IMUNE à queimadura!%n", nome);
            return;
        }
        turnosQueimando = 3;
        System.out.printf("   %s está EM CHAMAS por 3 turnos!%n", nome);
    }

    // Chamado no início de cada turno: tira 5 de vida enquanto estiver queimando.
    // Mesma ideia do veneno do exercicio18.
    public void sofrerQueimadura() {
        if (turnosQueimando == 0) {
            return;
        }
        vidaAtual = Math.max(0, vidaAtual - 5);
        turnosQueimando--;
        System.out.printf("   %s queima e perde 5 de vida! (HP: %d/%d)", nome, vidaAtual, vidaMaxima);
        if (turnosQueimando == 0) {
            System.out.println(" - as chamas se apagaram.");
        } else {
            System.out.printf(" - ainda queima por %d turno(s).%n", turnosQueimando);
        }
    }

    // Sorteia um dano entre ataqueMin e ataqueMax (incluindo os dois).
    public int calcularAtaque() {
        return aleatorio.nextInt((ataqueMax - ataqueMin) + 1) + ataqueMin;
    }

    public boolean isVivo() {
        return vidaAtual > 0;
    }

    public void exibirStatus() {
        System.out.printf("   INIMIGO: %s [%s] | HP: %d/%d", nome, elemento, vidaAtual, vidaMaxima);
        if (turnosQueimando > 0) {
            System.out.printf(" | EM CHAMAS (%d turno(s))", turnosQueimando);
        }
        System.out.println();
    }

    public String getNome() {
        return nome;
    }
}
