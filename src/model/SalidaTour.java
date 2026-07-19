package model;

import java.time.LocalDate;

/**
 * Representa una ejecución programada de un tour.
 *
 * Cada salida posee una fecha, un guía y una capacidad propia.
 */
public class SalidaTour {

    private final String codigo;
    private Tour tour;
    private LocalDate fecha;
    private int cupoMaximo;
    private int cuposDisponibles;
    private GuiaTuristico guiaResponsable;

    /**
     * Construye una salida programada.
     *
     * @param codigo identificador único de la salida.
     * @param tour producto turístico asociado.
     * @param fecha fecha de realización.
     * @param cupoMaximo capacidad de la salida.
     * @param guiaResponsable guía encargado.
     */
    public SalidaTour(
            String codigo,
            Tour tour,
            LocalDate fecha,
            int cupoMaximo,
            GuiaTuristico guiaResponsable
    ) {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException(
                    "El código de salida es obligatorio."
            );
        }

        this.codigo = codigo.trim().toUpperCase();

        setTour(tour);
        setFecha(fecha);
        setCupoMaximo(cupoMaximo);
        setGuiaResponsable(guiaResponsable);
    }

    public String getCodigo() {
        return codigo;
    }

    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        if (tour == null) {
            throw new IllegalArgumentException(
                    "La salida debe tener un tour."
            );
        }

        this.tour = tour;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        if (fecha == null) {
            throw new IllegalArgumentException(
                    "La fecha de salida es obligatoria."
            );
        }

        this.fecha = fecha;
    }

    public int getCupoMaximo() {
        return cupoMaximo;
    }

    /**
     * Modifica la capacidad sin invalidar reservas existentes.
     *
     * @param nuevoCupo nueva capacidad máxima.
     */
    public void setCupoMaximo(int nuevoCupo) {
        if (nuevoCupo <= 0) {
            throw new IllegalArgumentException(
                    "El cupo máximo debe ser mayor que cero."
            );
        }

        if (cupoMaximo == 0) {
            cupoMaximo = nuevoCupo;
            cuposDisponibles = nuevoCupo;
            return;
        }

        int cuposOcupados =
                cupoMaximo - cuposDisponibles;

        if (nuevoCupo < cuposOcupados) {
            throw new IllegalArgumentException(
                    "El nuevo cupo es menor que las reservas existentes."
            );
        }

        cupoMaximo = nuevoCupo;
        cuposDisponibles = nuevoCupo - cuposOcupados;
    }

    public int getCuposDisponibles() {
        return cuposDisponibles;
    }

    public GuiaTuristico getGuiaResponsable() {
        return guiaResponsable;
    }

    public void setGuiaResponsable(
            GuiaTuristico guiaResponsable
    ) {
        if (guiaResponsable == null) {
            throw new IllegalArgumentException(
                    "La salida debe tener un guía responsable."
            );
        }

        this.guiaResponsable = guiaResponsable;
    }

    /**
     * Actualiza los datos editables de la salida.
     */
    public void editar(
            Tour nuevoTour,
            LocalDate nuevaFecha,
            int nuevoCupo,
            GuiaTuristico nuevoGuia
    ) {
        if (nuevoTour == null
                || nuevaFecha == null
                || nuevoGuia == null) {
            throw new IllegalArgumentException(
                    "Los datos de la salida son obligatorios."
            );
        }

        setCupoMaximo(nuevoCupo);
        tour = nuevoTour;
        fecha = nuevaFecha;
        guiaResponsable = nuevoGuia;
    }

    public void reservarCupos(int cantidad) {
        validarCantidad(cantidad);

        if (cantidad > cuposDisponibles) {
            throw new IllegalArgumentException(
                    "No existen cupos suficientes. Disponibles: "
                            + cuposDisponibles
            );
        }

        cuposDisponibles -= cantidad;
    }

    public void liberarCupos(int cantidad) {
        validarCantidad(cantidad);

        if (cuposDisponibles + cantidad > cupoMaximo) {
            throw new IllegalStateException(
                    "No se puede superar el cupo máximo."
            );
        }

        cuposDisponibles += cantidad;
    }

    private void validarCantidad(int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException(
                    "La cantidad debe ser mayor que cero."
            );
        }
    }

    @Override
    public String toString() {
        return "SalidaTour{"
                + "codigo='" + codigo + '\''
                + ", tour='" + tour.getNombre() + '\''
                + ", fecha=" + fecha
                + ", cupoMaximo=" + cupoMaximo
                + ", cuposDisponibles=" + cuposDisponibles
                + ", guia='" + guiaResponsable.getNombre() + '\''
                + '}';
    }
}
