import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class DibujaLineaDDA extends JFrame {
    private BufferedImage buffer;
    public DibujaLineaDDA() {
        // Configuración de la ventana
        setTitle("Dibujo de Línea Recta con DDA");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        // Inicialización del buffer para dibujar un pixel
        buffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        buffer.getGraphics();
    }

    public void putPixel(int x, int y, Color c) {
        buffer.setRGB(0, 0, c.getRGB());
        this.getGraphics().drawImage(buffer, x, y, this);
    }

    // Algoritmo DDA para dibujar una línea
    public void dibujarLineaDDA(int x0, int y0, int x1, int y1) {
        int dx = x1 - x0;
        int dy = y1 - y0;

        // Determinar el número de pasos necesarios
        int steps = Math.max(Math.abs(dx), Math.abs(dy));

        // Calcular los incrementos en cada eje por cada paso
        float xIncrement = (float) dx / steps;
        float yIncrement = (float) dy / steps;

        float x = x0;
        float y = y0;

        // Dibujar los píxeles a lo largo de la línea
        for (int i = 0; i <= steps; i++) {
            putPixel(Math.round(x), Math.round(y), Color.BLACK);
            x += xIncrement;
            y += yIncrement;
        }
    }

    public static void main(String[] args) {
        DibujaLineaDDA ventana = new DibujaLineaDDA();
        ventana.dibujarLineaDDA(100, 100, 500, 400);  // Ejemplo de línea diagonal
    }
}
