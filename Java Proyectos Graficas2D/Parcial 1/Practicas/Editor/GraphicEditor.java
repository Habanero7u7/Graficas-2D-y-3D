import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class GraphicEditor extends JFrame {
    // Enumeración de tipos de figuras
    enum Figura { PUNTO, LINEA, RECTANGULO, CIRCULO }
    
    private Figura figuraSeleccionada = Figura.PUNTO;  // Figura predeterminada
    private Color colorSeleccionado = Color.BLACK;     // Color predeterminado
    private int startX, startY, endX, endY;            // Coordenadas de inicio y fin
    private BufferedImage buffer;                      // Imagen para el doble buffer
    private Graphics2D g2d;                            // Contexto gráfico
    private boolean dibujando = false;                 // Bandera para saber si se está dibujando
    private DrawPanel drawPanel;                       // Panel para dibujar

    public GraphicEditor() {
        // Configuración del JFrame
        setTitle("Editor Gráfico");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crear panel de dibujo
        drawPanel = new DrawPanel();
        add(drawPanel, BorderLayout.CENTER);

        // Crear menú de selección de figuras y colores
        crearMenu();
        
        // Hacer visible la ventana
        setVisible(true);
    }

    private void crearMenu() {
        JMenuBar menuBar = new JMenuBar();

        // Menú para figuras
        JMenu menuFiguras = new JMenu("Figuras");
        JMenuItem punto = new JMenuItem("Punto");
        JMenuItem linea = new JMenuItem("Línea");
        JMenuItem rectangulo = new JMenuItem("Rectángulo");
        JMenuItem circulo = new JMenuItem("Círculo");

        punto.addActionListener(e -> figuraSeleccionada = Figura.PUNTO);
        linea.addActionListener(e -> figuraSeleccionada = Figura.LINEA);
        rectangulo.addActionListener(e -> figuraSeleccionada = Figura.RECTANGULO);
        circulo.addActionListener(e -> figuraSeleccionada = Figura.CIRCULO);

        menuFiguras.add(punto);
        menuFiguras.add(linea);
        menuFiguras.add(rectangulo);
        menuFiguras.add(circulo);
        menuBar.add(menuFiguras);

        // Menú para colores
        JMenu menuColores = new JMenu("Colores");
        JMenuItem negro = new JMenuItem("Negro");
        JMenuItem rojo = new JMenuItem("Rojo");
        JMenuItem verde = new JMenuItem("Verde");
        JMenuItem azul = new JMenuItem("Azul");

        negro.addActionListener(e -> colorSeleccionado = Color.BLACK);
        rojo.addActionListener(e -> colorSeleccionado = Color.RED);
        verde.addActionListener(e -> colorSeleccionado = Color.GREEN);
        azul.addActionListener(e -> colorSeleccionado = Color.BLUE);

        menuColores.add(negro);
        menuColores.add(rojo);
        menuColores.add(verde);
        menuColores.add(azul);
        menuBar.add(menuColores);

        // Agregar la barra de menús al JFrame
        setJMenuBar(menuBar);
    }

    private class DrawPanel extends JPanel implements MouseListener, MouseMotionListener {

        public DrawPanel() {
            // Inicialización del buffer y su contexto gráfico
            buffer = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
            g2d = buffer.createGraphics();
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, 800, 600);

            // Agregar listeners del mouse
            addMouseListener(this);
            addMouseMotionListener(this);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(buffer, 0, 0, null);
        }

        // Dibujar la figura seleccionada en el buffer
        private void dibujarFiguraTemporal(Graphics g) {
            g.setColor(colorSeleccionado);
            switch (figuraSeleccionada) {
                case PUNTO:
                    g.fillOval(startX, startY, 5, 5);
                    break;
                case LINEA:
                    g.drawLine(startX, startY, endX, endY);
                    break;
                case RECTANGULO:
                    g.drawRect(Math.min(startX, endX), Math.min(startY, endY),
                               Math.abs(startX - endX), Math.abs(startY - endY));
                    break;
                case CIRCULO:
                    int radio = Math.max(Math.abs(startX - endX), Math.abs(startY - endY));
                    g.drawOval(Math.min(startX, endX), Math.min(startY, endY), radio, radio);
                    break;
            }
        }

        // Al presionar el mouse, se obtiene la posición inicial
        @Override
        public void mousePressed(MouseEvent e) {
            startX = e.getX();
            startY = e.getY();
            dibujando = true;
        }

        // Mientras se arrastra el mouse, se dibuja la figura de manera temporal
        @Override
        public void mouseDragged(MouseEvent e) {
            if (dibujando) {
                endX = e.getX();
                endY = e.getY();

                // Redibujar el buffer original y luego la figura temporal
                repaint(); // Redibujar el JPanel
                Graphics g = getGraphics();
                dibujarFiguraTemporal(g);         // Dibujar la figura temporal
            }
        }

        // Al soltar el mouse, se dibuja la figura de manera permanente en el buffer
        @Override
        public void mouseReleased(MouseEvent e) {
            if (dibujando) {
                endX = e.getX();
                endY = e.getY();

                // Dibujar la figura en el buffer permanente
                dibujarFiguraTemporal(g2d);

                dibujando = false;
                repaint();
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {}
        @Override
        public void mouseEntered(MouseEvent e) {}
        @Override
        public void mouseExited(MouseEvent e) {}
        @Override
        public void mouseMoved(MouseEvent e) {}
    }

    public static void main(String[] args) {
        // Ejecutar en el Event Dispatch Thread para evitar problemas de concurrencia
        SwingUtilities.invokeLater(() -> {
            GraphicEditor editor = new GraphicEditor();
            editor.setVisible(true);
        });
    }
}