import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class LineasConMascara extends JFrame {
    private BufferedImage buffer;
    public LineasConMascara() {
        // Configuración del JFrame
        setTitle("Dibujo de Líneas con Máscara");
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

    // Algoritmo para dibujar una línea con máscara
    public void dibujarLineaConMascara(int x0, int y0, int x1, int y1, int[] mascara) {
        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);
        int sx = (x0 < x1) ? 1 : -1;
        int sy = (y0 < y1) ? 1 : -1;
        int err = dx - dy;
        int i = 0;  // Índice para la máscara

        while (true) {
            // Dibujar solo si el bit de la máscara es 1
            if (mascara[i % mascara.length] == 1) {
                putPixel(x0, y0, Color.BLACK);
            }

            if (x0 == x1 && y0 == y1) break;

            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x0 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y0 += sy;
            }

            i++;  // Avanzar en la máscara
        }
    }

    // Método para dibujar varios tipos de líneas
    public void dibujarLineas() {
        // Línea sólida (máscara con todos los bits en 1)
        int[] mascaraSolida = {1, 1, 1, 1, 1, 1, 1, 1};
        dibujarLineaConMascara(50, 50, 300, 50, mascaraSolida);

        // Línea punteada (máscara alternada)
        int[] mascaraPunteada = {1, 0, 1, 0, 1, 0, 1, 0};
        dibujarLineaConMascara(50, 100, 300, 100, mascaraPunteada);

        // Línea discontinua (más segmentos largos)
        int[] mascaraDiscontinua = {1, 1, 1, 0, 0, 0, 1, 1};
        dibujarLineaConMascara(50, 150, 300, 150, mascaraDiscontinua);
    }

    public static void main(String[] args) {
        LineasConMascara ventana = new LineasConMascara();
        ventana.dibujarLineas();  // Dibujar las líneas con diferentes máscaras
    }
}
