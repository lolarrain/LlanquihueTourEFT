package model;

import util.RutInvalidoException;

/**
 * Representa a un guía responsable de acompañar los tours.
 */
public class GuiaTuristico extends Persona implements Registrable {

    private String especialidad;

    public GuiaTuristico(
            String rut,
            String nombre,
            String telefono,
            Direccion direccion,
            String especialidad
    ) throws RutInvalidoException {
        super(rut, nombre, telefono, direccion);
        setEspecialidad(especialidad);
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        validarTexto(especialidad, "La especialidad");
        this.especialidad = especialidad.trim();
    }

    @Override
    public void registrar() {
        System.out.println(
                "Guía turístico registrado: " + getNombre()
        );
    }

    @Override
    public void mostrarDatos() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "GuiaTuristico{"
                + super.toString()
                + ", especialidad='" + especialidad + '\''
                + '}';
    }
}