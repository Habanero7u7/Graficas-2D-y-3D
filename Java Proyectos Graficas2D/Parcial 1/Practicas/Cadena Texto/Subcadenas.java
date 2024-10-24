public class Subcadenas {
    public static void main(String[] args) {
        // Verificar que se ha proporcionado un argumento
        if (args.length != 1) {
            System.out.println("Por favor, proporciona una cadena de texto.");
            return;
        }

        String texto = args[0];
        int longitud = texto.length();

        // Primera parte: subcadenas eliminando caracteres del final
        for (int i = longitud; i > 0; i--) {
            System.out.println("\"" + texto.substring(0, i) + "\"");
        }

        // Segunda parte: subcadenas eliminando caracteres del principio, pero manteniendo el final completo
        for (int i = longitud; i > 1; i--) {
            String subcadena = texto.substring(i);
                if (!subcadena.isEmpty()) {
                System.out.println("\"" + subcadena + "\"");
            }
        }

        // Para mantener el formato exacto del ejemplo
        System.out.println("\"" + texto + "\"");
    }
}