package com.jalloft.minimumspanningtree.model;

public record Aresta(Vertice origem, Vertice destino, int peso) implements Comparable<Aresta> {
    @Override
    public int compareTo(Aresta outra) {
        return this.peso - outra.peso;
    }
}
