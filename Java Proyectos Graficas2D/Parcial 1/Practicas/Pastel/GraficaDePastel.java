import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GraficaDePastel extends JPanel {
    private int[] valores; // Valores para los sectores de la gráfica
    private String[] etiquetas; // Etiquetas para cada sector
    private Color[] colores = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.ORANGE, Color.MAGENTA};

    public GraficaDePastel(int[] valores) {
        this.valores = valores;
        this.etiquetas = new String[valores.length];
        // Generar etiquetas por defecto
        for (int i = 0; i < valores.length; i++) {
            etiquetas[i] = "Valor: " + (i + 1);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int anchoVentana = getWidth();
        int altoVentana = getHeight();
        
        // Centro del área donde se dibujará el pastel
        int centroX = anchoVentana / 3;
        int centroY = altoVentana / 2;
        int radio = Math.min(anchoVentana, altoVentana) / 3;

        // Total de valores para calcular los ángulos
        int total = 0;
        for (int valor : valores) {
            total += valor;
        }

        // Dibujar los sectores del gráfico de pastel
        int startAngle = 0;
        for (int i = 0; i < valores.length; i++) {
            int arcAngle = (int) Math.round((valores[i] * 360.0 / total));
            g2d.setColor(colores[i % colores.length]); // Asignar color a cada sector
            g2d.fillArc(centroX - radio, centroY - radio, 2 * radio, 2 * radio, startAngle, arcAngle);
            startAngle += arcAngle;
        }

        // Dibujar las etiquetas a la derecha del gráfico
        int etiquetaX = centroX + radio + 30;
        int etiquetaY = centroY - (valores.length * 20 / 2); // Centrar etiquetas verticalmente
        for (int i = 0; i < valores.length; i++) {
            g2d.setColor(colores[i % colores.length]);
            g2d.fillRect(etiquetaX, etiquetaY + i * 30, 20, 20); // Dibujar cuadro de color

            g2d.setColor(Color.BLACK);
            g2d.drawString(etiquetas[i] + " (" + valores[i] + ")", etiquetaX + 30, etiquetaY + i * 30 + 15); // Dibujar etiqueta con valor
        }
    }

    public static void main(String[] args) {
        // Validar si se han pasado suficientes argumentos
        if (args.length == 0) {
            System.out.println("Por favor, ingresa los valores de los sectores de la gráfica de pastel.");
            System.exit(1);
        }

        // Convertir los argumentos en enteros
        int[] valores = new int[args.length];
        try {
            for (int i = 0; i < args.length; i++) {
                valores[i] = Integer.parseInt(args[i]);
            }
        } catch (NumberFormatException e) {
            System.out.println("Por favor, asegúrate de que todos los valores sean enteros.");
            System.exit(1);
        }

        // Crear el JFrame para mostrar la gráfica de pastel
        JFrame ventana = new JFrame("Gráfica de Pastel");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(400, 400);
        ventana.setLocationRelativeTo(null); // Centrar ventana
        ventana.add(new GraficaDePastel(valores));
        ventana.setVisible(true);
    }
}