package org.example.View;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

public class GanttChart extends JPanel {
    public static final int PANEL_HEIGHT = 50;
    private static final double UNIT_WIDTH = 50.0;
    private static final int SPACING = 20;

    private ColorsList colors;

    public GanttChart() {
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        this.setPreferredSize(new Dimension(0, PANEL_HEIGHT));
        this.setBorder(new EmptyBorder(40, SPACING, 40, SPACING));

        colors = new ColorsList();
    }

    public void addProcess(String processName, double duration) {
        JPanel processPanel = new JPanel();
        processPanel.setBackground(colors.dameColores());
        processPanel.setPreferredSize(new Dimension((int) (UNIT_WIDTH * duration), PANEL_HEIGHT));
        JLabel processLabel = new JLabel(processName, SwingConstants.CENTER);
        processLabel.setForeground(Color.BLACK);
        processPanel.add(processLabel);
        this.add(processPanel);
        this.revalidate();
        this.repaint();
    }
}