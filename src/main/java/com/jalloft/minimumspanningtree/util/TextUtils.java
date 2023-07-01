package com.jalloft.minimumspanningtree.util;

import com.jalloft.minimumspanningtree.model.StateField;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextUtils {


    public static StateField verificarEntrada(String vertice, String arestas) {
        try {

            if (vertice.isEmpty() || arestas.isEmpty()) {
                return StateField.EMPTY;
            }

            int numVertices = Integer.parseInt(vertice);
            int numArestas = Integer.parseInt(arestas);

            if (numArestas == 0 || numVertices == 0) {
                return StateField.ZERO;
            }

            if (numArestas < 0 || numVertices < 0) {
                return StateField.NEGATIVE;
            }

            int maxAresta = numVertices * (numVertices - 1) / 2;
            if (numArestas > maxAresta) {
                return StateField.EDGE_MAX;
            }

            return StateField.VALID;
        } catch (NumberFormatException e) {
            return StateField.INVALID;
        }
    }


    public static boolean isNumeric(String input) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }
}
