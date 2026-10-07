/*
 * As Crônicas da Terra-média - Micro-RPG em Java
 * Autoria: Jamile Keiller
 * Disciplina: Coding: Linguagens e Técnicas - Semestre 2026.2
 * Professor: Fábio Chicout - Faculdade Senac Recife
 * Baseado no "Guia Prático de Construção de Software: Micro-RPG" (Meet 10)
 * Inspirado na obra de J.R.R. Tolkien (projeto acadêmico, sem fins comerciais)
 */

package edu.pe.senac.br.rpg;

// Uma poção que cura vida (CURA_HP) ou mana (CURA_MP).
public class Item {

    private final String nome;
    private final String tipo;
    private final int poderEfeito;

    public Item(String nome, String tipo, int poderEfeito) {
        this.nome = nome;
        this.tipo = tipo.toUpperCase();
        this.poderEfeito = poderEfeito;
    }

    // Aplica o efeito da poção no herói.
    public void aplicar(Heroi heroi) {
        switch (tipo) {
            case "CURA_HP" -> heroi.curarVida(poderEfeito);
            case "CURA_MP" -> heroi.restaurarMana(poderEfeito);
            default -> System.out.println("   Item com efeito desconhecido.");
        }
    }

    public String getNome() {
        return nome;
    }

    public String getTipo() {
        return tipo;
    }

    public int getPoderEfeito() {
        return poderEfeito;
    }
}
