package model;

import util.RutInvalidoException;

/**
 * Representa a un proveedor de servicios turísticos.
 */
public class Proveedor extends Persona implements Registrable {

    private String tipoServicio;

    public Proveedor(
            String rut,
            String nombre,
            String telefono,
            Direccion direccion,
            String tipoServicio
    ) throws RutInvalidoException {
        super(rut, nombre, telefono, direccion);
        setTipoServicio(tipoServicio);
    }

    public String getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(String tipoServicio) {
        validarTexto(tipoServicio, "El tipo de servicio");
        this.tipoServicio = tipoServicio.trim();
    }

    @Override
    public void registrar() {
        System.out.println(
                "Proveedor registrado: " + getNombre()
        );
    }

    @Override
    public void mostrarDatos() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "Proveedor{"
                + super.toString()
                + ", tipoServicio='" + tipoServicio + '\''
                + '}';
    }
}