import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class DibujaCirculoBresenham extends JFrame {
    private BufferedImage buffer;
    public DibujaCirculoBresenham() {
        // Configuración de la ventana
        setTitle("Dibujo de Circunferencia usando Algoritmo de Bresenham");
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

    // Método para dibujar una circunferencia usando el algoritmo de Bresenham
    public void dibujarCirculo(int xc, int yc, int radio) {
        int x = 0;
        int y = radio;
        int d = 3 - 2 * radio;  // Valor inicial de la función de decisión

        // Dibujar los puntos iniciales utilizando simetría
        dibujarSimetria(xc, yc, x, y);

        while (x <= y) {
            x++;

            if (d < 0) {
                d = d + 4 * x + 6;
            } else {
                y--;
                d = d + 4 * (x - y) + 10;
            }

            dibujarSimetria(xc, yc, x, y);
        }
    }

    // Método para dibujar los puntos simétricos en los ocho octantes
    private void dibujarSimetria(int xc, int yc, int x, int y) {
        putPixel(xc + x, yc + y, Color.BLACK);  // Octante 1
        putPixel(xc - x, yc + y, Color.BLACK);  // Octante 2
        putPixel(xc + x, yc - y, Color.BLACK);  // Octante 3
        putPixel(xc - x, yc - y, Color.BLACK);  // Octante 4
        putPixel(xc + y, yc + x, Color.BLACK);  // Octante 5
        putPixel(xc - y, yc + x, Color.BLACK);  // Octante 6
        putPixel(xc + y, yc - x, Color.BLACK);  // Octante 7
        putPixel(xc - y, yc - x, Color.BLACK);  // Octante 8
    }

    public static void main(String[] args) {
        DibujaCirculoBresenham ventana = new DibujaCirculoBresenham();
        ventana.dibujarCirculo(400, 300, 150);  // Círculo con centro en (400, 300) y radio 150
    }
}
