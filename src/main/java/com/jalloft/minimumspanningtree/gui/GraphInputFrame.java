package com.jalloft.minimumspanningtree.gui;

import com.jalloft.minimumspanningtree.model.*;
import com.jalloft.minimumspanningtree.util.TextUtils;
import com.jalloft.minimumspanningtree.util.UtilsUi;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.jalloft.minimumspanningtree.GraphApp.WINDOW_HEIGHT;
import static com.jalloft.minimumspanningtree.GraphApp.WINDOW_WIDTH;

public class GraphInputFrame extends JFrame {

    public StateField stateField = StateField.VALID;

    public Set<String> caminhos;

    public static final int GRAPH_FORMAT_ORGANIC = 0;
    public static final int GRAPH_FORMAT_TREE = 1;

    public GraphInputFrame(int numVertices, int numArestas, JFrame parent) {
        setTitle("Prova II - Grafos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        JMenuBar menuBar = new JMenuBar();

        JMenu menuOpcoes = new JMenu("Opções");
        JCheckBoxMenuItem itemOption1 = new JCheckBoxMenuItem("Mostrar todos caminhos");
        itemOption1.setState(true);
        JMenuItem itemOpcao2 = new JMenuItem("Voltar");

        JMenu estiloMenu = new JMenu("Formato do grafo");
        JRadioButtonMenuItem radioOrganico = new JRadioButtonMenuItem("Orgânico");
        JRadioButtonMenuItem radioArvore = new JRadioButtonMenuItem("Árvore");
        ButtonGroup buttonGroup = new ButtonGroup();
        radioArvore.setSelected(true);
        buttonGroup.add(radioArvore);
        buttonGroup.add(radioOrganico);
        estiloMenu.add(radioArvore);
        estiloMenu.add(radioOrganico);
        menuOpcoes.add(estiloMenu);


        itemOpcao2.addActionListener(e -> {
            if (parent != null) {
                parent.setVisible(true);
                setVisible(false);
            }
        });
        menuOpcoes.add(itemOption1);
        menuOpcoes.add(itemOpcao2);
        menuBar.add(menuOpcoes);
        setJMenuBar(menuBar);

        UtilsUi.setPosition(this);
        caminhos = new HashSet<>();
        Grafo grafo = new Grafo();

        for (int i = 1; i <= numVertices; i++) {
            Vertice vertice = new Vertice(i, String.valueOf(i));
            grafo.adicionarVertice(vertice);
        }

        JPanel painel = new JPanel();
        painel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        painel.add(new JLabel("Origem"), gbc);

        gbc.gridx = 1;
        painel.add(new JLabel("Destino"), gbc);

        gbc.gridx = 2;
        painel.add(new JLabel("Peso"), gbc);

        gbc.gridy = 1;
        List<CamposDeTexto> camposDeTextos = new ArrayList<>();
        for (int i = 0; i < numArestas; i++) {
            gbc.gridx = 0;
            JTextField origemField = new JTextField();
            origemField.setName("origem-" + i);
            origemField.setPreferredSize(new Dimension(100, 20));
            painel.add(origemField, gbc);

            gbc.gridx = 1;
            JTextField destinoField = new JTextField();
            origemField.setName("destino-" + i);
            destinoField.setPreferredSize(new Dimension(100, 20));
            painel.add(destinoField, gbc);

            gbc.gridx = 2;
            JTextField pesoField = new JTextField();
            origemField.setName("peso-" + i);
            pesoField.setPreferredSize(new Dimension(100, 20));
            painel.add(pesoField, gbc);

            gbc.gridy++;

            CamposDeTexto camposDeTexto = new CamposDeTexto(origemField, destinoField, pesoField);
            camposDeTextos.add(camposDeTexto);
        }
        JButton completeButton = new JButton("︽");
        JButton processButton = new JButton("Criar");
        gbc.gridx = 1;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridwidth = 1;
        painel.add(processButton, gbc);
        if (numArestas == 11 && numVertices == 7) {
            gbc.gridx = 2;
            gbc.gridwidth = 1;
            painel.add(completeButton, gbc);

            var valores = new int[][]{{1, 2, 7}, {1, 4, 5}, {2, 3, 8}, {2, 4, 9}, {2, 5, 7}, {3, 5, 5}, {4, 5, 15}, {4, 6, 6}, {5, 6, 8}, {5, 7, 9}, {6, 7, 11}};

            completeButton.addActionListener(e -> {
                for (int i = 0; i < camposDeTextos.size(); i++) {
                    var camposDeTexto = camposDeTextos.get(i);
                    var linha = valores[i];
                    camposDeTexto.origemField().setText(String.valueOf(linha[0]));
                    camposDeTexto.destinoField().setText(String.valueOf(linha[1]));
                    camposDeTexto.pesoField().setText(String.valueOf(linha[2]));
                }
            });
        }
        processButton.addActionListener(e -> {
            caminhos = new HashSet<>();
            stateField = StateField.VALID;
            camposDeTextos.forEach(camposDeTexto -> {
                String origemText = camposDeTexto.origemField().getText();
                String destinoText = camposDeTexto.destinoField().getText();
                String pesoText = camposDeTexto.pesoField().getText();
                if (origemText.isEmpty() || destinoText.isEmpty() || pesoText.isEmpty()) {
                    stateField = StateField.EMPTY;
                    return;
                }

                if (!TextUtils.isNumeric(origemText) || !TextUtils.isNumeric(destinoText) || !TextUtils.isNumeric(pesoText)) {
                    stateField = StateField.INVALID;
                    return;
                }

                int origem = Integer.parseInt(origemText);
                int destino = Integer.parseInt(destinoText);
                int peso = Integer.parseInt(pesoText);
//                if (origem == 0 || destino == 0){
//                    stateField = StateField.ZERO;
//                    return;
//                }

                String caminho1 = origem + "-" + destino;
                String caminho2 = destino + "-" + origem;

                if (caminhos.contains(caminho1) || caminhos.contains(caminho2)) {
                    stateField = StateField.EXISTING_PATH;
                    return;
                }

                caminhos.add(caminho1);

                Aresta aresta = new Aresta(new Vertice(origem, String.valueOf(origem)), new Vertice(destino, String.valueOf(destino)), peso);
                grafo.adicionarAresta(aresta.origem(), aresta.destino(), aresta.peso());
            });
            if (stateField == StateField.VALID) {
                int grafoFormato = radioOrganico.isSelected() ? GRAPH_FORMAT_ORGANIC : GRAPH_FORMAT_TREE;
                GraphViewFrame viewFrame = new GraphViewFrame(grafo, itemOption1.getState(), grafoFormato, GraphInputFrame.this);
                viewFrame.setVisible(true);
                setVisible(false);
            } else if (stateField == StateField.EMPTY) {
                JOptionPane.showMessageDialog(null, "Existem campos com valor inválido. Campo não pode está vazio", "Valor inválido", JOptionPane.WARNING_MESSAGE);
            } else if (stateField == StateField.INVALID) {
                JOptionPane.showMessageDialog(null, "Existem campos com valor inválido. Adicione apenas valores numéricos.", "Valor inválido", JOptionPane.WARNING_MESSAGE);
            } else if (stateField == StateField.EXISTING_PATH) {
                JOptionPane.showMessageDialog(null, "Existem campos com valor inválido. Existe par de vértice repetidos.", "Valor inválido", JOptionPane.WARNING_MESSAGE);
            }
        });

        JScrollPane scrollPane = new JScrollPane(painel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        add(scrollPane, BorderLayout.CENTER);
    }

}
