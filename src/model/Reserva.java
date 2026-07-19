package model;

/**
 * Representa una reserva realizada por un cliente
 * para una salida programada.
 */
public class Reserva {

    private final String codigo;
    private Cliente cliente;
    private SalidaTour salidaTour;
    private int cantidadPersonas;
    private boolean activa;

    public Reserva(
            String codigo,
            Cliente cliente,
            SalidaTour salidaTour,
            int cantidadPersonas
    ) {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException(
                    "El código de reserva es obligatorio."
            );
        }

        validarCliente(cliente);
        validarSalida(salidaTour);
        validarCantidad(cantidadPersonas);

        salidaTour.reservarCupos(cantidadPersonas);

        this.codigo = codigo.trim().toUpperCase();
        this.cliente = cliente;
        this.salidaTour = salidaTour;
        this.cantidadPersonas = cantidadPersonas;
        this.activa = true;
    }

    public String getCodigo() {
        return codigo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public SalidaTour getSalidaTour() {
        return salidaTour;
    }

    public int getCantidadPersonas() {
        return cantidadPersonas;
    }

    public boolean isActiva() {
        return activa;
    }

    /**
     * Actualiza una reserva y coordina la liberación
     * y asignación de cupos.
     */
    public void editar(
            Cliente nuevoCliente,
            SalidaTour nuevaSalida,
            int nuevaCantidad
    ) {
        if (!activa) {
            throw new IllegalStateException(
                    "No se puede editar una reserva cancelada."
            );
        }

        validarCliente(nuevoCliente);
        validarSalida(nuevaSalida);
        validarCantidad(nuevaCantidad);

        SalidaTour salidaAnterior = salidaTour;
        int cantidadAnterior = cantidadPersonas;

        salidaAnterior.liberarCupos(cantidadAnterior);

        try {
            nuevaSalida.reservarCupos(nuevaCantidad);

            cliente = nuevoCliente;
            salidaTour = nuevaSalida;
            cantidadPersonas = nuevaCantidad;

        } catch (RuntimeException e) {
            salidaAnterior.reservarCupos(cantidadAnterior);

            throw new IllegalArgumentException(
                    "No fue posible editar la reserva: "
                            + e.getMessage()
            );
        }
    }

    public boolean cancelar() {
        if (!activa) {
            return false;
        }

        salidaTour.liberarCupos(cantidadPersonas);
        activa = false;

        return true;
    }

    public double calcularTotal() {
        return salidaTour.getTour().getPrecio()
                * cantidadPersonas;
    }

    private void validarCliente(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException(
                    "La reserva debe tener un cliente."
            );
        }
    }

    private void validarSalida(SalidaTour salida) {
        if (salida == null) {
            throw new IllegalArgumentException(
                    "La reserva debe tener una salida."
            );
        }
    }

    private void validarCantidad(int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException(
                    "La reserva debe incluir al menos una persona."
            );
        }
    }

    @Override
    public String toString() {
        return "Reserva{"
                + "codigo='" + codigo + '\''
                + ", cliente='" + cliente.getNombre() + '\''
                + ", tour='"
                + salidaTour.getTour().getNombre() + '\''
                + ", salida='" + salidaTour.getCodigo() + '\''
                + ", fecha=" + salidaTour.getFecha()
                + ", personas=" + cantidadPersonas
                + ", total=" + calcularTotal()
                + ", estado='"
                + (activa ? "ACTIVA" : "CANCELADA")
                + '\''
                + '}';
    }
}