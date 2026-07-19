package util;

/**
 * Excepción generada cuando un RUT no supera la validación.
 */
public class RutInvalidoException extends Exception {

    public RutInvalidoException(String mensaje) {
        super(mensaje);
    }
}