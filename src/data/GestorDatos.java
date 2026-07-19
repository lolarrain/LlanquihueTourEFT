package data;

import model.Cliente;
import model.Direccion;
import model.GuiaTuristico;
import model.Persona;
import model.Proveedor;
import model.Registrable;
import model.Reserva;
import model.SalidaTour;
import model.Tour;
import util.RutInvalidoException;
import util.ValidadorRut;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Administra las colecciones y reglas operativas del sistema.
 *
 * La lectura de archivos pertenece a LectorDatos.
 */
public class GestorDatos {

    private final List<Persona> personas;
    private final List<Tour> tours;
    private final List<SalidaTour> salidas;
    private final List<Reserva> reservas;

    public GestorDatos() {
        personas = new ArrayList<>();
        tours = new ArrayList<>();
        salidas = new ArrayList<>();
        reservas = new ArrayList<>();
    }

    /**
     * Construye el gestor con datos previamente leídos.
     */
    public GestorDatos(
            List<Persona> personas,
            List<Tour> tours,
            List<SalidaTour> salidas
    ) {
        this();

        for (Persona persona : personas) {
            agregarPersona(persona);
        }

        for (Tour tour : tours) {
            agregarTour(tour);
        }

        for (SalidaTour salida : salidas) {
            agregarSalida(salida);
        }
    }

    public void agregarPersona(Persona persona) {
        if (persona == null) {
            throw new IllegalArgumentException(
                    "La persona no puede ser nula."
            );
        }

        if (buscarPersona(persona.getRut()) != null) {
            throw new IllegalArgumentException(
                    "El RUT ya se encuentra registrado."
            );
        }

        personas.add(persona);
    }

    public Cliente registrarCliente(
            String rut,
            String nombre,
            String telefono,
            Direccion direccion,
            String correo
    ) throws RutInvalidoException {
        Cliente cliente = new Cliente(
                rut,
                nombre,
                telefono,
                direccion,
                correo
        );

        agregarPersona(cliente);
        return cliente;
    }

    public GuiaTuristico registrarGuiaTuristico(
            String rut,
            String nombre,
            String telefono,
            Direccion direccion,
            String especialidad
    ) throws RutInvalidoException {
        GuiaTuristico guia = new GuiaTuristico(
                rut,
                nombre,
                telefono,
                direccion,
                especialidad
        );

        agregarPersona(guia);
        return guia;
    }

    public Proveedor registrarProveedor(
            String rut,
            String nombre,
            String telefono,
            Direccion direccion,
            String tipoServicio
    ) throws RutInvalidoException {
        Proveedor proveedor = new Proveedor(
                rut,
                nombre,
                telefono,
                direccion,
                tipoServicio
        );

        agregarPersona(proveedor);
        return proveedor;
    }

    public Persona buscarPersona(String rut) {
        String rutBuscado = ValidadorRut.limpiar(rut);

        for (Persona persona : personas) {
            if (ValidadorRut.limpiar(persona.getRut())
                    .equals(rutBuscado)) {
                return persona;
            }
        }

        return null;
    }

    /**
     * Sobrecarga de búsqueda por nombre y comuna.
     */
    public List<Persona> buscarPersona(
            String nombre,
            String comuna
    ) {
        List<Persona> resultados = new ArrayList<>();

        for (Persona persona : personas) {
            boolean coincideNombre = persona.getNombre()
                    .toLowerCase()
                    .contains(nombre.trim().toLowerCase());

            boolean coincideComuna = persona.getDireccion()
                    .getComuna()
                    .equalsIgnoreCase(comuna.trim());

            if (coincideNombre && coincideComuna) {
                resultados.add(persona);
            }
        }

        return resultados;
    }

    public Cliente buscarCliente(String rut) {
        Persona persona = buscarPersona(rut);

        if (persona == null) {
            return null;
        }

        if (persona instanceof Cliente cliente) {
            return cliente;
        }

        throw new IllegalArgumentException(
                "El RUT pertenece a una persona que no es cliente."
        );
    }

    public List<Persona> filtrarPersonas(
            String tipo,
            String comuna
    ) {
        List<Persona> resultados = new ArrayList<>();
        String tipoBuscado = tipo == null ? "" : tipo.trim();
        String comunaBuscada = comuna == null ? "" : comuna.trim();

        for (Persona persona : personas) {
            boolean coincideTipo =
                    tipoBuscado.isBlank()
                            || coincideTipo(persona, tipoBuscado);

            boolean coincideComuna =
                    comunaBuscada.isBlank()
                            || persona.getDireccion()
                            .getComuna()
                            .equalsIgnoreCase(comunaBuscada);

            if (coincideTipo && coincideComuna) {
                resultados.add(persona);
            }
        }

        return resultados;
    }

    private boolean coincideTipo(
            Persona persona,
            String tipo
    ) {
        return switch (tipo.toUpperCase()) {
            case "CLIENTE" -> persona instanceof Cliente;
            case "PROVEEDOR" -> persona instanceof Proveedor;
            case "GUIA" -> persona instanceof GuiaTuristico;
            default -> false;
        };
    }

