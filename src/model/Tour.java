package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa un producto turístico ofrecido por Llanquihue Tour.
 *
 * Las fechas, cupos y guías se administran mediante SalidaTour.
 */
public class Tour {

    private String codigo;
    private String nombre;
    private String destino;
    private double precio;
    private final List<String> itinerario;

    /**
     * Construye un tour con sus datos comerciales.
     *
     * @param codigo identificador único.
     * @param nombre nombre del tour.
     * @param destino lugar de realización.
     * @param precio precio por persona.
     */
    public Tour(
            String codigo,
            String nombre,
            String destino,
            double precio
    ) {
        itinerario = new ArrayList<>();

        setCodigo(codigo);
        setNombre(nombre);
        setDestino(destino);
        setPrecio(precio);
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        validarTexto(codigo, "El código");
        this.codigo = codigo.trim().toUpperCase();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        validarTexto(nombre, "El nombre");
        this.nombre = nombre.trim();
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        validarTexto(destino, "El destino");
        this.destino = destino.trim();
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        if (precio <= 0) {
            throw new IllegalArgumentException(
                    "El precio debe ser mayor que cero."
            );
        }

        this.precio = precio;
    }

    public List<String> getItinerario() {
        return new ArrayList<>(itinerario);
    }

    public void agregarActividad(String actividad) {
        validarTexto(actividad, "La actividad");
        itinerario.add(actividad.trim());
    }

    /**
     * Reemplaza completamente el itinerario.
     *
     * @param actividades nuevo listado de actividades.
     */
    public void reemplazarItinerario(
            List<String> actividades
    ) {
        if (actividades == null) {
            throw new IllegalArgumentException(
                    "El itinerario no puede ser nulo."
            );
        }

        for (String actividad : actividades) {
            validarTexto(actividad, "La actividad");
        }

        itinerario.clear();

        for (String actividad : actividades) {
            itinerario.add(actividad.trim());
        }
    }

    public void mostrarItinerario() {
        System.out.println("Itinerario:");

        if (itinerario.isEmpty()) {
            System.out.println("No hay actividades registradas.");
            return;
        }

        for (int i = 0; i < itinerario.size(); i++) {
            System.out.println(
                    (i + 1) + ". " + itinerario.get(i)
            );
        }
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
        return "Tour{"
                + "codigo='" + codigo + '\''
                + ", nombre='" + nombre + '\''
                + ", destino='" + destino + '\''
                + ", precio=" + precio
                + '}';
    }
}