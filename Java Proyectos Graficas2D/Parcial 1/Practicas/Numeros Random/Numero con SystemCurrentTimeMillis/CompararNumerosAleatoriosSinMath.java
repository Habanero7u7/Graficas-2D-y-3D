public class CompararNumerosAleatoriosSinMath {
    public static void main(String[] args) {
        // Obtener la hora actual del sistema en milisegundos
        long semilla1 = System.currentTimeMillis();

        // Esperar un tiempo para garantizar una semilla distinta para el segundo número
        try {
            Thread.sleep(10);  // Dormir el programa por 10 milisegundos
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long semilla2 = System.currentTimeMillis();

        // Generar números aleatorios utilizando operaciones sobre las semillas
        int numero1 = (int) (semilla1 % 101);  // Número entre 0 y 100
        int numero2 = (int) (semilla2 % 101);  // Número entre 0 y 100

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