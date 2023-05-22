package org.example.View;

import org.example.Model.SRTFAlgorithm;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.border.EmptyBorder;
public class SRTFView extends JFrame {
    private static final int TABLE_MARGIN = 20;
    public SRTFView() {
        super("Simulación de la aplicación del algoritmo SRTF");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        String[] columnNames = {"Nombre del Proceso", "Tiempo de Llegada", "Duración"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model) {
            @Override
            public Dimension getPreferredScrollableViewportSize() {
                return new Dimension(this.getPreferredSize().width, this.getRowHeight() * this.getRowCount());
            }
        };
        try (BufferedReader br = new BufferedReader(new FileReader("SO_Simulator/src/main/java/org/example/FileData/SRTFData.csv"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                model.addRow(datos);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Crear una instancia de GanttChart y añadirla al panel
        GanttChart ganttChart = new GanttChart();
        SRTFAlgorithm srtf = new SRTFAlgorithm();
        srtf.cargarProcesosDesdeCSV(ganttChart);

        // Asumiendo que tienes una variable `totalWidth` que representa el ancho total de todos tus procesos.
        ganttChart.setPreferredSize(new Dimension(1500, 600));

        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setBorder(new EmptyBorder(0, TABLE_MARGIN, TABLE_MARGIN, TABLE_MARGIN));

        getContentPane().add(tableScrollPane, BorderLayout.CENTER);

        // Agregar GanttChart y JTable al panel
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(table), BorderLayout.PAGE_START);
        panel.add(ganttChart, BorderLayout.CENTER);
        this.getContentPane().add(panel);
    }

    public static void main(String[] args) {
        SRTFView view = new SRTFView();
        view.pack();
        view.setVisible(true);
    }
}