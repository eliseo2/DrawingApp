 package drawingapp;
import java.awt.*;
 
public class Rectángulo extends Figuras{
     protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = getWidth();
        int height = getHeight();

        int squareSize = Math.min(width, height) / 2; // Tamaño del cuadrado

        // Dibuja el cuadrado en el centro del panel
        g.setColor(Color.BLUE);
        g.fillRect((width - squareSize) / 2, (height - squareSize) / 2, squareSize, squareSize);
    }
}
