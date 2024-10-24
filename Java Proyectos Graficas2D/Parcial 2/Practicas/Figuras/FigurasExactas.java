import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class FigurasExactas extends JFrame {
    private BufferedImage buffer;
    public FigurasExactas() {
        // Configuración del JFrame
        setTitle("Dibujo Exacto de Figuras Básicas");
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

    // Algoritmo de Bresenham para dibujar líneas
    public void dibujarLinea(int x0, int y0, int x1, int y1) {
        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);
        int sx = (x0 < x1) ? 1 : -1;
        int sy = (y0 < y1) ? 1 : -1;
        int err = dx - dy;

        while (true) {
            putPixel(x0, y0, Color.BLACK);

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
        }
    }

    // Algoritmo de punto medio para dibujar un círculo
    public void dibujarCirculo(int xc, int yc, int radio) {
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

    // Dibuja los puntos simétricos del círculo
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

    // Método para dibujar un rectángulo utilizando líneas
    public void dibujarRectangulo(int x0, int y0, int x1, int y1) {
        dibujarLinea(x0, y0, x1, y0); // Línea superior
        dibujarLinea(x0, y1, x1, y1); // Línea inferior
        dibujarLinea(x0, y0, x0, y1); // Línea izquierda
        dibujarLinea(x1, y0, x1, y1); // Línea derecha
    }

    // Método para dibujar una elipse completa utilizando el algoritmo de punto medio
    public void dibujarElipse(int xc, int yc, int a, int b) {
        int x = 0;
        int y = b;
        int a2 = a * a;
        int b2 = b * b;
        int d = b2 - (a2 * b) + (a2 / 4);

        while (b2 * x <= a2 * y) {
            dibujarSimetriaElipse(xc, yc, x, y);
            if (d < 0) {
                d += b2 * (2 * x + 3);
            } else {
                d += b2 * (2 * x + 3) + a2 * (-2 * y + 2);
                y--;
            }
            x++;
        }

        d = (int) (b2 * (x + 0.5) * (x + 0.5) + a2 * (y - 1) * (y - 1) - a2 * b2);
        while (y >= 0) {
            dibujarSimetriaElipse(xc, yc, x, y);
            if (d > 0) {
                d += a2 * (-2 * y + 3);
            } else {
                d += b2 * (2 * x + 2) + a2 * (-2 * y + 3);
                x++;
            }
            y--;
        }
    }

    // Dibuja los puntos simétricos de la elipse
    private void dibujarSimetriaElipse(int xc, int yc, int x, int y) {
        putPixel(xc + x, yc + y, Color.BLACK);
        putPixel(xc - x, yc + y, Color.BLACK);
        putPixel(xc + x, yc - y, Color.BLACK);
        putPixel(xc - x, yc - y, Color.BLACK);
    }

    // Método para dibujar todas las figuras exactas
    public void dibujarFiguras() {
        // Dibujar líneas inclinadas y horizontal
        dibujarLinea(50, 50, 150, 150);
        dibujarLinea(200, 50, 300, 50);
        dibujarLinea(350, 150, 450, 50);
        dibujarLinea(550, 150, 650, 150);

        // Dibujar círculos concéntricos con círculo más pequeño
        dibujarCirculo(150, 400, 30);
        dibujarCirculo(150, 400, 60);
        dibujarCirculo(150, 400, 90);
        dibujarCirculo(150, 400, 10); // Círculo más pequeño

        // Dibujar rectángulo exterior con uno interior
        dibujarRectangulo(300, 350, 450, 450);
        dibujarRectangulo(330, 380, 420, 420);

        // Dibujar elipses concéntricas completas
        dibujarElipse(600, 400, 50, 30);
        dibujarElipse(600, 400, 80, 50);
        dibujarElipse(600, 400, 110, 70);
        dibujarElipse(600, 400, 30, 15); // Elipse más pequeña
    }

    public static void main(String[] args) {
        FigurasExactas ventana = new FigurasExactas();
        ventana.dibujarFiguras();  // Dibujar las figuras exactas
    }
}
