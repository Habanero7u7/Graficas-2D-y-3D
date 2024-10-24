import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class LineasConGrosor extends JFrame {
    private BufferedImage buffer;
    public LineasConGrosor() {
        // Configuración del JFrame
        setTitle("Dibujo de Líneas con Grosor");
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

    // Algoritmo de Bresenham para dibujar una línea con grosor
    public void dibujarLineaConGrosor(int x0, int y0, int x1, int y1, int grosor) {
        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);
        int sx = (x0 < x1) ? 1 : -1;
        int sy = (y0 < y1) ? 1 : -1;
        int err = dx - dy;

        while (true) {
            // Dibujar la línea principal con grosor
            dibujarConGrosor(x0, y0, grosor);

            if (x0 == x1 && y0 == y1) break;

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

    // Método para dibujar el píxel con el grosor especificado
    private void dibujarConGrosor(int x, int y, int grosor) {
        for (int i = -grosor / 2; i <= grosor / 2; i++) {
            for (int j = -grosor / 2; j <= grosor / 2; j++) {
                putPixel(x + i, y + j, Color.BLACK);
            }
        }
    }

    // Método para dibujar varias líneas con diferentes grosores
    public void dibujarLineas() {
        // Línea con grosor 1
        dibujarLineaConGrosor(50, 50, 300, 50, 1);

        // Línea con grosor 3
        dibujarLineaConGrosor(50, 100, 300, 100, 3);

        // Línea con grosor 5
        dibujarLineaConGrosor(50, 150, 300, 150, 5);

        // Línea con grosor 7
        dibujarLineaConGrosor(50, 200, 300, 200, 7);
    }

    public static void main(String[] args) {
        LineasConGrosor ventana = new LineasConGrosor();
        ventana.dibujarLineas();  // Dibujar las líneas con diferentes grosores
    }
}
