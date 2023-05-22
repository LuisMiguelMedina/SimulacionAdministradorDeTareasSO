package org.example.View;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;
public class ColorsList extends JFrame {
    private final List<Color> colores;
    private int indiceActual;

    public ColorsList() {
        colores = new ArrayList<>();
        colores.add(Color.GREEN);
        colores.add(Color.BLUE);
        colores.add(Color.ORANGE);
        colores.add(Color.YELLOW);
        colores.add(Color.RED);
        colores.add(Color.MAGENTA);
        colores.add(Color.CYAN);
        colores.add(Color.PINK);

        indiceActual = 0;
    }

    public Color dameColores() {
        Color color = colores.get(indiceActual);
        indiceActual = (indiceActual + 1) % colores.size();
        return color;
    }
}