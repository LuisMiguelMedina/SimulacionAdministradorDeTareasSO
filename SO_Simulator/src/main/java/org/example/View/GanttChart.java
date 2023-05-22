package org.example.View;

import javax.swing.*;
import java.awt.*;

public class GanttChart extends JPanel {

    private static final int PANEL_HEIGHT = 50;
    private static final int UNIT_WIDTH = 40;

    public GanttChart() {
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        this.setPreferredSize(new Dimension(0, PANEL_HEIGHT));
    }

    public void addProcess(String processName, Color color, int duration) {
        JPanel processPanel = new JPanel();
        processPanel.setBackground(color);
        processPanel.setPreferredSize(new Dimension(UNIT_WIDTH * duration, PANEL_HEIGHT));
        JLabel processLabel = new JLabel(processName, SwingConstants.CENTER);
        processLabel.setForeground(Color.BLACK);
        processPanel.add(processLabel);
        this.add(processPanel);
    }

    public void SRTFView(){

    }

    public static void main(String[] args) {
        GanttChart ganttChart = new GanttChart();
        ganttChart.addProcess("Proceso 1", Color.RED, 5);
        ganttChart.addProcess("Proceso 2", Color.GREEN, 3);
        ganttChart.addProcess("Proceso 3", Color.BLUE, 7);

        JFrame frame = new JFrame("Diagrama de Gantt");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(ganttChart);
        frame.pack();
        frame.setVisible(true);
    }
}
