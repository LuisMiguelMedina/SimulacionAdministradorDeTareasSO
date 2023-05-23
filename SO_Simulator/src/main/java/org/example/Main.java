package org.example;

import org.example.View.SRTFView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SRTFView view = new SRTFView();
            view.setVisible(true);
        });
    }
}