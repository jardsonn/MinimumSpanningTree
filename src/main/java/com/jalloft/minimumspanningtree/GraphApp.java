package com.jalloft.minimumspanningtree;

import com.jalloft.minimumspanningtree.gui.GraphInputFrame;
import com.jalloft.minimumspanningtree.util.TextUtils;
import com.jalloft.minimumspanningtree.util.UtilsUi;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class GraphApp extends JFrame {

    public static int WINDOW_WIDTH = 800;
    public static int WINDOW_HEIGHT = 600;

    private JPanel contentPane;

    private final JTextField verticesField;
    private final JTextField arestasField;

    public GraphApp() {
        setTitle("Prova II - Grafos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        UtilsUi.setPosition(this);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        int fieldW = 230;
        int fieldH = 20;

        JLabel lblNmeroDeVertice = new JLabel("Número de vértice");
        lblNmeroDeVertice.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblNmeroDeVertice.setBounds(WINDOW_WIDTH / 2 - fieldW / 2, 72, fieldW, fieldH);
        contentPane.add(lblNmeroDeVertice);

        verticesField = new JTextField();
        verticesField.setBounds(WINDOW_WIDTH / 2 - fieldW / 2, 91, 231, 20);
        contentPane.add(verticesField);
        verticesField.setColumns(10);

        JLabel lblNmeroDeArestas = new JLabel("Número de arestas");
        lblNmeroDeArestas.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblNmeroDeArestas.setBounds(WINDOW_WIDTH / 2 - fieldW / 2, 134, fieldW, fieldH);
        contentPane.add(lblNmeroDeArestas);

        arestasField = new JTextField();
        arestasField.setColumns(10);
        arestasField.setBounds(WINDOW_WIDTH / 2 - fieldW / 2, 153, 231, 20);
        contentPane.add(arestasField);

        JButton submitButton = new JButton("Próximo");
        JButton completeButton = new JButton("︽");

        completeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verticesField.setText("7");
                arestasField.setText("11");
            }
        });
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String verticeText = verticesField.getText();
                String arestasText = arestasField.getText();

                if (UtilsUi.isValid(TextUtils.verificarEntrada(verticeText, arestasText))){
                    int numVertices = Integer.parseInt(verticeText);
                    int numArestas = Integer.parseInt(arestasText);
                    GraphInputFrame inputFrame = new GraphInputFrame(numVertices, numArestas, GraphApp.this);
                    inputFrame.setVisible(true);
                    setVisible(false);
                }
            }
        });
        submitButton.setBounds(WINDOW_WIDTH / 2 - 90 / 2, 209, 90, 23);
        completeButton.setBounds(WINDOW_WIDTH / 2 + arestasField.getWidth() / 2 - 50, 209, 50, 23);
        contentPane.add(submitButton);
        contentPane.add(completeButton);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                GraphApp app = new GraphApp();
                app.setVisible(true);
            }
        });
    }
}
