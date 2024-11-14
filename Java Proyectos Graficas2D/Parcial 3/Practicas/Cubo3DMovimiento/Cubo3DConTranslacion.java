import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Cubo3DConTranslacion extends JFrame implements KeyListener {
    private BufferedImage buffer;
    private Graphics bufferGraphics;

    // Parámetros del cubo
    private int[][] vertices;   // Coordenadas de los vértices en 3D
    private int[][] vertices2D; // Coordenadas proyectadas en 2D
    private int size = 100;     // Tamaño del cubo
    private int offsetX = 250;  // Desplazamiento en X para centrar en la pantalla
    private int offsetY = 250;  // Desplazamiento en Y para centrar en la pantalla
    private int d = 300;        // Distancia de la cámara (profundidad de perspectiva)

    // Variables para la translación
    private int translateX = 0;
    private int translateY = 0;
    private int translateZ = 0;

    public Cubo3DConTranslacion() {
        setTitle("Cubo 3D con Proyección en Perspectiva y Translación");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        addKeyListener(this);

        buffer = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB);
        bufferGraphics = buffer.getGraphics();
        
        // Inicializar los vértices del cubo en 3D
        vertices = new int[][] {
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
            int x = vertices[i][0] + translateX;
            int y = vertices[i][1] + translateY;
            int z = vertices[i][2] + translateZ;

            // Aplicar fórmula de proyección en perspectiva
            int x2D = (int) (x * d / (z + d)) + offsetX;
            int y2D = (int) (y * d / (z + d)) + offsetY;

            vertices2D[i][0] = x2D;
            vertices2D[i][1] = y2D;
        }
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

    // Implementación del KeyListener para controlar la translación con el teclado
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_UP:
                translateY -= 10;
                break;
            case KeyEvent.VK_DOWN:
                translateY += 10;
                break;
            case KeyEvent.VK_LEFT:
                translateX -= 10;
                break;
            case KeyEvent.VK_RIGHT:
                translateX += 10;
                break;
            case KeyEvent.VK_W:
                translateZ += 10;
                break;
            case KeyEvent.VK_S:
                translateZ -= 10;
                break;
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // No se requiere implementar
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // No se requiere implementar
    }

    public static void main(String[] args) {
        new Cubo3DConTranslacion();
    }
}