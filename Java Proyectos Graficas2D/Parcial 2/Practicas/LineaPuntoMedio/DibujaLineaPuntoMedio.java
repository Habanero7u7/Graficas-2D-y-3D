import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class DibujaLineaPuntoMedio extends JFrame {
    private BufferedImage buffer;
    public DibujaLineaPuntoMedio() {
        // Configuración de la ventana
        setTitle("Dibujo de Línea con Algoritmo de Punto Medio");
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

    // Algoritmo de Punto Medio para dibujar una línea recta
    public void dibujarLineaPuntoMedio(int x0, int y0, int x1, int y1) {
        int dx = x1 - x0;
        int dy = y1 - y0;

        int d = dy - (dx / 2);  // Valor inicial de la función de decisión
        int x = x0, y = y0;

        putPixel(x, y, Color.BLACK);  // Dibujar el primer pixel

        // Bucle para calcular y dibujar los puntos de la línea
        while (x < x1) {
            x++;

            // Verificar si el valor de la función de decisión es negativo o positivo
            if (d < 0) {
                d = d + dy;
            } else {
                y++;
                d = d + (dy - dx);
            }

            putPixel(x, y, Color.BLACK);  // Dibujar el pixel en la posición calculada
        }
    }

    public static void main(String[] args) {
        DibujaLineaPuntoMedio ventana = new DibujaLineaPuntoMedio();
        ventana.dibujarLineaPuntoMedio(100, 100, 500, 300);  // Línea diagonal ejemplo
    }
}
