import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class RecortePorPuntos extends JFrame {
    private BufferedImage buffer;
    // Definición del área de recorte
    private int xmin = 100, ymin = 100, xmax = 400, ymax = 400;

    public RecortePorPuntos() {
        // Configuración del JFrame
        setTitle("Recorte de Circunferencias por Puntos");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        // Inicialización del buffer para dibujar un pixel
        buffer = new BufferedImage(600, 600, BufferedImage.TYPE_INT_RGB);
        this.getGraphics();
        inicializarFondo();  // Establecer fondo blanco
    }

    public void putPixel(int x, int y, Color c) {
        if (x >= xmin && x <= xmax && y >= ymin && y <= ymax) {
            buffer.setRGB(x, y, c.getRGB());
            this.getGraphics().drawImage(buffer, 0, 0, this);
        }
    }

    public void inicializarFondo() {
        for (int y = 0; y < buffer.getHeight(); y++) {
            for (int x = 0; x < buffer.getWidth(); x++) {
                buffer.setRGB(x, y, Color.WHITE.getRGB());
            }
        }
    }

    // Algoritmo de punto medio para dibujar una circunferencia con recorte por puntos
    public void dibujarCircunferenciaConRecorte(int xc, int yc, int radio) {
        int x = 0;
        int y = radio;
        int d = 1 - radio;

        dibujarSimetriaConRecorte(xc, yc, x, y);

        while (x < y) {
            if (d < 0) {
                d += 2 * x + 3;
            } else {
                d += 2 * (x - y) + 5;
                y--;
            }
            x++;
            dibujarSimetriaConRecorte(xc, yc, x, y);
        }
    }

    // Dibuja los puntos simétricos de la circunferencia, aplicando el recorte por puntos
    private void dibujarSimetriaConRecorte(int xc, int yc, int x, int y) {
        putPixel(xc + x, yc + y, Color.BLACK);
        putPixel(xc - x, yc + y, Color.BLACK);
        putPixel(xc + x, yc - y, Color.BLACK);
        putPixel(xc - x, yc - y, Color.BLACK);
        putPixel(xc + y, yc + x, Color.BLACK);
        putPixel(xc - y, yc + x, Color.BLACK);
        putPixel(xc + y, yc - x, Color.BLACK);
        putPixel(xc - y, yc - x, Color.BLACK);
    }

    // Dibujar varias circunferencias dentro del área de recorte
    public void dibujarCircunferencias() {
        // Dibujar el rectángulo de recorte
        dibujarLinea(xmin, ymin, xmax, ymin);
        dibujarLinea(xmax, ymin, xmax, ymax);
        dibujarLinea(xmax, ymax, xmin, ymax);
        dibujarLinea(xmin, ymax, xmin, ymin);

        // Dibujar varias circunferencias aplicando el recorte por puntos
        dibujarCircunferenciaConRecorte(200, 200, 50);
        dibujarCircunferenciaConRecorte(300, 300, 100);
        dibujarCircunferenciaConRecorte(400, 200, 75);
        dibujarCircunferenciaConRecorte(150, 400, 90);
    }

    // Algoritmo de Bresenham para dibujar líneas
    public void dibujarLinea(int x0, int y0, int x1, int y1) {
        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);
        int sx = (x0 < x1) ? 1 : -1;
        int sy = (y0 < y1) ? 1 : -1;
        int err = dx - dy;

        while (true) {
            putPixel(x0, y0, Color.BLACK);

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

    public static void main(String[] args) {
        RecortePorPuntos ventana = new RecortePorPuntos();
        ventana.dibujarCircunferencias();  // Dibujar las circunferencias con recorte
    }
}
