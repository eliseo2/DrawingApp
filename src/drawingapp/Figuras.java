package drawingapp;
import javax.swing.*;
import java.awt.*;

public class Figuras extends JPanel {
    private Color color; // Color de la figura
    private int x, y; // Posición de la figura
    private int width, height; // Ancho y alto de la figura

    // Constructor de Figuras
    public Figuras(Color color, int x, int y, int width, int height) {
        this.color = color;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    // Método para dibujar la figura
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(color);
        g.fillRect(x, y, width, height);
    }
}
