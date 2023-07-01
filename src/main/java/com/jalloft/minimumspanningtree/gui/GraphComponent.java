package com.jalloft.minimumspanningtree.gui;

import com.jalloft.minimumspanningtree.algoritmo.Kruskal;
import com.jalloft.minimumspanningtree.model.Aresta;
import com.jalloft.minimumspanningtree.model.Grafo;
import com.jalloft.minimumspanningtree.model.Vertice;
import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.layout.mxFastOrganicLayout;
import com.mxgraph.layout.mxGraphLayout;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxEvent;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;

import java.util.List;
import java.util.Map;

import static com.jalloft.minimumspanningtree.GraphApp.WINDOW_WIDTH;


public class GraphComponent extends mxGraph {
    public GraphComponent(Grafo grafo, int grafoFormato, boolean isShowedAllPath) {

        Object parent = getDefaultParent();
        getModel().beginUpdate();

        try {
            configStyle();
            drawVertexAndEdges(parent, grafo, isShowedAllPath);
            mxGraphLayout layout;
            if (grafoFormato == GraphInputFrame.GRAPH_FORMAT_ORGANIC) {
                layout = new mxFastOrganicLayout(this);
            } else {
                layout = new mxCompactTreeLayout(this, false);
                ((mxCompactTreeLayout) layout).setEdgeRouting(false);
                ((mxCompactTreeLayout) layout).setMoveTree(false);
                ((mxCompactTreeLayout) layout).setLevelDistance(80);
                ((mxCompactTreeLayout) layout).setNodeDistance(25);
            }
            layout.execute(parent);

            setCellsDisconnectable(false);
            setCellsCloneable(false);

            addListener(mxEvent.ADD_CELLS, (sender, evt) -> {
                Object[] cells = (Object[]) evt.getProperty("cells");
                for (Object cell : cells) {
                    if (this.getModel().isEdge(cell)) {
                        this.getModel().remove(cell);
                    }
                }
            });

        } finally {
            this.getModel().endUpdate();
        }

    }


    private void drawVertexAndEdges(Object parent, Grafo grafo, boolean isShowedAllPath) {
        List<Vertice> verticesIniciais = grafo.getVertices();
        List<Aresta> arestasIniciais = grafo.getArestas();

        Kruskal kruskal = new Kruskal(verticesIniciais, arestasIniciais);

        List<Aresta> arvoreGeradoraMinima = kruskal.getArvoreGeradoraMinima();

        Object[] vertices = new Object[verticesIniciais.size()];
        for (int i = 0; i < verticesIniciais.size(); i++) {
            Vertice vertice = verticesIniciais.get(i);
            vertices[i] = insertVertex(parent, null, vertice.nome(), WINDOW_WIDTH / 2.0, 10, 50, 50);
        }

        for (Aresta aresta : arvoreGeradoraMinima) {
            Vertice origem = aresta.origem();
            Vertice destino = aresta.destino();
            int peso = aresta.peso();

            Object arestaVisual = this.insertEdge(parent, null, peso, vertices[origem.id() - 1], vertices[destino.id() - 1]);
            setCellStyles(mxConstants.STYLE_STROKECOLOR, "#00C000", new Object[]{arestaVisual});
            setCellStyles(mxConstants.STYLE_FONTCOLOR, "black", new Object[]{arestaVisual});
            setCellStyles(mxConstants.STYLE_STROKEWIDTH, "4", new Object[]{arestaVisual});
        }


        if (isShowedAllPath) {
            for (Aresta aresta : arestasIniciais) {
                if (arvoreGeradoraMinima.contains(aresta)) {
                    continue;
                }

                Vertice origem = aresta.origem();
                Vertice destino = aresta.destino();
                int peso = aresta.peso();

                Object arestaVisual = this.insertEdge(parent, null, peso, vertices[origem.id() - 1], vertices[destino.id() - 1]);
                setCellStyles(mxConstants.STYLE_STROKECOLOR, "#8B8B8B", new Object[]{arestaVisual});
                setCellStyles(mxConstants.STYLE_FONTCOLOR, "#000000", new Object[]{arestaVisual});
                setCellStyles(mxConstants.STYLE_STROKEWIDTH, "3", new Object[]{arestaVisual});

            }
        }
    }

    private void configStyle() {
        mxStylesheet stylesheet = getStylesheet();
        Map<String, Object> vertexStyle = stylesheet.getDefaultVertexStyle();
        vertexStyle.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_ELLIPSE);
        vertexStyle.put(mxConstants.STYLE_PERIMETER, mxConstants.PERIMETER_ELLIPSE);
        vertexStyle.put(mxConstants.STYLE_FILLCOLOR, "#00C000");
        vertexStyle.put(mxConstants.STYLE_STROKECOLOR, "#00C000");
        vertexStyle.put(mxConstants.STYLE_FONTCOLOR, "#000000");
        vertexStyle.put(mxConstants.STYLE_FONTSTYLE, mxConstants.FONT_BOLD);

        Map<String, Object> edgeStyle = stylesheet.getDefaultEdgeStyle();
        edgeStyle.put(mxConstants.STYLE_FONTSTYLE, mxConstants.FONT_BOLD);
        edgeStyle.put(mxConstants.STYLE_ENDARROW, mxConstants.NONE);
    }
}
