import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class RecorteExplícito extends JFrame {
    private BufferedImage buffer;
    // Constantes para las regiones del algoritmo de Cohen-Sutherland
    private static final int INSIDE = 0; // 0000
    private static final int LEFT = 1;   // 0001
    private static final int RIGHT = 2;  // 0010
    private static final int BOTTOM = 4; // 0100
    private static final int TOP = 8;    // 1000

    // Definición del rectángulo de recorte
    private int xmin = 100, ymin = 100, xmax = 400, ymax = 400;

    public RecorteExplícito() {
        // Configuración del JFrame
        setTitle("Recorte Explícito de Líneas");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        // Inicialización del buffer para dibujar un pixel
        buffer = new BufferedImage(600, 600, BufferedImage.TYPE_INT_RGB);
        this.getGraphics();
        inicializarFondo(); // Establecer fondo blanco
    }

    public void putPixel(int x, int y, Color c) {
        buffer.setRGB(x, y, c.getRGB());
        this.getGraphics().drawImage(buffer, 0, 0, this);
    }

    public void inicializarFondo() {
        for (int y = 0; y < buffer.getHeight(); y++) {
            for (int x = 0; x < buffer.getWidth(); x++) {
                buffer.setRGB(x, y, Color.WHITE.getRGB());
            }
        }
    }

    // Obtener el código de región de un punto (algoritmo de Cohen-Sutherland)
    private int calcularCodigo(int x, int y) {
        int codigo = INSIDE;

        if (x < xmin) codigo |= LEFT;
        else if (x > xmax) codigo |= RIGHT;

        if (y < ymin) codigo |= TOP;
        else if (y > ymax) codigo |= BOTTOM;

        return codigo;
    }

    // Algoritmo de recorte explícito (Cohen-Sutherland)
    public void recortarYdibujarLinea(int x0, int y0, int x1, int y1) {
        int codigo0 = calcularCodigo(x0, y0);
        int codigo1 = calcularCodigo(x1, y1);
        boolean aceptada = false;

        while (true) {
            if ((codigo0 | codigo1) == 0) { // Ambos puntos están dentro del área de recorte
                aceptada = true;
                break;
            } else if ((codigo0 & codigo1) != 0) { // Ambos puntos están fuera del mismo lado
                break;
            } else {
                // Al menos un punto está fuera; calcular el punto de intersección
                int codigoExterno = (codigo0 != 0) ? codigo0 : codigo1;
                int x = 0, y = 0;

                if ((codigoExterno & TOP) != 0) { // Punto está arriba del rectángulo
                    x = x0 + (x1 - x0) * (ymin - y0) / (y1 - y0);
                    y = ymin;
                } else if ((codigoExterno & BOTTOM) != 0) { // Punto está abajo del rectángulo
                    x = x0 + (x1 - x0) * (ymax - y0) / (y1 - y0);
                    y = ymax;
                } else if ((codigoExterno & RIGHT) != 0) { // Punto está a la derecha del rectángulo
                    y = y0 + (y1 - y0) * (xmax - x0) / (x1 - x0);
                    x = xmax;
                } else if ((codigoExterno & LEFT) != 0) { // Punto está a la izquierda del rectángulo
                    y = y0 + (y1 - y0) * (xmin - x0) / (x1 - x0);
                    x = xmin;
                }

                // Reemplazar el punto fuera del área con el punto de intersección
                if (codigoExterno == codigo0) {
                    x0 = x;
                    y0 = y;
                    codigo0 = calcularCodigo(x0, y0);
                } else {
                    x1 = x;
                    y1 = y;
                    codigo1 = calcularCodigo(x1, y1);
                }
            }
        }

        if (aceptada) {
            dibujarLinea(x0, y0, x1, y1);
        }
    }

    // Algoritmo de Bresenham para dibujar una línea
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

    // Dibujar varias líneas y recortar según el área de recorte
    public void dibujarLineasConRecorte() {
        // Dibujar el rectángulo de recorte
        dibujarLinea(xmin, ymin, xmax, ymin);
        dibujarLinea(xmax, ymin, xmax, ymax);
        dibujarLinea(xmax, ymax, xmin, ymax);
        dibujarLinea(xmin, ymax, xmin, ymin);

        // Dibujar varias líneas con recorte
        recortarYdibujarLinea(50, 50, 300, 300);
        recortarYdibujarLinea(200, 50, 500, 300);
        recortarYdibujarLinea(100, 400, 300, 100);
        recortarYdibujarLinea(400, 200, 600, 400);
    }

    public static void main(String[] args) {
        RecorteExplícito ventana = new RecorteExplícito();
        ventana.dibujarLineasConRecorte();
    }
}
