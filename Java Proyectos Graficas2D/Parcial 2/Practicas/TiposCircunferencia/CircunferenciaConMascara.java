import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class CircunferenciaConMascara extends JFrame {
    private BufferedImage buffer;
    public CircunferenciaConMascara() {
        // Configuración del JFrame
        setTitle("Dibujo de Circunferencia con Máscara");
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

    // Algoritmo de punto medio para dibujar una circunferencia con máscara
    public void dibujarCircunferenciaConMascara(int xc, int yc, int radio, int[] mascara) {
        int x = 0;
        int y = radio;
        int d = 1 - radio;
        int i = 0;  // Índice para la máscara

        // Dibujar los puntos iniciales utilizando máscara
        dibujarSimetriaConMascara(xc, yc, x, y, mascara, i);

        while (x < y) {
            if (d < 0) {
                d += 2 * x + 3;
            } else {
                d += 2 * (x - y) + 5;
                y--;
            }
            x++;
            i++;  // Avanzar en la máscara
            dibujarSimetriaConMascara(xc, yc, x, y, mascara, i);
        }
    }

    // Método para dibujar los puntos simétricos aplicando la máscara
    private void dibujarSimetriaConMascara(int xc, int yc, int x, int y, int[] mascara, int i) {
        if (mascara[i % mascara.length] == 1) {
            putPixel(xc + x, yc + y, Color.BLACK);
            putPixel(xc - x, yc + y, Color.BLACK);
            putPixel(xc + x, yc - y, Color.BLACK);
            putPixel(xc - x, yc - y, Color.BLACK);
            putPixel(xc + y, yc + x, Color.BLACK);
            putPixel(xc - y, yc + x, Color.BLACK);
            putPixel(xc + y, yc - x, Color.BLACK);
            putPixel(xc - y, yc - x, Color.BLACK);
        }
    }

    // Método para dibujar varias circunferencias con diferentes máscaras
    public void dibujarCircunferencias() {
        // Circunferencia sólida (máscara con todos los bits en 1)
        int[] mascaraSolida = {1, 1, 1, 1, 1, 1, 1, 1};
        dibujarCircunferenciaConMascara(200, 300, 100, mascaraSolida);

        // Circunferencia punteada (máscara alternada)
        int[] mascaraPunteada = {1, 0, 1, 0, 1, 0, 1, 0};
        dibujarCircunferenciaConMascara(400, 300, 100, mascaraPunteada);

        // Circunferencia discontinua (más segmentos largos)
        int[] mascaraDiscontinua = {1, 1, 1, 0, 0, 0, 1, 1};
        dibujarCircunferenciaConMascara(600, 300, 100, mascaraDiscontinua);
    }

    public static void main(String[] args) {
        CircunferenciaConMascara ventana = new CircunferenciaConMascara();
        ventana.dibujarCircunferencias();  // Dibujar las circunferencias con diferentes máscaras
    }
}
