package com.jalloft.minimumspanningtree.algoritmo;

import com.jalloft.minimumspanningtree.model.Aresta;
import com.jalloft.minimumspanningtree.model.Vertice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Kruskal {

    private final List<Vertice> vertices;

    private final List<Aresta> arestas;

    public Kruskal(List<Vertice> vertices, List<Aresta> arestas){
        this.vertices = vertices;
        this.arestas = arestas;
    }

    private int encontrar(int vertice, int[] pais) {
        if (pais[vertice] == vertice) {
            return vertice;
        }
        return encontrar(pais[vertice], pais);
    }

    private void unir(int x, int y, int[] pais, int[] tamanho) {
        int raizX = encontrar(x, pais);
        int raizY = encontrar(y, pais);

        if (tamanho[raizX] < tamanho[raizY]) {
            pais[raizX] = raizY;
            tamanho[raizY] += tamanho[raizX];
        } else {
            pais[raizY] = raizX;
            tamanho[raizX] += tamanho[raizY];
        }
    }

    public List<Aresta> getArvoreGeradoraMinima() {
        List<Aresta> arvoreGeradoraMinima = new ArrayList<>();

        Collections.sort(arestas);

        int numVertices = vertices.size()+1;
        int[] pais = new int[numVertices];
        int[] tamanho = new int[numVertices];

        for (int i = 0; i < numVertices; i++) {
            pais[i] = i;
            tamanho[i] = 1;
        }

        for (Aresta aresta : arestas) {
            Vertice origem = aresta.origem();
            Vertice destino = aresta.destino();

            int raizOrigem = encontrar(origem.id()-1, pais);
            int raizDestino = encontrar(destino.id()-1, pais);

            if (raizOrigem != raizDestino) {
                arvoreGeradoraMinima.add(aresta);
                unir(raizOrigem, raizDestino, pais, tamanho);
            }
        }

        return arvoreGeradoraMinima;
    }

    public List<Aresta> getArestas() {
        return arestas;
    }
}
