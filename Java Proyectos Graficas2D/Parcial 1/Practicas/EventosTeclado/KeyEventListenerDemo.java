import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class KeyEventListenerDemo extends JFrame implements KeyListener {

    public KeyEventListenerDemo() {
        // Configuración del JFrame
        setTitle("Eventos de Teclado en JFrame");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Hacer que el JFrame sea el foco para los eventos del teclado
        setFocusable(true);
        addKeyListener(this);  // Agregar el KeyListener al JFrame

        setVisible(true);
    }

    // Se ejecuta cuando se presiona una tecla
    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("Tecla presionada: " + KeyEvent.getKeyText(e.getKeyCode()) + 
                            " (Código: " + e.getKeyCode() + ")");
    }

    // Se ejecuta cuando se libera una tecla
    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("Tecla liberada: " + KeyEvent.getKeyText(e.getKeyCode()) + 
                            " (Código: " + e.getKeyCode() + ")");
    }

    // Se ejecuta cuando se escribe una tecla (teclas con caracteres imprimibles)
    @Override
    public void keyTyped(KeyEvent e) {
        System.out.println("Tecla escrita: " + e.getKeyChar());
    }

    public static void main(String[] args) {
        // Ejecutar en el Event Dispatch Thread
        SwingUtilities.invokeLater(KeyEventListenerDemo::new);
    }
}
