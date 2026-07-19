package model;

import util.RutInvalidoException;
import util.ValidadorRut;

/**
 * Representa los datos comunes de las personas relacionadas
 * con Llanquihue Tour.
 */
public abstract class Persona {

    private String rut;
    private String nombre;
    private String telefono;
    private Direccion direccion;

    /**
     * Construye una persona con sus datos generales.
     *
     * @param rut RUT de la persona.
     * @param nombre nombre completo.
     * @param telefono teléfono de contacto.
     * @param direccion dirección de la persona.
     * @throws RutInvalidoException si el RUT no es válido.
     */
    public Persona(
            String rut,
            String nombre,
            String telefono,
            Direccion direccion
    ) throws RutInvalidoException {
        setRut(rut);
        setNombre(nombre);
        setTelefono(telefono);
        setDireccion(direccion);
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) throws RutInvalidoException {
        this.rut = ValidadorRut.formatear(rut);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        validarTexto(nombre, "El nombre");
        this.nombre = nombre.trim();
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        validarTexto(telefono, "El teléfono");
        this.telefono = telefono.trim();
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        if (direccion == null) {
            throw new IllegalArgumentException(
                    "La dirección no puede ser nula."
            );
        }

        this.direccion = direccion;
    }

    protected void validarTexto(String texto, String campo) {
        if (texto == null || texto.isBlank()) {
            throw new IllegalArgumentException(
                    campo + " no puede estar vacío."
            );
        }
    }

    @Override
    public String toString() {
        return "rut='" + rut + '\''
                + ", nombre='" + nombre + '\''
                + ", telefono='" + telefono + '\''
                + ", direccion=" + direccion;
    }
}