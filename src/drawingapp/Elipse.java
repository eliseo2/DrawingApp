package drawingapp;
import java.awt.Color;
import java.awt.Graphics;

public class Elipse extends Figuras {
     protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = getWidth();
        int height = getHeight();

        // Dibuja la elipse en el centro del panel
        g.setColor(Color.RED);
        g.fillOval(width / 4, height / 4, width / 2, height / 2);
    }
    
    
}
