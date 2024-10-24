import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JFrame;

public class MouseEventDemo extends JFrame implements MouseListener, MouseMotionListener, MouseWheelListener {

    public MouseEventDemo() {
        // Configuración del JFrame
        setTitle("Demo de eventos del mouse");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        // Agregar los listeners para capturar eventos del mouse
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
    }

    // Métodos para manejar eventos de MouseListener
    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("Mouse Clicked: " + e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("Mouse Pressed: " + e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println("Mouse Released: " + e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        System.out.println("Mouse Entered: " + e);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        System.out.println("Mouse Exited: " + e);
    }

    // Métodos para manejar eventos de MouseMotionListener
    @Override
    public void mouseDragged(MouseEvent e) {
        System.out.println("Mouse Dragged: " + e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        System.out.println("Mouse Moved: " + e);
    }

    // Método para manejar eventos de MouseWheelListener
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        System.out.println("Mouse Wheel Moved: " + e);
    }

    public static void main(String[] args) {
        new MouseEventDemo();
    }
}
