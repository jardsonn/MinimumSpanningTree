package com.jalloft.minimumspanningtree.model;


import java.util.ArrayList;
import java.util.List;

public class Grafo {

    private final List<Vertice> vertices;

    private final List<Aresta> arestas;
    public Grafo() {
        this.vertices = new ArrayList<>();
        this.arestas = new ArrayList<>();
    }

    public void adicionarVertice(Vertice vertice) {
        vertices.add(vertice);
    }

    public void adicionarAresta(Vertice origem, Vertice destino, int peso) {
        Aresta aresta = new Aresta(origem, destino, peso);
        arestas.add(aresta);
    }

    public List<Vertice> getVertices() {
        return vertices;
    }

    public List<Aresta> getArestas() {
        return arestas;
    }
}
