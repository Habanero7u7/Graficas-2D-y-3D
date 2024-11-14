import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Superficie3DConRotacionAutomatica extends JFrame implements Runnable {
    private BufferedImage buffer;
    private Graphics bufferGraphics;

    // Parámetros de la superficie toroidal
    private int puntosU = 50;       // Número de divisiones en el parámetro u
    private int puntosV = 50;       // Número de divisiones en el parámetro v
    private double[][][] vertices;  // Coordenadas de los puntos en la superficie
    private int[][][] vertices2D;   // Coordenadas proyectadas en 2D

    // Parámetros de la proyección y visualización
    private int offsetX = 400;      // Desplazamiento en X para centrar en la pantalla
    private int offsetY = 300;      // Desplazamiento en Y para centrar en la pantalla
    private int d = 2000;            // Distancia de la cámara (profundidad de perspectiva)

    // Ángulos de rotación
    private double angleX = 0;
    private double angleY = 0;
    private double angleZ = 0;

    // Velocidad de rotación
    private double rotationSpeedX = Math.toRadians(0.3);
    private double rotationSpeedY = Math.toRadians(0.2);
    private double rotationSpeedZ = Math.toRadians(0.1);

    public Superficie3DConRotacionAutomatica() {
        setTitle("Superficie 3D con Rotación Automática");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        buffer = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
        bufferGraphics = buffer.getGraphics();

        // Inicializar los puntos de la superficie toroidal
        vertices = new double[puntosU][puntosV][3];
        double r = 100;  // Radio menor (radio del tubo)
        double R = 200;  // Radio mayor (distancia al centro del toroide)
        for (int i = 0; i < puntosU; i++) {
            double u = i * 2 * Math.PI / (puntosU - 1);
            for (int j = 0; j < puntosV; j++) {
                double v = j * 2 * Math.PI / (puntosV - 1);
                vertices[i][j][0] = (R + r * Math.cos(v)) * Math.cos(u);  // x
                vertices[i][j][1] = (R + r * Math.cos(v)) * Math.sin(u);  // y
                vertices[i][j][2] = r * Math.sin(v);                      // z
            }
        }

        calcularProyeccion2D();
        setVisible(true);

        // Iniciar la rotación automática en un hilo separado
        Thread animationThread = new Thread(this);
        animationThread.start();
    }

    // Método para proyectar los puntos en 2D usando proyección en perspectiva
    private void calcularProyeccion2D() {
        vertices2D = new int[puntosU][puntosV][2];

        for (int i = 0; i < puntosU; i++) {
            for (int j = 0; j < puntosV; j++) {
                double[] verticeRotado = rotarVertice(vertices[i][j]);
                int x = (int) verticeRotado[0];
                int y = (int) verticeRotado[1];
                int z = (int) verticeRotado[2];

                // Aplicar fórmula de proyección en perspectiva
                int x2D = (int) (x * d / (z + d)) + offsetX;
                int y2D = (int) (y * d / (z + d)) + offsetY;

                vertices2D[i][j][0] = x2D;
                vertices2D[i][j][1] = y2D;
            }
        }
    }

    // Método para rotar un vértice en 3D
    private double[] rotarVertice(double[] vertice) {
        double x = vertice[0];
        double y = vertice[1];
        double z = vertice[2];

        // Rotación alrededor del eje X
        double yX = y * Math.cos(angleX) - z * Math.sin(angleX);
        double zX = y * Math.sin(angleX) + z * Math.cos(angleX);
        y = yX;
        z = zX;

        // Rotación alrededor del eje Y
        double xY = x * Math.cos(angleY) + z * Math.sin(angleY);
        double zY = -x * Math.sin(angleY) + z * Math.cos(angleY);
        x = xY;
        z = zY;

        // Rotación alrededor del eje Z
        double xZ = x * Math.cos(angleZ) - y * Math.sin(angleZ);
        double yZ = x * Math.sin(angleZ) + y * Math.cos(angleZ);
        x = xZ;
        y = yZ;

        return new double[] {x, y, z};
    }

    // Método para dibujar la superficie toroidal (proyectada en 2D)
    public void dibujarSuperficie() {
        for (int i = 0; i < puntosU - 1; i++) {
            for (int j = 0; j < puntosV - 1; j++) {
                dibujarLinea(vertices2D[i][j][0], vertices2D[i][j][1], vertices2D[i + 1][j][0], vertices2D[i + 1][j][1], Color.CYAN);
                dibujarLinea(vertices2D[i][j][0], vertices2D[i][j][1], vertices2D[i][j + 1][0], vertices2D[i][j + 1][1], Color.CYAN);
            }
        }
    }

    // Método para dibujar una línea recta entre dos puntos
    public void dibujarLinea(int x0, int y0, int x1, int y1, Color color) {
        int dx = Math.abs(x1 - x0), dy = Math.abs(y1 - y0);
        int sx = (x0 < x1) ? 1 : -1;
        int sy = (y0 < y1) ? 1 : -1;
        int err = dx - dy;

        while (true) {
            putPixel(x0, y0, color);
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

    public void putPixel(int x, int y, Color color) {
        if (x >= 0 && x < buffer.getWidth() && y >= 0 && y < buffer.getHeight()) {
            buffer.setRGB(x, y, color.getRGB());
        }
    }

    @Override
    public void paint(Graphics g) {
        inicializarFondo();
        calcularProyeccion2D();
        dibujarSuperficie();
        g.drawImage(buffer, 0, 0, null);
    }

    public void inicializarFondo() {
        bufferGraphics.setColor(Color.BLACK);
        bufferGraphics.fillRect(0, 0, buffer.getWidth(), buffer.getHeight());
    }

    @Override
    public void run() {
        while (true) {
            // Incrementar los ángulos para la rotación automática con diferentes velocidades en cada eje
            angleX += rotationSpeedX;
            angleY += rotationSpeedY;
            angleZ += rotationSpeedZ;
            repaint();

            try {
                Thread.sleep(20); // Controla la velocidad de la animación
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Superficie3DConRotacionAutomatica();
    }
}