package org.example.View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
public class SRTFView extends JFrame {

    private final int stepCounter = 0;

    public SRTFView() {
        super("Simulación de la aplicación del algoritmo SRTF");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        String[] columnNames = {"Nombre del Proceso", "Tiempo de Llegada", "Duración"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);

        try (BufferedReader br = new BufferedReader(new FileReader("SO_Simulator/src/main/java/org/example/FileData/SRTFData.csv"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                model.addRow(datos);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        // Agregar la tabla y el gráfico de Gantt a la ventana.
        add(new JScrollPane(table), BorderLayout.NORTH);
        pack();
    }

    public static void main(String[] args) {
        SRTFView view = new SRTFView();
        view.setVisible(true);
    }
}