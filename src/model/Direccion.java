package model;

/**
 * Representa la dirección asociada a una persona.
 */
public class Direccion {

    private String calle;
    private String numero;
    private String comuna;
    private String region;

    /**
     * Construye una dirección completa.
     *
     * @param calle nombre de la calle.
     * @param numero número del domicilio.
     * @param comuna comuna correspondiente.
     * @param region región correspondiente.
     */
    public Direccion(
            String calle,
            String numero,
            String comuna,
            String region
    ) {
        setCalle(calle);
        setNumero(numero);
        setComuna(comuna);
        setRegion(region);
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        validarTexto(calle, "La calle");
        this.calle = calle.trim();
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        validarTexto(numero, "El número");
        this.numero = numero.trim();
    }

    public String getComuna() {
        return comuna;
    }

    public void setComuna(String comuna) {
        validarTexto(comuna, "La comuna");
        this.comuna = comuna.trim();
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        validarTexto(region, "La región");
        this.region = region.trim();
    }

    private void validarTexto(String texto, String campo) {
        if (texto == null || texto.isBlank()) {
            throw new IllegalArgumentException(
                    campo + " no puede estar vacío."
            );
        }
    }

    @Override
    public String toString() {
        return calle + " " + numero
                + ", " + comuna
                + ", " + region;
    }
}
