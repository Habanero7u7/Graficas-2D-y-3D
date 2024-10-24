import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class DibujaCirculoPolarSimetria extends JFrame {
    private BufferedImage buffer;
    public DibujaCirculoPolarSimetria() {
        // Configuración de la ventana
        setTitle("Dibujo de Circunferencia con Coordenadas Polares y Simetría");
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

    // Método para dibujar un círculo utilizando coordenadas polares y simetría
    public void dibujarCirculo(int xc, int yc, int radio) {
        double paso = 0.001;

        for (double t = 0; t <= Math.PI / 4; t += paso) {
            // Cálculo de las coordenadas usando coordenadas polares
            int x = (int) Math.round(radio * Math.cos(t));
            int y = (int) Math.round(radio * Math.sin(t));

            // Dibujar los puntos en los ocho octantes
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
        DibujaCirculoPolarSimetria ventana = new DibujaCirculoPolarSimetria();
        ventana.dibujarCirculo(400, 300, 150);  // Círculo con centro en (400, 300) y radio 150
    }
}
