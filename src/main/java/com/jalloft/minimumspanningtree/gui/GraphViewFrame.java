package com.jalloft.minimumspanningtree.gui;

import com.jalloft.minimumspanningtree.model.Grafo;
import com.jalloft.minimumspanningtree.util.UtilsUi;
import com.mxgraph.swing.mxGraphComponent;

import javax.swing.*;

import static com.jalloft.minimumspanningtree.GraphApp.WINDOW_HEIGHT;
import static com.jalloft.minimumspanningtree.GraphApp.WINDOW_WIDTH;

public class GraphViewFrame extends JFrame {


    public GraphViewFrame(Grafo grafo, boolean isShowedAllPath, int grafoFormato, JFrame parent) {
        setTitle("Árvore Geradora Mínima");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        UtilsUi.setPosition(this);
        mxGraphComponent graphComponent = new mxGraphComponent(new GraphComponent(grafo, grafoFormato, isShowedAllPath));
        getContentPane().add(graphComponent);
        JMenuBar menuBar = new JMenuBar();
        JMenuItem itemOpcao = new JMenuItem("Voltar");
        itemOpcao.addActionListener(e -> {
            parent.setVisible(true);
            setVisible(false);
        });
        menuBar.add(itemOpcao);
        setJMenuBar(menuBar);
    }
}