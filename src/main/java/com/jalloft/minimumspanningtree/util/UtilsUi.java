package com.jalloft.minimumspanningtree.util;

import com.jalloft.minimumspanningtree.GraphApp;
import com.jalloft.minimumspanningtree.model.StateField;

import javax.swing.*;
import java.awt.*;

public class UtilsUi {
    public static void setPosition(JFrame frame) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        int frameWidth = frame.getSize().width;
        int frameHeight = frame.getSize().height;
        int posX = (screenWidth - frameWidth) / 2;
        int posY = (screenHeight - frameHeight) / 2;
        frame.setLocation(posX, posY);
    }


    public static boolean isValid(StateField stateField) {

        if (stateField == StateField.VALID) {
            return true;
        } else if (stateField == StateField.EMPTY) {
            showErrorMessage("Por favor, defina o número de vértices e o número de arestas.");
        } else if (stateField == StateField.ZERO) {
            showErrorMessage("Por favor, defina o número de vértices e o número de arestas diferente de zero");
        } else if (stateField == StateField.INVALID) {
            showErrorMessage("Por favor, adicione apenas valores numéricos.");
        } else if (stateField == StateField.EDGE_MAX) {
            showErrorMessage("O número de arestas ultrapassou o limite para a quantidade de vértices");
        }
        return false;
    }


    private static void showErrorMessage(String msg) {
        JOptionPane.showMessageDialog(null, "O valor definido é inválido. " + msg, "Valor inválido", JOptionPane.WARNING_MESSAGE);
    }
}
