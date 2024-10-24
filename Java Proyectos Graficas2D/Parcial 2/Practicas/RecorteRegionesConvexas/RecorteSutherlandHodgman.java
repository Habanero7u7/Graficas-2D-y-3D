import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

public class RecorteSutherlandHodgman extends JFrame {

    // Ventana de recorte definida por sus esquinas
    private int xmin = 100, ymin = 100, xmax = 400, ymax = 400;

    public RecorteSutherlandHodgman() {
        setTitle("Recorte de Polígono con Sutherland-Hodgman");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Clase para representar un punto (vértice)
    static class Punto {
        int x, y;
        Punto(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    // Método para dibujar un polígono
    public void dibujarPoligono(Graphics g, List<Punto> vertices, Color color) {
        g.setColor(color);
        for (int i = 0; i < vertices.size() - 1; i++) {
            g.drawLine(vertices.get(i).x, vertices.get(i).y,
                    vertices.get(i + 1).x, vertices.get(i + 1).y);
        }
        // Dibujar la última línea para cerrar el polígono
        g.drawLine(vertices.get(vertices.size() - 1).x, vertices.get(vertices.size() - 1).y,
                vertices.get(0).x, vertices.get(0).y);
    }

    // Algoritmo de recorte de Sutherland-Hodgman
    public List<Punto> recortarPoligono(List<Punto> vertices) {
        List<Punto> recortado = vertices;

        // Recortar contra cada arista del área de recorte
        recortado = recortarContraArista(recortado, xmin, ymin, xmax, ymin); // Top
        recortado = recortarContraArista(recortado, xmax, ymin, xmax, ymax); // Right
        recortado = recortarContraArista(recortado, xmax, ymax, xmin, ymax); // Bottom
        recortado = recortarContraArista(recortado, xmin, ymax, xmin, ymin); // Left

        return recortado;
    }

    // Método para recortar un polígono contra una arista específica
    private List<Punto> recortarContraArista(List<Punto> vertices, int x1, int y1, int x2, int y2) {
        List<Punto> resultado = new ArrayList<>();

        for (int i = 0; i < vertices.size(); i++) {
            Punto actual = vertices.get(i);
            Punto anterior = vertices.get((i - 1 + vertices.size()) % vertices.size());

            boolean dentroActual = estaDentro(actual, x1, y1, x2, y2);
            boolean dentroAnterior = estaDentro(anterior, x1, y1, x2, y2);

            if (dentroActual) {
                if (!dentroAnterior) {
                    resultado.add(interseccion(anterior, actual, x1, y1, x2, y2));
                }
                resultado.add(actual);
            } else if (dentroAnterior) {
                resultado.add(interseccion(anterior, actual, x1, y1, x2, y2));
            }
        }
        return resultado;
    }

    // Verificar si un punto está dentro del área de recorte
    private boolean estaDentro(Punto p, int x1, int y1, int x2, int y2) {
        return (x2 - x1) * (p.y - y1) >= (y2 - y1) * (p.x - x1);
    }

    // Calcular el punto de intersección entre una línea y una arista
    private Punto interseccion(Punto p1, Punto p2, int x1, int y1, int x2, int y2) {
        int dx = p2.x - p1.x;
        int dy = p2.y - p1.y;
        int num = (x2 - x1) * (p1.y - y1) - (y2 - y1) * (p1.x - x1);
        int den = (y2 - y1) * dx - (x2 - x1) * dy;

        if (den == 0) return p1; // Evitar división por cero

        double t = (double) num / den;
        return new Punto(p1.x + (int) (t * dx), p1.y + (int) (t * dy));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // Dibujar el área de recorte
        g.setColor(Color.RED);
        g.drawRect(xmin, ymin, xmax - xmin, ymax - ymin);

        // Definir un polígono arbitrario
        List<Punto> poligono = new ArrayList<>();
        poligono.add(new Punto(50, 50));
        poligono.add(new Punto(300, 50));
        poligono.add(new Punto(350, 300));
        poligono.add(new Punto(150, 400));
        poligono.add(new Punto(50, 300));

        // Dibujar el polígono original en negro
        dibujarPoligono(g, poligono, Color.BLACK);

        // Recortar el polígono y dibujarlo en azul
        List<Punto> poligonoRecortado = recortarPoligono(poligono);
        dibujarPoligono(g, poligonoRecortado, Color.BLUE);
    }

    public static void main(String[] args) {
        new RecorteSutherlandHodgman();
    }
}
