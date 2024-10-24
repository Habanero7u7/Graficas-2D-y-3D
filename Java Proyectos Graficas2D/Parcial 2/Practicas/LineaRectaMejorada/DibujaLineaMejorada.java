import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class DibujaLineaMejorada extends JFrame {
    private BufferedImage buffer;
    public DibujaLineaMejorada() {
        // Configuración de la ventana
        setTitle("Dibujo de Línea Recta Mejorada");
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

    // Método mejorado para dibujar una línea utilizando y = mx + b
    public void dibujarLinea(int x0, int y0, int x1, int y1) {
        // Aseguramos que x0 sea menor que x1
        if (x0 > x1) {
            int tempX = x0; x0 = x1; x1 = tempX;
            int tempY = y0; y0 = y1; y1 = tempY;
        }

        float dx = x1 - x0;
        float dy = y1 - y0;

        // Determinar si la pendiente es mayor o menor que 1
        if (Math.abs(dy) <= Math.abs(dx)) {
            float m = dy / dx;  // Pendiente
            float b = y0 - m * x0;  // Intersección en y

            for (int x = x0; x <= x1; x++) {
                int y = Math.round(m * x + b);
                putPixel(x, y, Color.BLACK);  // Dibuja el pixel
            }
        } else {
            float m = dx / dy;  // Invertimos la pendiente
            float b = x0 - m * y0;  // Intersección en x

            int startY = Math.min(y0, y1);
            int endY = Math.max(y0, y1);

            for (int y = startY; y <= endY; y++) {
                int x = Math.round(m * y + b);
                putPixel(x, y, Color.BLACK);  // Dibuja el pixel
            }
        }
    }

    public static void main(String[] args) {
        DibujaLineaMejorada ventana = new DibujaLineaMejorada();
        ventana.dibujarLinea(100, 100, 500, 400);  // Línea diagonal
    }
}
