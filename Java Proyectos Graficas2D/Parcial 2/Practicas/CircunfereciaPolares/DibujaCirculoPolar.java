import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class DibujaCirculoPolar extends JFrame {
    private BufferedImage buffer;
    public DibujaCirculoPolar() {
        // Configuración de la ventana
        setTitle("Dibujo de Círculo Usando Coordenadas Polares");
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

    // Método para dibujar un círculo usando coordenadas polares
    public void dibujarCirculo(int xc, int yc, int radio) {
        double paso = 0.001;  // Paso angular para mayor precisión (en radianes)

        for (double t = 0; t <= 2 * Math.PI; t += paso) {
            // Cálculo de las coordenadas en base a las ecuaciones polares
            int x = (int) Math.round(xc + radio * Math.sin(t));
            int y = (int) Math.round(yc + radio * Math.cos(t));

            putPixel(x, y, Color.BLACK);  // Dibujar el pixel calculado
        }
    }

    public static void main(String[] args) {
        DibujaCirculoPolar ventana = new DibujaCirculoPolar();
        ventana.dibujarCirculo(400, 300, 150);  // Círculo con centro en (400, 300) y radio 150
    }
}
