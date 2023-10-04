package drawingapp;
import java.awt.*;

public class Triángulo extends Figuras {
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = getWidth();
        int height = getHeight();

        int[] xPoints = {width / 2, width / 2 + 50, width / 2 - 50};
        int[] yPoints = {height / 2 - 50, height / 2 + 50, height / 2 + 50};

        // Dibuja el triángulo en el centro del panel
        g.setColor(Color.ORANGE);
        g.fillPolygon(xPoints, yPoints, 3);
    }
}
