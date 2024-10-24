import javax.swing.JFrame;

public class Ventana extends JFrame{
    public Ventana(){
        setSize(500, 500);
    }

    public static void main(String[] args) {
        Ventana jf1 = new Ventana();

        jf1.setVisible(true);
        jf1.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}