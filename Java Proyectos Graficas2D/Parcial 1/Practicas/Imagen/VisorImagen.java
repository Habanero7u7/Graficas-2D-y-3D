import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class VisorImagen extends JFrame {
    private JScrollPane panel;
    private Pantalla pantalla;

    // Constructor que recibe la ruta del archivo de la imagen
    public VisorImagen(String archivo) {
        super("Visor Imagen");

        // Cargar la imagen usando Toolkit
        Image img = Toolkit.getDefaultToolkit().getImage(archivo);
        pantalla = new Pantalla(img);
        
        // Crear el JScrollPane con barras de desplazamiento automáticas
        panel = new JScrollPane(pantalla, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        // Añadir el JScrollPane al JFrame
        getContentPane().add(panel);

        // Configuración de la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600); // Tamaño de la ventana
        setLocationRelativeTo(null); // Centrar la ventana
        setVisible(true); // Hacer visible la ventana
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Por favor, proporciona la ruta de la imagen como argumento.");
            System.exit(1);
        }
        
        // Crear una instancia del visor de imagen con la ruta proporcionada
        new VisorImagen(args[0]);
    }
}