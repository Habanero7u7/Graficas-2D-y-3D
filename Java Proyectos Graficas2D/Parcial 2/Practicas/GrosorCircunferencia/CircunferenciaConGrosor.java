import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class CircunferenciaConGrosor extends JFrame {
    private BufferedImage buffer;
    public CircunferenciaConGrosor() {
        // Configuración del JFrame
        setTitle("Dibujo de Circunferencia con Grosor");
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

    // Algoritmo de punto medio para dibujar una circunferencia con grosor
    public void dibujarCircunferenciaConGrosor(int xc, int yc, int radio, int grosor) {
        for (int i = 0; i < grosor; i++) {
            dibujarCircunferencia(xc, yc, radio + i);
        }
    }

    // Algoritmo de punto medio para dibujar una circunferencia
    private void dibujarCircunferencia(int xc, int yc, int radio) {
        int x = 0;
        int y = radio;
        int d = 1 - radio;

        while (x <= y) {
            dibujarSimetria(xc, yc, x, y);
            if (d < 0) {
                d += 2 * x + 3;
            } else {
                d += 2 * (x - y) + 5;
                y--;
            }
            x++;
        }
    }

    // Dibuja los puntos simétricos de la circunferencia
    private void dibujarSimetria(int xc, int yc, int x, int y) {
        putPixel(xc + x, yc + y, Color.BLACK);
        putPixel(xc - x, yc + y, Color.BLACK);
        putPixel(xc + x, yc - y, Color.BLACK);
        putPixel(xc - x, yc - y, Color.BLACK);
        putPixel(xc + y, yc + x, Color.BLACK);
        putPixel(xc - y, yc + x, Color.BLACK);
        putPixel(xc + y, yc - x, Color.BLACK);
        putPixel(xc - y, yc - x, Color.BLACK);
    }

    // Método para dibujar varias circunferencias con diferentes grosores
    public void dibujarCircunferencias() {
        // Circunferencia con grosor 1
        dibujarCircunferenciaConGrosor(200, 300, 50, 1);

        // Circunferencia con grosor 3
        dibujarCircunferenciaConGrosor(400, 300, 50, 3);

        // Circunferencia con grosor 5
        dibujarCircunferenciaConGrosor(600, 300, 50, 5);
    }

    public static void main(String[] args) {
        CircunferenciaConGrosor ventana = new CircunferenciaConGrosor();
        ventana.dibujarCircunferencias();  // Dibujar las circunferencias con diferentes grosores
    }
}
