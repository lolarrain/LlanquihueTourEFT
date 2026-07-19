package util;

/**
 * Proporciona métodos para validar y formatear RUT.
 *
 * La validación comprueba únicamente su estructura,
 * permitiendo utilizar RUT de prueba.
 */
public final class ValidadorRut {

    /**
     * Impide crear objetos de esta clase utilitaria.
     */
    private ValidadorRut() {
    }

    /**
     * Comprueba que el RUT tenga un formato básico válido.
     *
     * Se aceptan siete u ocho números y un dígito
     * verificador numérico o la letra K.
     *
     * @param rut RUT que se desea validar.
     * @return true si cumple con la estructura esperada.
     */
    public static boolean esValido(String rut) {
        if (rut == null || rut.isBlank()) {
            return false;
        }

        String entrada = rut
                .replace(" ", "")
                .toUpperCase();

        boolean formatoConPuntos =
                entrada.matches(
                        "\\d{1,2}\\.\\d{3}\\.\\d{3}-?[0-9K]"
                );

        boolean formatoSinPuntos =
                entrada.matches(
                        "\\d{7,8}-?[0-9K]"
                );

        return formatoConPuntos || formatoSinPuntos;
    }

    /**
     * Elimina puntos, espacios y guion del RUT.
     *
     * @param rut RUT que se desea limpiar.
     * @return RUT sin caracteres de formato.
     */
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

    /**
     * Normaliza el RUT utilizando un guion antes
     * del dígito verificador.
     *
     * NO comprueba matemáticamente el dígito verificador.
     *
     * @param rut RUT que se desea formatear.
     * @return RUT normalizado.
     * @throws RutInvalidoException si el formato no es válido.
     */
    public static String formatear(String rut)
            throws RutInvalidoException {

        if (!esValido(rut)) {
            throw new RutInvalidoException(
                    "El formato del RUT no es válido: " + rut
            );
        }

        String rutLimpio = limpiar(rut);
        int posicionDigito = rutLimpio.length() - 1;

        return rutLimpio.substring(0, posicionDigito)
                + "-"
                + rutLimpio.charAt(posicionDigito);
    }
}