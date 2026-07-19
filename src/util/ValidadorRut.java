package util;

/**
 * Proporciona métodos para validar y formatear RUT chilenos.
 */
public final class ValidadorRut {

    private ValidadorRut() {
    }

    public static boolean esValido(String rut) {
        if (rut == null || rut.isBlank()) {
            return false;
        }

        String rutLimpio = limpiar(rut);

        if (!rutLimpio.matches("\\d{7,8}[0-9K]")) {
            return false;
        }

        String cuerpo = rutLimpio.substring(
                0,
                rutLimpio.length() - 1
        );

        char digitoIngresado =
                rutLimpio.charAt(rutLimpio.length() - 1);

        return digitoIngresado
                == calcularDigitoVerificador(cuerpo);
    }

    public static String limpiar(String rut) {
        if (rut == null) {
            return "";
        }

        return rut
                .replace(".", "")
                .replace("-", "")
                .replace(" ", "")
                .toUpperCase();
    }

    public static String formatear(String rut)
            throws RutInvalidoException {

        if (!esValido(rut)) {
            throw new RutInvalidoException(
                    "El RUT no es válido: " + rut
            );
        }

        String limpio = limpiar(rut);
        int posicionDigito = limpio.length() - 1;

        return limpio.substring(0, posicionDigito)
                + "-"
                + limpio.charAt(posicionDigito);
    }

    private static char calcularDigitoVerificador(
            String cuerpo
    ) {
        int suma = 0;
        int multiplicador = 2;

        for (int i = cuerpo.length() - 1; i >= 0; i--) {
            int digito =
                    Character.getNumericValue(cuerpo.charAt(i));

            suma += digito * multiplicador;
            multiplicador++;

            if (multiplicador > 7) {
                multiplicador = 2;
            }
        }

        int resultado = 11 - (suma % 11);

        if (resultado == 11) {
            return '0';
        }

        if (resultado == 10) {
            return 'K';
        }

        return Character.forDigit(resultado, 10);
    }
}