    public List<Registrable> obtenerRegistrables() {
        List<Registrable> registros = new ArrayList<>();

        for (Persona persona : personas) {
            if (persona instanceof Registrable registrable) {
                registros.add(registrable);
            }
        }

        return registros;
    }

    public void mostrarPersonas() {
        List<Registrable> registros = obtenerRegistrables();

        if (registros.isEmpty()) {
            System.out.println("No hay personas registradas.");
            return;
        }

        for (Registrable registro : registros) {
            registro.mostrarDatos();
        }
    }

    public void editarPersona(
            String rut,
            String nombre,
            String telefono,
            Direccion direccion,
            String datoEspecifico
    ) {
        Persona persona = buscarPersona(rut);

        if (persona == null) {
            throw new IllegalArgumentException(
                    "No existe una persona con ese RUT."
            );
        }

        persona.setNombre(nombre);
        persona.setTelefono(telefono);
        persona.setDireccion(direccion);

        if (persona instanceof Cliente cliente) {
            cliente.setCorreo(datoEspecifico);
        } else if (persona instanceof GuiaTuristico guia) {
            guia.setEspecialidad(datoEspecifico);
        } else if (persona instanceof Proveedor proveedor) {
            proveedor.setTipoServicio(datoEspecifico);
        }
    }

    public void agregarTour(Tour tour) {
        if (tour == null) {
            throw new IllegalArgumentException(
                    "El tour no puede ser nulo."
            );
        }

        if (buscarTour(tour.getCodigo()) != null) {
            throw new IllegalArgumentException(
                    "El código del tour ya está registrado."
            );
        }

        tours.add(tour);
    }

    public Tour registrarTour(
            String codigo,
            String nombre,
            String destino,
            double precio,
            List<String> actividades
    ) {
        Tour tour = new Tour(
                codigo,
                nombre,
                destino,
                precio
        );

        tour.reemplazarItinerario(actividades);
        agregarTour(tour);

        return tour;
    }

    public Tour buscarTour(String codigo) {
        if (codigo == null) {
            return null;
        }

        for (Tour tour : tours) {
            if (tour.getCodigo()
                    .equalsIgnoreCase(codigo.trim())) {
                return tour;
            }
        }

        return null;
    }

    public void editarTour(
            String codigo,
            String nombre,
            String destino,
            double precio,
            List<String> actividades
    ) {
        Tour tour = buscarTour(codigo);

        if (tour == null) {
            throw new IllegalArgumentException(
                    "No existe un tour con ese código."
            );
        }

        tour.setNombre(nombre);
        tour.setDestino(destino);
        tour.setPrecio(precio);
        tour.reemplazarItinerario(actividades);
    }

    public List<Tour> filtrarTours(
            String destino,
            Double precioMinimo,
            Double precioMaximo
    ) {
        List<Tour> resultados = new ArrayList<>();
        String destinoBuscado =
                destino == null ? "" : destino.trim().toLowerCase();

        if (precioMinimo != null
                && precioMaximo != null
                && precioMinimo > precioMaximo) {
            throw new IllegalArgumentException(
                    "El precio mínimo no puede superar al máximo."
            );
        }

        for (Tour tour : tours) {
            boolean coincideDestino =
                    destinoBuscado.isBlank()
                            || tour.getDestino()
                            .toLowerCase()
                            .contains(destinoBuscado);

            boolean coincideMinimo =
                    precioMinimo == null
                            || tour.getPrecio() >= precioMinimo;

            boolean coincideMaximo =
                    precioMaximo == null
                            || tour.getPrecio() <= precioMaximo;

            if (coincideDestino
                    && coincideMinimo
                    && coincideMaximo) {
                resultados.add(tour);
            }
        }

        return resultados;
    }

    public void agregarSalida(SalidaTour salida) {
        if (salida == null) {
            throw new IllegalArgumentException(
                    "La salida no puede ser nula."
            );
        }

        if (buscarSalida(salida.getCodigo()) != null) {
            throw new IllegalArgumentException(
                    "El código de salida ya está registrado."
            );
        }

        salidas.add(salida);
    }

    public SalidaTour registrarSalida(
            String codigo,
            String codigoTour,
            LocalDate fecha,
            int cupoMaximo,
            String rutGuia
    ) {
        Tour tour = buscarTour(codigoTour);

        if (tour == null) {
            throw new IllegalArgumentException(
                    "No existe el tour indicado."
            );
        }

        Persona persona = buscarPersona(rutGuia);

        if (!(persona instanceof GuiaTuristico guia)) {
            throw new IllegalArgumentException(
                    "No existe un guía con ese RUT."
            );
        }

        SalidaTour salida = new SalidaTour(
                codigo,
                tour,
                fecha,
                cupoMaximo,
                guia
        );

        agregarSalida(salida);
        return salida;
    }

    public SalidaTour buscarSalida(String codigo) {
        if (codigo == null) {
            return null;
        }

        for (SalidaTour salida : salidas) {
            if (salida.getCodigo()
                    .equalsIgnoreCase(codigo.trim())) {
                return salida;
            }
        }

        return null;
    }

