package drawingapp;
import java.awt.Color;
import java.awt.Graphics;


public class Circulo extends Figuras{

    public Circulo(Color color, int x, int y, int width, int height) {
        super(color, x, y, width, height);
    }
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = getWidth();
        int height = getHeight();

        int circleRadius = Math.min(width, height) / 2; // Radio del círculo

        // Dibuja el círculo en el centro del panel
        g.setColor(Color.BLUE);
        g.fillOval((width - circleRadius) / 2, (height - circleRadius) / 2, circleRadius, circleRadius);
    }
}
