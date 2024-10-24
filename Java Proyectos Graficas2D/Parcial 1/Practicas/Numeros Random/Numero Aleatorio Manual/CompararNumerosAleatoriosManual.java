public class CompararNumerosAleatoriosManual {
    public static void main(String[] args) {
        // Definir una semilla manual
        int semilla1 = 12345;  // Un número inicial para el primer número
        int semilla2 = 54321;  // Un número inicial para el segundo número

        // Generar pseudo-números aleatorios usando una fórmula simple
        int numero1 = (semilla1 * 13 + 17) % 101;  // Fórmula para obtener un número entre 0 y 100
        int numero2 = (semilla2 * 29 + 31) % 101;  // Otra fórmula para el segundo número entre 0 y 100

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