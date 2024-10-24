public class Hex2IPconvertidora {

    // Método para convertir dirección IP a formato hexadecimal
    public String ipToHex(String ipAddress) {
        // Dividir la dirección IP en partes usando el punto como separador
        String[] octetos = ipAddress.split("\\.");
        if (octetos.length != 4) {
            throw new IllegalArgumentException("Dirección IP inválida: " + ipAddress);
        }

        // Convertir cada octeto a hexadecimal y concatenar
        StringBuilder hexString = new StringBuilder();
        for (String octeto : octetos) {
            int num = Integer.parseInt(octeto);
            if (num < 0 || num > 255) {
                throw new IllegalArgumentException("Cada octeto debe estar entre 0 y 255.");
            }
            hexString.append(String.format("%02X", num));
        }

        return hexString.toString();
    }

    // Método para convertir hexadecimal a dirección IP
    public String hexToIp(String hexString) {
        if (hexString.length() != 8) {
            throw new IllegalArgumentException("Cadena hexadecimal debe tener 8 dígitos.");
        }

        // Convertir cada par de dígitos hexadecimales a un número decimal
        StringBuilder ipAddress = new StringBuilder();
        for (int i = 0; i < 8; i += 2) {
            String hexOctet = hexString.substring(i, i + 2);
            int num = Integer.parseInt(hexOctet, 16);
            if (i > 0) {
                ipAddress.append(".");
            }
            ipAddress.append(num);
        }

        return ipAddress.toString();
    }

    // Método principal para ejecutar el programa
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Uso: java Hex2IPconvertidora <-hex|-ip> <cadena>");
            return;
        }

        Hex2IPconvertidora convertidora = new Hex2IPconvertidora();
        String option = args[0];
        String value = args[1];

        try {
            if ("-hex".equals(option)) {
                String hex = convertidora.ipToHex(value);
                System.out.println("Dirección IP a Hexadecimal: " + hex);
            } else if ("-ip".equals(option)) {
                String ip = convertidora.hexToIp(value);
                System.out.println("Hexadecimal a Dirección IP: " + ip);
            } else {
                System.out.println("Opción no válida. Use -hex para IP a hexadecimal o -ip para hexadecimal a IP.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
