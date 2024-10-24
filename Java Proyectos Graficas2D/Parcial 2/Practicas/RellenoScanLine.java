import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFrame;

public class RellenoScanLine extends JFrame {
    private BufferedImage buffer;
    public RellenoScanLine() {
        // Configuración del JFrame
        setTitle("Relleno de Polígono con Scan-Line");
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

    // Método para dibujar una línea entre dos puntos
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

    // Algoritmo de Scan-Line para rellenar un polígono
    public void rellenarPoligono(int[][] vertices) {
        int yMin = Integer.MAX_VALUE;
        int yMax = Integer.MIN_VALUE;

        // Encontrar los límites superior e inferior en Y
        for (int[] vertice : vertices) {
            yMin = Math.min(yMin, vertice[1]);
            yMax = Math.max(yMax, vertice[1]);
        }

        // Procesar cada línea horizontal
        for (int y = yMin; y <= yMax; y++) {
            ArrayList<Integer> intersecciones = new ArrayList<>();

            // Encontrar las intersecciones con las aristas del polígono
            for (int i = 0; i < vertices.length; i++) {
                int x0 = vertices[i][0];
                int y0 = vertices[i][1];
                int x1 = vertices[(i + 1) % vertices.length][0];
                int y1 = vertices[(i + 1) % vertices.length][1];

                if ((y0 <= y && y1 > y) || (y1 <= y && y0 > y)) {
                    int xInterseccion = x0 + (y - y0) * (x1 - x0) / (y1 - y0);
                    intersecciones.add(xInterseccion);
                }
            }

            // Ordenar las intersecciones
            intersecciones.sort(Integer::compareTo);

            // Dibujar los segmentos entre pares de intersecciones
            for (int i = 0; i < intersecciones.size() - 1; i += 2) {
                int xStart = intersecciones.get(i);
                int xEnd = intersecciones.get(i + 1);
                for (int x = xStart; x <= xEnd; x++) {
                    putPixel(x, y, Color.RED);
                }
            }
        }
    }

    // Dibujar un polígono y rellenarlo
    public void dibujarPoligono() {
        int[][] vertices = {
            {100, 100}, {200, 50}, {300, 100}, {250, 200}, {150, 200}
        };

        // Dibujar el contorno del polígono
        for (int i = 0; i < vertices.length; i++) {
            int x0 = vertices[i][0];
            int y0 = vertices[i][1];
            int x1 = vertices[(i + 1) % vertices.length][0];
            int y1 = vertices[(i + 1) % vertices.length][1];
            dibujarLinea(x0, y0, x1, y1);
        }

        // Rellenar el polígono con el algoritmo de Scan-Line
        rellenarPoligono(vertices);
    }

    public static void main(String[] args) {
        RellenoScanLine ventana = new RellenoScanLine();
        ventana.dibujarPoligono();  // Dibujar y rellenar el polígono
    }
}
