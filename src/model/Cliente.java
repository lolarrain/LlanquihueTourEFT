package model;

import util.RutInvalidoException;

/**
 * Representa a un cliente que puede realizar reservas.
 */
public class Cliente extends Persona implements Registrable {

    private String correo;

    public Cliente(
            String rut,
            String nombre,
            String telefono,
            Direccion direccion,
            String correo
    ) throws RutInvalidoException {
        super(rut, nombre, telefono, direccion);
        setCorreo(correo);
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        validarTexto(correo, "El correo");

        if (!correo.contains("@")) {
            throw new IllegalArgumentException(
                    "El correo debe contener @."
            );
        }

        this.correo = correo.trim();
    }

    @Override
    public void registrar() {
        System.out.println(
                "Cliente registrado: " + getNombre()
        );
    }

    @Override
    public void mostrarDatos() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "Cliente{"
                + super.toString()
                + ", correo='" + correo + '\''
                + '}';
    }
}