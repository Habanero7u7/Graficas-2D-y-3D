import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class EspiralArquimedes extends JPanel implements Runnable {
    private static final int MAX_PUNTOS = 1000; // Número máximo de puntos
    private int numPuntos = 0; // Contador de puntos dibujados

    public EspiralArquimedes() {
        // Crear un hilo para la ejecución del dibujo
        Thread hilo = new Thread(this);
        hilo.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        int lastX = centerX, lastY = centerY; // Puntos iniciales
        // Dibujar la espiral de Arquímedes usando líneas continuas
        for (int i = 0; i < numPuntos; i++) {
            double t = i * 0.1; // Factor para controlar el crecimiento de la espiral
            int x = (int) (centerX + t * Math.cos(t)); // Coordenada X del siguiente punto
            int y = (int) (centerY + t * Math.sin(t)); // Coordenada Y del siguiente punto

            // Dibujar línea desde el último punto hasta el nuevo punto
            g2d.drawLine(lastX, lastY, x, y);

            // Actualizar el último punto
            lastX = x;
            lastY = y;
        }
    }

    @Override
    public void run() {
        while (numPuntos < MAX_PUNTOS) {
            numPuntos++;
            repaint(); // Redibuja la espiral con más puntos
            try {
                TimeUnit.MILLISECONDS.sleep(10); // Retraso de 10 ms entre cada actualización
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) {
        JFrame ventana = new JFrame("Espiral de Arquímedes");
        EspiralArquimedes espiral = new EspiralArquimedes();
        ventana.add(espiral);
        ventana.setSize(300, 300);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setVisible(true);
        ventana.setLocationRelativeTo(null);
    }
}