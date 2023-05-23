package org.example.View;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.lang.Runnable;

public class GanttChart extends JPanel {
    public static final int PANEL_HEIGHT = 25;
    public static int currentIndex = -1;
    public static final int SPACING = 20;;
    private static final int UNIT_WIDTH = 25;
    private final ColorsList colors;
    public final List<JPanel> processPanels = new ArrayList<>();

    public GanttChart() {
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        this.setPreferredSize(new Dimension(0, PANEL_HEIGHT));
        this.setBorder(new EmptyBorder(40, SPACING, 40, SPACING));

        colors = new ColorsList();
    }

    public void addProcess(String processName, int burstTime, int executionTime) {
        JPanel processPanel = new JPanel();
        processPanel.setBackground(colors.dameColores());
        // El ancho del panel está basado en el tiempo de ejecución del proceso
        processPanel.setPreferredSize(new Dimension(UNIT_WIDTH * executionTime, PANEL_HEIGHT));
        System.out.println(executionTime);
        JLabel processLabel = new JLabel(processName, SwingConstants.CENTER);
        processLabel.setForeground(Color.BLACK);
        processPanel.add(processLabel);
        processPanel.setVisible(false);

        this.processPanels.add(processPanel);
        this.add(processPanel);
        this.revalidate();
        this.repaint();
    }

    public void executeNextRunnable() {
        if (hasMoreProcesses()) {
            currentIndex++;
            SwingUtilities.invokeLater(() -> {
                processPanels.get(currentIndex).setVisible(true);
                this.revalidate();
                this.repaint();
            });
        }
    }

    public boolean hasMoreProcesses() {
        return (currentIndex + 1) < processPanels.size();
    }
}