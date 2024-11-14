import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class DibujaCirculo extends JFrame {
    private BufferedImage buffer;
    public DibujaCirculo() {
        // Configuración de la ventana
        setTitle("Dibujo de Círculo Usando la Fórmula Matemática");
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

    // Método para dibujar un círculo utilizando la ecuación R^2 = (x - xc)^2 + (y - yc)^2
    public void dibujarCirculo(int xc, int yc, int radio) {
        for (int x = -radio; x <= radio; x++) {
            int y = (int) Math.round(Math.sqrt(radio * radio - x * x));

            putPixel(xc + x, yc + y, Color.BLACK);  // Dibuja el punto en la parte superior
            putPixel(xc + x, yc - y, Color.BLACK);  // Dibuja el punto en la parte inferior
        }

    }

    public static void main(String[] args) {
        DibujaCirculo ventana = new DibujaCirculo();
        ventana.dibujarCirculo(400, 300, 200);  // Ejemplo de círculo con centro en (400, 300) y radio 100
    }
}
