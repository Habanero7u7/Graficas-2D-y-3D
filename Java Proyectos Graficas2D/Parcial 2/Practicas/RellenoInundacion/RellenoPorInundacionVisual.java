import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Stack;

import javax.swing.JFrame;

public class RellenoPorInundacionVisual extends JFrame {
    private BufferedImage buffer;
    public RellenoPorInundacionVisual() {
        // Configuración del JFrame
        setTitle("Relleno por Inundación");
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
        buffer.setRGB(x, y, c.getRGB());
        this.getGraphics().drawImage(buffer, 0, 0, this);
    }

    public Color getPixel(int x, int y) {
        int rgb = buffer.getRGB(x, y);
        return new Color(rgb);
    }

    public void inicializarFondo() {
        for (int y = 0; y < buffer.getHeight(); y++) {
            for (int x = 0; x < buffer.getWidth(); x++) {
                buffer.setRGB(x, y, Color.WHITE.getRGB());
            }
        }
    }

    // Algoritmo iterativo de Flood-Fill usando pila
    public void floodFillIterativo(int x, int y, Color colorRelleno, Color colorOriginal) {
        Stack<int[]> pila = new Stack<>();
        pila.push(new int[]{x, y});

        while (!pila.isEmpty()) {
            int[] punto = pila.pop();
            int px = punto[0];
            int py = punto[1];

            // Verificar límites y color del píxel
            if (px < 0 || px >= buffer.getWidth() || py < 0 || py >= buffer.getHeight()) continue;
            if (!getPixel(px, py).equals(colorOriginal)) continue;

            // Rellenar el píxel y añadir vecinos a la pila
            putPixel(px, py, colorRelleno);
            pila.push(new int[]{px + 1, py});
            pila.push(new int[]{px - 1, py});
            pila.push(new int[]{px, py + 1});
            pila.push(new int[]{px, py - 1});
        }
    }

    // Dibujar un borde simple para demostrar el relleno
    public void dibujarBorde() {
        int[][] borde = {
            {10, 10}, {10, 30}, {30, 30}, {30, 50}, {50, 50}, {50, 10}, {10, 10}
        };

        for (int i = 0; i < borde.length - 1; i++) {
            int x0 = borde[i][0] * 10;
            int y0 = borde[i][1] * 10;
            int x1 = borde[i + 1][0] * 10;
            int y1 = borde[i + 1][1] * 10;
            dibujarLinea(x0, y0, x1, y1);
        }
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
        RellenoPorInundacionVisual ventana = new RellenoPorInundacionVisual();
        ventana.dibujarBorde();  // Dibujar el borde de la figura
        ventana.floodFillIterativo(200, 200, Color.GREEN, Color.WHITE);  // Rellenar la figura
    }
}
