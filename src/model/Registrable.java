package model;

/**
 * Define el comportamiento común de las entidades
 * que pueden registrarse y mostrar sus datos.
 */
public interface Registrable {

    /**
     * Informa el registro de la entidad.
     */
    void registrar();

    /**
     * Muestra los datos de la entidad.
     */
    void mostrarDatos();
}