    public void editarSalida(
            String codigo,
            String codigoTour,
            LocalDate fecha,
            int cupoMaximo,
            String rutGuia
    ) {
        SalidaTour salida = buscarSalida(codigo);
        Tour tour = buscarTour(codigoTour);
        Persona persona = buscarPersona(rutGuia);

        if (salida == null) {
            throw new IllegalArgumentException(
                    "No existe la salida indicada."
            );
        }

        if (tour == null) {
            throw new IllegalArgumentException(
                    "No existe el tour indicado."
            );
        }

        if (!(persona instanceof GuiaTuristico guia)) {
            throw new IllegalArgumentException(
                    "No existe un guía con ese RUT."
            );
        }

        salida.editar(
                tour,
                fecha,
                cupoMaximo,
                guia
        );
    }

    public List<SalidaTour> filtrarSalidasPorTour(
            String codigoTour
    ) {
        List<SalidaTour> resultados = new ArrayList<>();

        for (SalidaTour salida : salidas) {
            if (salida.getTour()
                    .getCodigo()
                    .equalsIgnoreCase(codigoTour.trim())) {
                resultados.add(salida);
            }
        }

        return resultados;
    }

    public Reserva crearReserva(
            String codigo,
            String rutCliente,
            String codigoSalida,
            int cantidadPersonas
    ) {
        if (buscarReserva(codigo) != null) {
            throw new IllegalArgumentException(
                    "El código de reserva ya está registrado."
            );
        }

        Cliente cliente = buscarCliente(rutCliente);

        if (cliente == null) {
            throw new IllegalArgumentException(
                    "El cliente no está registrado."
            );
        }

        SalidaTour salida = buscarSalida(codigoSalida);

        if (salida == null) {
            throw new IllegalArgumentException(
                    "No existe la salida indicada."
            );
        }

        Reserva reserva = new Reserva(
                codigo,
                cliente,
                salida,
                cantidadPersonas
        );

        reservas.add(reserva);
        return reserva;
    }

    public Reserva buscarReserva(String codigo) {
        if (codigo == null) {
            return null;
        }

        for (Reserva reserva : reservas) {
            if (reserva.getCodigo()
                    .equalsIgnoreCase(codigo.trim())) {
                return reserva;
            }
        }

        return null;
    }

    public void editarReserva(
            String codigo,
            String rutCliente,
            String codigoSalida,
            int cantidadPersonas
    ) {
        Reserva reserva = buscarReserva(codigo);
        Cliente cliente = buscarCliente(rutCliente);
        SalidaTour salida = buscarSalida(codigoSalida);

        if (reserva == null) {
            throw new IllegalArgumentException(
                    "No existe la reserva indicada."
            );
        }

        if (cliente == null) {
            throw new IllegalArgumentException(
                    "No existe el cliente indicado."
            );
        }

        if (salida == null) {
            throw new IllegalArgumentException(
                    "No existe la salida indicada."
            );
        }

        reserva.editar(
                cliente,
                salida,
                cantidadPersonas
        );
    }

    public boolean cancelarReserva(String codigo) {
        Reserva reserva = buscarReserva(codigo);

        return reserva != null && reserva.cancelar();
    }

    public List<Reserva> filtrarReservas(
            LocalDate fecha,
            Boolean estadoActivo,
            String rutCliente,
            String codigoTour
    ) {
        List<Reserva> resultados = new ArrayList<>();
        String rutBuscado =
                ValidadorRut.limpiar(rutCliente);

        String tourBuscado =
                codigoTour == null ? "" : codigoTour.trim();

        for (Reserva reserva : reservas) {
            boolean coincideFecha =
                    fecha == null
                            || reserva.getSalidaTour()
                            .getFecha()
                            .equals(fecha);

            boolean coincideEstado =
                    estadoActivo == null
                            || reserva.isActiva() == estadoActivo;

            boolean coincideCliente =
                    rutBuscado.isBlank()
                            || ValidadorRut.limpiar(
                            reserva.getCliente().getRut()
                    ).equals(rutBuscado);

            boolean coincideTour =
                    tourBuscado.isBlank()
                            || reserva.getSalidaTour()
                            .getTour()
                            .getCodigo()
                            .equalsIgnoreCase(tourBuscado);

            if (coincideFecha
                    && coincideEstado
                    && coincideCliente
                    && coincideTour) {
                resultados.add(reserva);
            }
        }

        return resultados;
    }

    public void mostrarTours() {
        mostrarLista(tours, "No hay tours registrados.");
    }

    public void mostrarSalidas() {
        mostrarLista(salidas, "No hay salidas programadas.");
    }

    public void mostrarReservas() {
        mostrarLista(reservas, "No hay reservas registradas.");
    }

    private void mostrarLista(
            List<?> elementos,
            String mensajeVacio
    ) {
        if (elementos.isEmpty()) {
            System.out.println(mensajeVacio);
            return;
        }

        for (Object elemento : elementos) {
            System.out.println(elemento);
        }
    }
}