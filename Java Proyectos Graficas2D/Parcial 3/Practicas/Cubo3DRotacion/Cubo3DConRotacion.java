import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Cubo3DConRotacion extends JFrame implements KeyListener {
    private BufferedImage buffer;
    private Graphics bufferGraphics;

    // Parámetros del cubo
    private double[][] vertices;   // Coordenadas de los vértices en 3D
    private int[][] vertices2D;    // Coordenadas proyectadas en 2D
    private int size = 100;        // Tamaño del cubo
    private int offsetX = 250;     // Desplazamiento en X para centrar en la pantalla
    private int offsetY = 250;     // Desplazamiento en Y para centrar en la pantalla
    private int d = 300;           // Distancia de la cámara (profundidad de perspectiva)

    // Ángulos de rotación
    private double angleX = 0;
    private double angleY = 0;
    private double angleZ = 0;

    public Cubo3DConRotacion() {
        setTitle("Cubo 3D con Proyección en Perspectiva y Rotación");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        addKeyListener(this);

        buffer = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB);
        bufferGraphics = buffer.getGraphics();
        
        // Inicializar los vértices del cubo en 3D
        vertices = new double[][] {
            {-size, -size, -size}, {size, -size, -size}, {size, size, -size}, {-size, size, -size},
            {-size, -size, size}, {size, -size, size}, {size, size, size}, {-size, size, size}
        };
        
        calcularProyeccion2D();
        setVisible(true);
    }

    // Método para proyectar los puntos en 2D usando proyección en perspectiva
    private void calcularProyeccion2D() {
        vertices2D = new int[8][2];

        for (int i = 0; i < vertices.length; i++) {
            double[] verticeRotado = rotarVertice(vertices[i]);
            int x = (int) verticeRotado[0];
            int y = (int) verticeRotado[1];
            int z = (int) verticeRotado[2];

            // Aplicar fórmula de proyección en perspectiva
            int x2D = (int) (x * d / (z + d)) + offsetX;
            int y2D = (int) (y * d / (z + d)) + offsetY;

            vertices2D[i][0] = x2D;
            vertices2D[i][1] = y2D;
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

    // Método para dibujar el cubo usando las líneas entre los vértices proyectados
    public void dibujarCubo() {
        // Aristas de la base trasera
        dibujarLinea(vertices2D[0][0], vertices2D[0][1], vertices2D[1][0], vertices2D[1][1], Color.BLUE);
        dibujarLinea(vertices2D[1][0], vertices2D[1][1], vertices2D[2][0], vertices2D[2][1], Color.BLUE);
        dibujarLinea(vertices2D[2][0], vertices2D[2][1], vertices2D[3][0], vertices2D[3][1], Color.BLUE);
        dibujarLinea(vertices2D[3][0], vertices2D[3][1], vertices2D[0][0], vertices2D[0][1], Color.BLUE);

        // Aristas de la base frontal
        dibujarLinea(vertices2D[4][0], vertices2D[4][1], vertices2D[5][0], vertices2D[5][1], Color.RED);
        dibujarLinea(vertices2D[5][0], vertices2D[5][1], vertices2D[6][0], vertices2D[6][1], Color.RED);
        dibujarLinea(vertices2D[6][0], vertices2D[6][1], vertices2D[7][0], vertices2D[7][1], Color.RED);
        dibujarLinea(vertices2D[7][0], vertices2D[7][1], vertices2D[4][0], vertices2D[4][1], Color.RED);

        // Aristas que conectan las dos bases
        for (int i = 0; i < 4; i++) {
            dibujarLinea(vertices2D[i][0], vertices2D[i][1], vertices2D[i + 4][0], vertices2D[i + 4][1], Color.GREEN);
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
        dibujarCubo();
        g.drawImage(buffer, 0, 0, null);
    }

    public void inicializarFondo() {
        bufferGraphics.setColor(Color.BLACK);
        bufferGraphics.fillRect(0, 0, buffer.getWidth(), buffer.getHeight());
    }

    // Implementación del KeyListener para controlar la rotación con el teclado
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_UP:
                angleX -= Math.toRadians(5);
                break;
            case KeyEvent.VK_DOWN:
                angleX += Math.toRadians(5);
                break;
            case KeyEvent.VK_LEFT:
                angleY -= Math.toRadians(5);
                break;
            case KeyEvent.VK_RIGHT:
                angleY += Math.toRadians(5);
                break;
            case KeyEvent.VK_A:
                angleZ -= Math.toRadians(5);
                break;
            case KeyEvent.VK_D:
                angleZ += Math.toRadians(5);
                break;
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // No se requiere implementación
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // No se requiere implementación
    }

    public static void main(String[] args) {
        new Cubo3DConRotacion();
    }
}