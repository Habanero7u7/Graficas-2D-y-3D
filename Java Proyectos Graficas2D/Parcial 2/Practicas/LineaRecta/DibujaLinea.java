import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class DibujaLinea extends JFrame {
    private BufferedImage buffer;
    public DibujaLinea() {
        // Configuración de la ventana
        setTitle("Dibujo de Línea Recta");
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

    // Método para dibujar una línea recta utilizando la ecuación y = mx + b
    public void dibujarLinea(int x0, int y0, int x1, int y1) {
        int dx = x1 - x0;
        int dy = y1 - y0;
        float m = dy / dx; // Calcula la pendiente
        float b = y0 - (m * x0); // Calcula la intersección en y

        // Recorre los puntos entre x0 y x1 para dibujar la línea
        for (int x = x0; x <= x1; x++) {
            float y = (m * x) + b; // Calcula y para cada x
            putPixel(x, Math.round(y), Color.BLACK); // Dibuja el pixel en la posición calculada
        }
    }

    public static void main(String[] args) {
        DibujaLinea ventana = new DibujaLinea();
        ventana.dibujarLinea(150, 500, 500, 150); // Ejemplo de línea entre (50,50) y (300,300)
    }
}
