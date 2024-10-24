import java.awt.Graphics;

import javax.swing.JFrame;

public class Monito extends JFrame {

    // Constructor que asigna el título a la ventana
    public Monito(String title) {
        super(title); // Llama al constructor de JFrame para asignar el título
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g); // Asegurarse de que se dibuje correctamente la ventana

        g.drawString("Dibujar Monito", 10, 50);

        // Obtener el ancho y alto de la ventana
        int anchoVentana = getWidth();
        int altoVentana = getHeight();

        // Coordenadas para centrar el monito
        int centroX = anchoVentana / 2;
        int centroY = altoVentana / 2;

        // Dibujar la carita (círculo)
        int radioCabeza = 50;
        g.drawArc(centroX - radioCabeza / 2, centroY - 150, radioCabeza, radioCabeza, 0, 360); // Cabeza
        g.drawArc(centroX - 15, centroY - 130, 30, 20, 180, 180); // Sonrisa
        g.fillOval(centroX - 15, centroY - 140, 10, 10); // Ojo izquierdo
        g.fillOval(centroX + 5, centroY - 140, 10, 10); // Ojo derecho

        // Dibujar el cuerpo
        g.drawLine(centroX, centroY - 100, centroX, centroY + 50); // Cuerpo

        // Dibujar los brazos
        g.drawLine(centroX, centroY - 80, centroX - 50, centroY - 40); // Brazo izquierdo
        g.drawLine(centroX, centroY - 80, centroX + 50, centroY - 40); // Brazo derecho

        // Dibujar las piernas
        g.drawLine(centroX, centroY + 50, centroX - 40, centroY + 120); // Pierna izquierda
        g.drawLine(centroX, centroY + 50, centroX + 40, centroY + 120); // Pierna derecha
    }

    public static void main(String[] args) {
        // Crear una instancia de Monito con el título "Monito"
        Monito monito = new Monito("Monito");

        // Configuración de la ventana
        monito.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        monito.setSize(200, 400); // Tamaño de la ventana
        monito.setLocationRelativeTo(null); // Centrar la ventana
        monito.setVisible(true); // Hacer la ventana visible
    }
}
