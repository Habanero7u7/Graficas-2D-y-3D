import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class DibujaLineaBresenham extends JFrame {
    private BufferedImage buffer;
    public DibujaLineaBresenham() {
        // Configuración de la ventana
        setTitle("Dibujo de Línea con Algoritmo de Bresenham");
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

    // Algoritmo de Bresenham para dibujar una línea
    public void dibujarLineaBresenham(int x0, int y0, int x1, int y1) {
        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);

        int sx = (x0 < x1) ? 1 : -1;
        int sy = (y0 < y1) ? 1 : -1;

        int err = dx - dy;

        while (true) {
            putPixel(x0, y0, Color.BLACK);  // Dibuja el pixel

            if (x0 == x1 && y0 == y1) break;  // Salimos del bucle si llegamos al punto final

            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x0 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y0 += sy;
            }
        }
    }

    public static void main(String[] args) {
        DibujaLineaBresenham ventana = new DibujaLineaBresenham();
        ventana.dibujarLineaBresenham(100, 100, 500, 400);  // Ejemplo de línea diagonal
    }
}
