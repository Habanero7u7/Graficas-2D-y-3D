import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class DibujaRectangulo extends JFrame {
    private BufferedImage buffer;
    public DibujaRectangulo() {
        // Configuración de la ventana
        setTitle("Dibujo de Rectángulo sin Huecos");
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

    // Algoritmo de punto medio para dibujar una línea entre dos puntos
    public void dibujarLineaPuntoMedio(int x0, int y0, int x1, int y1) {
        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);
        int sx = (x0 < x1) ? 1 : -1;
        int sy = (y0 < y1) ? 1 : -1;

        boolean esPendienteAlta = dy > dx;

        if (esPendienteAlta) {
            int temp = dx;
            dx = dy;
            dy = temp;
        }

        int d = 2 * dy - dx;
        int x = x0, y = y0;

        for (int i = 0; i <= dx; i++) {
            putPixel(x, y, Color.BLACK);

            if (d > 0) {
                if (esPendienteAlta) {
                    x += sx;
                } else {
                    y += sy;
                }
                d -= 2 * dx;
            }

            if (esPendienteAlta) {
                y += sy;
            } else {
                x += sx;
            }
            d += 2 * dy;
        }
    }

    // Método para dibujar un rectángulo sin huecos en las esquinas
    public void dibujarRectangulo(int x0, int y0, int ancho, int alto) {
        // Dibujar cada línea del rectángulo, asegurando que las esquinas se conecten
        dibujarLineaPuntoMedio(x0, y0, x0 + ancho, y0);              // Línea superior
        dibujarLineaPuntoMedio(x0, y0 + alto, x0 + ancho, y0 + alto); // Línea inferior
        dibujarLineaPuntoMedio(x0, y0, x0, y0 + alto);                // Línea izquierda
        dibujarLineaPuntoMedio(x0 + ancho, y0, x0 + ancho, y0 + alto); // Línea derecha
    }

    public static void main(String[] args) {
        DibujaRectangulo ventana = new DibujaRectangulo();
        ventana.dibujarRectangulo(100, 100, 300, 200);  // Ejemplo de rectángulo
    }
}