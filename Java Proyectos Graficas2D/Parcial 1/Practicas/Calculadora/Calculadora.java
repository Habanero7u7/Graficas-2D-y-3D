import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Calculadora extends JFrame implements ActionListener {
    // Elementos de la interfaz
    private JTextField pantalla;
    private JButton[] botones;
    private String[] etiquetas = {
        "C", "/", "*", "-", 
        "7", "8", "9", "+", 
        "4", "5", "6", "=", 
        "1", "2", "3", "", 
        "0", "."};

    private String operador;
    private double operando1, operando2;
    private boolean nuevaOperacion = true;

    public Calculadora() {
        setTitle("Calculadora");
        setLayout(new BorderLayout());
        setResizable(false);  // No permitir redimensionar
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crear un panel superior para contener la barra de título personalizada y la pantalla
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new BorderLayout());

        // Barra de título personalizada
        JPanel barraTitulo = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnMinimizar = new JButton("_");
        JButton btnMaximizar = new JButton("□");
        JButton btnCerrar = new JButton("X");

        btnMinimizar.addActionListener(e -> setState(Frame.ICONIFIED));  // Minimizar ventana
        btnMaximizar.addActionListener(e -> setExtendedState(getExtendedState() ^ JFrame.MAXIMIZED_BOTH));  // Maximizar/restaurar ventana
        btnCerrar.addActionListener(e -> System.exit(0));  // Cerrar ventana

        barraTitulo.add(btnMinimizar);
        barraTitulo.add(btnMaximizar);
        barraTitulo.add(btnCerrar);

        // Crear la pantalla de la calculadora
        pantalla = new JTextField("0");
        pantalla.setHorizontalAlignment(JTextField.RIGHT);  // Alinear texto a la derecha
        pantalla.setEditable(false);  // No editable
        pantalla.setFont(new Font("Arial", Font.PLAIN, 20));
        
        // Añadir la barra de título personalizada y la pantalla al panel superior
        panelSuperior.add(barraTitulo, BorderLayout.NORTH);
        panelSuperior.add(pantalla, BorderLayout.CENTER);

        // Añadir el panel superior al JFrame
        add(panelSuperior, BorderLayout.NORTH);

        // Crear los botones y el panel que los contiene
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(5, 4, 5, 5));  // 5 filas, 4 columnas, 5px de margen

        botones = new JButton[etiquetas.length];

        for (int i = 0; i < etiquetas.length; i++) {
            if (etiquetas[i].equals("0")) {
                // Hacer que el botón "0" ocupe dos espacios
                botones[i] = new JButton(etiquetas[i]);
                botones[i].setFont(new Font("Arial", Font.PLAIN, 20));
                panelBotones.add(botones[i], new GridBagConstraints(0, 0, 2, 1, 1.0, 1.0, 
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
                botones[i].addActionListener(this);
            } else if (!etiquetas[i].equals("")) {
                botones[i] = new JButton(etiquetas[i]);
                botones[i].setFont(new Font("Arial", Font.PLAIN, 20));
                panelBotones.add(botones[i]);
                botones[i].addActionListener(this);
            } else {
                panelBotones.add(new JLabel());  // Espacio vacío en lugar de botón
            }
        }

        add(panelBotones, BorderLayout.CENTER);

        // Configuración final de la ventana
        setSize(300, 400);
        setLocationRelativeTo(null);  // Centrar en la pantalla
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        if (comando.equals("C")) {
            pantalla.setText("0");
            nuevaOperacion = true;
            operador = "";
            operando1 = 0;
            operando2 = 0;
        } else if (comando.equals("=")) {
            operando2 = Double.parseDouble(pantalla.getText());
            switch (operador) {
                case "+":
                    operando1 += operando2;
                    break;
                case "-":
                    operando1 -= operando2;
                    break;
                case "*":
                    operando1 *= operando2;
                    break;
                case "/":
                    if (operando2 != 0) {
                        operando1 /= operando2;
                    } else {
                        pantalla.setText("Error");
                        nuevaOperacion = true;
                        return;
                    }
                    break;
            }
            pantalla.setText(Double.toString(operando1));
            nuevaOperacion = true;
        } else if ("+-*/".contains(comando)) {
            operador = comando;
            operando1 = Double.parseDouble(pantalla.getText());
            nuevaOperacion = true;
        } else {
            if (nuevaOperacion) {
                pantalla.setText(comando);
                nuevaOperacion = false;
            } else {
                pantalla.setText(pantalla.getText() + comando);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Calculadora::new);
    }
}
