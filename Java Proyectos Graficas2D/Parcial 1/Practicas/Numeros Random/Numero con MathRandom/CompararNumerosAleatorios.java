public class CompararNumerosAleatorios {
    public static void main(String[] args) {
        // Generar dos números aleatorios entre 0 y 100 utilizando Math.random()
        int numero1 = (int) (Math.random() * 101);  // Genera un número entre 0 y 100
        int numero2 = (int) (Math.random() * 101);  // Genera un número entre 0 y 100

        // Imprimir los números generados
        System.out.println("Número 1: " + numero1);
        System.out.println("Número 2: " + numero2);

        // Comparar los números e imprimir el resultado
        if (numero1 > numero2) {
            System.out.println("El número 1 (" + numero1 + ") es mayor que el número 2 (" + numero2 + ").");
        } else if (numero2 > numero1) {
            System.out.println("El número 2 (" + numero2 + ") es mayor que el número 1 (" + numero1 + ").");
        } else {
            System.out.println("Ambos números son iguales: " + numero1);
        }
    }
}