import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class RelojAnalogicoConImagenes extends JPanel implements Runnable {
    private int horas;
    private int minutos;
    private int segundos;
    private Thread hilo;
    private Image fondoReloj;  // Imagen del fondo del reloj
    private Image manecillaHora, manecillaMinuto, manecillaSegundo;  // Imágenes de las manecillas
    private Image buffer;  // Doble buffer para evitar parpadeos
    private Graphics2D gBuffer;

    public RelojAnalogicoConImagenes() {
        // Cargar las imágenes del reloj y las manecillas
        try {
            fondoReloj = ImageIO.read(new File("Imagenes/relojsuperman.png"));  // Fondo del reloj
            manecillaHora = ImageIO.read(new File("Imagenes/manecilla horas.png"));  // Manecilla de la hora
            manecillaMinuto = ImageIO.read(new File("Imagenes/manecilla minutos.png"));  // Manecilla del minuto
            manecillaSegundo = ImageIO.read(new File("Imagenes/manecilla segundos.png"));  // Manecilla del segundo
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        actualizarTiempo();  // Obtener la hora del sistema
        iniciarHilo();  // Iniciar el hilo de actualización
    }

    private void actualizarTiempo() {
        // Obtener la hora actual del sistema
        Calendar calendario = Calendar.getInstance();
        horas = calendario.get(Calendar.HOUR);
        minutos = calendario.get(Calendar.MINUTE);
        segundos = calendario.get(Calendar.SECOND);
    }

    private void iniciarHilo() {
        // Iniciar un hilo para actualizar el reloj cada segundo
        hilo = new Thread(this);
        hilo.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                actualizarTiempo();  // Actualizar la hora cada segundo
                repaint();  // Redibujar el reloj
                Thread.sleep(1000);  // Pausar por 1 segundo
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Crear el doble buffer si es necesario
        if (buffer == null) {
            buffer = createImage(getWidth(), getHeight());
            gBuffer = (Graphics2D) buffer.getGraphics();
        }

        // Limpiar el buffer
        gBuffer.setColor(Color.BLACK);
        gBuffer.fillRect(0, 0, getWidth(), getHeight());

        // Centrar el reloj
        int centroX = getWidth() / 2;
        int centroY = getHeight() / 2;
        int radio = Math.min(centroX, centroY) - 1;  // Ajustar el radio del reloj

        // Dibujar el fondo del reloj
        gBuffer.drawImage(fondoReloj, centroX - radio, centroY - radio, radio * 2, radio * 2, null);

        // Aquí ajustamos el tamaño de las manecillas
        int longitudSegundo = (int) (radio * 0.75);  // 90% del radio para la manecilla de segundos
        int longitudMinuto = (int) (radio * 0.73);  // 75% del radio para la manecilla de minutos
        int longitudHora = (int) (radio * 0.40);  // 50% del radio para la manecilla de horas

        // Dibujar las manecillas sobre el fondo
        dibujarManecilla(gBuffer, manecillaMinuto, centroX, centroY, longitudMinuto, minutos * 6);  // Minutos
        dibujarManecilla(gBuffer, manecillaHora, centroX, centroY, longitudHora, horas * 30 + minutos / 2);  // Horas
        dibujarManecilla(gBuffer, manecillaSegundo, centroX, centroY, longitudSegundo, segundos * 6);  // Segundos

        // Dibujar el buffer en la pantalla
        g.drawImage(buffer, 0, 0, this);
    }

    private void dibujarManecilla(Graphics2D g, Image manecilla, int centroX, int centroY, int longitud, double angulo) {
        // Rotar la manecilla según el ángulo
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.rotate(Math.toRadians(angulo), centroX, centroY);  // Rotar alrededor del centro

        // Aquí se ajusta el tamaño de la imagen de la manecilla
        int anchoManecilla = (int) (manecilla.getWidth(null) * 0.5);  // Reducir el ancho al 70%
        int altoManecilla = (int) (manecilla.getHeight(null) * 0.4);  // Reducir el alto al 70%

        // Dibujar la imagen de la manecilla escalada
        int manecillaX = centroX - anchoManecilla / 2;
        int manecillaY = centroY - longitud;
        g2d.drawImage(manecilla, manecillaX, manecillaY, anchoManecilla, altoManecilla, null);

        g2d.dispose();  // Limpiar recursos gráficos
    }

    public static void main(String[] args) {
        JFrame ventana = new JFrame("Reloj Analógico con Imágenes");
        ventana.setUndecorated(true);
        ventana.setShape(new Ellipse2D.Double(0, 0, 700, 700));

        RelojAnalogicoConImagenes reloj = new RelojAnalogicoConImagenes();
        ventana.add(reloj);
        ventana.setSize(700, 700);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setVisible(true);
        ventana.setLocationRelativeTo(null);
        ventana.setBackground(new Color(0, 0, 0, 0));
    }
}
