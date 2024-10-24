import java.util.Arrays;

public class OrdenarNumeros {
    public static void main(String[] args) {
        // Verificar si se han proporcionado argumentos
        if (args.length == 0) {
            System.out.println("No se proporcionaron números para ordenar.");
            return;
        }

        // Convertir los argumentos de String a int
        int[] numeros = new int[args.length];
        
        try {
            for (int i = 0; i < args.length; i++){
                numeros[i] = Integer.parseInt(args[i]);
            }
        } catch (NumberFormatException e) {
            System.out.println("Un argumento no es un número válido.");
            return;
        }

        // Ordenar el arreglo de números
        Arrays.sort(numeros);

        // Imprimir la lista de números ordenada
        System.out.println("Lista de números ordenada de menor a mayor:");
        for (int numero : numeros) {
            System.out.print(numero + " ");
        }
        System.out.println();
    }
}