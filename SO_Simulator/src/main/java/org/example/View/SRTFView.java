package org.example.View;

import org.example.Model.SRTFAlgorithm;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SRTFView extends JFrame {
    private static final int TABLE_MARGIN = 20;

    public SRTFView() {
        super("Simulación de la aplicación del algoritmo SRTF");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;

        String[] columnNames = {"Nombre del Proceso", "Tiempo de Llegada", "Duración"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        populateTableData(tableModel);

        JButton buttonInicio = new JButton("Inicio");
        JButton buttonTerminar = new JButton("Terminar");
        buttonTerminar.setEnabled(false);
        GanttChart ganttChart = new GanttChart();

        String[] finalColumnNames = {"Información"};
        DefaultTableModel finalTableModel = new DefaultTableModel(finalColumnNames, 0);

        SRTFAlgorithm srtf = new SRTFAlgorithm();
        srtf.cargarProcesosDesdeCSV(ganttChart);

        ganttChart.setPreferredSize(new Dimension(800, 200));
        JScrollPane tableScrollPane = new JScrollPane(new JTable(tableModel));
        tableScrollPane.setBorder(new EmptyBorder(0, TABLE_MARGIN, 0, TABLE_MARGIN));
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1.0;
        constraints.weighty = 0.5;
        constraints.gridwidth = 2;
        constraints.insets = new Insets(TABLE_MARGIN, TABLE_MARGIN, 0, TABLE_MARGIN);
        getContentPane().add(tableScrollPane, constraints);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(ganttChart, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.add(buttonInicio);
        buttonPanel.add(buttonTerminar);

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.add(buttonPanel);

        panel.add(centerPanel, BorderLayout.PAGE_END);
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.weightx = 1.0;
        constraints.weighty = 0.0;
        constraints.gridwidth = 3;
        constraints.insets = new Insets(TABLE_MARGIN, TABLE_MARGIN, TABLE_MARGIN, TABLE_MARGIN);
        getContentPane().add(panel, constraints);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(new EmptyBorder(TABLE_MARGIN, 0, TABLE_MARGIN, 0));
        bottomPanel.add(new JScrollPane(new JTable(finalTableModel)), BorderLayout.CENTER);
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.weightx = 1.0;
        constraints.weighty = 0.5;
        constraints.gridwidth = 2;
        constraints.insets = new Insets(TABLE_MARGIN, TABLE_MARGIN, TABLE_MARGIN, TABLE_MARGIN);
        getContentPane().add(bottomPanel, constraints);

        buttonInicio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ganttChart.hasMoreProcesses()) {
                    ganttChart.executeNextRunnable();
                    if (!ganttChart.hasMoreProcesses()) {
                        buttonInicio.setText("Finalizado");
                        buttonInicio.setEnabled(false);
                        buttonTerminar.setEnabled(true);
                        finalTableModel.addRow(new Object[]{"El cambio de contexto se realiza en 0.2 milisegundos "});
                        finalTableModel.addRow(new Object[]{"Tiempo de espera por proceso: " + srtf.getProcessDataList()});
                        finalTableModel.addRow(new Object[]{"Tiempo de espera promedio (TEP): " + srtf.getTEP()});
                        finalTableModel.addRow(new Object[]{"Tiempo total de procesos (TTP): " + srtf.getTTP()});
                        finalTableModel.addRow(new Object[]{"Porcentaje de TTP que consume TEP: " + (srtf.getTTPQueConsumeTEP())});
                    } else {
                        buttonInicio.setText("Paso - " + (GanttChart.currentIndex + 1));
                    }
                }
            }
        });

        buttonTerminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        pack();
        setSize(850, 600);
        setLocationRelativeTo(null);
    }

    private void populateTableData(DefaultTableModel tableModel) {
        try (BufferedReader br = new BufferedReader(new FileReader("SO_Simulator/src/main/java/org/example/FileData/SRTFData.csv"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                tableModel.addRow(datos);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}