import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Pantalla extends JPanel {
    private Image imagen;

    // Constructor que recibe la imagen
    public Pantalla(Image imagen) {
        this.imagen = imagen;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // Usamos ImageIcon para obtener las dimensiones de la imagen
        ImageIcon icon = new ImageIcon(imagen);
        Dimension tam = new Dimension(icon.getIconWidth(), icon.getIconHeight());

        // Ajustar el tamaño del panel según la imagen
        setPreferredSize(tam);
        setMinimumSize(tam);
        setMaximumSize(tam);
        setSize(tam);

        // Dibujar la imagen en el panel
        g.drawImage(imagen, 0, 0, this);
    }
}
