package app;

import data.GestorDatos;
import model.Cliente;
import model.Direccion;
import model.GuiaTuristico;
import model.Persona;
import model.Proveedor;
import model.Reserva;
import model.SalidaTour;
import model.Tour;
import util.RutInvalidoException;
import util.ValidadorRut;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Administra la interacción por consola entre el usuario
 * y las operaciones disponibles en Llanquihue Tour.
 *
 * La clase se ocupa exclusivamente de presentar menús,
 * solicitar datos y mostrar resultados.
 */
public class MenuConsola {

    private static final DateTimeFormatter FORMATO_FECHA =
            DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private final GestorDatos gestor;
    private final Scanner scanner;

    /**
     * Construye el menú utilizando el gestor del sistema.
     *
     * @param gestor gestor que administra las entidades.
     */
    public MenuConsola(GestorDatos gestor) {
        if (gestor == null) {
            throw new IllegalArgumentException(
                    "El gestor no puede ser nulo."
            );
        }

        this.gestor = gestor;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Inicia el menú principal y lo mantiene activo
     * hasta que el usuario selecciona la salida.
     */
    public void iniciar() {
        int opcion;

        do {
            mostrarMenuPrincipal();
            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1 -> menuPersonas();
                case 2 -> menuReservas();
                case 3 -> menuTours();
                case 4 -> menuFiltros();
                case 5 -> System.out.println(
                        "Programa finalizado."
                );
                default -> System.out.println(
                        "La opción ingresada no es válida."
                );
            }

        } while (opcion != 5);

        scanner.close();
    }

    /**
     * Muestra las áreas principales del sistema.
     */
    private void mostrarMenuPrincipal() {
        System.out.println();
        System.out.println("=== LLANQUIHUE TOUR ===");
        System.out.println("1. Personas");
        System.out.println("2. Reservas");
        System.out.println("3. Tours");
        System.out.println("4. Búsqueda y filtros");
        System.out.println("5. Salir");
    }

    /**
     * Administra las operaciones relacionadas con personas.
     */
    private void menuPersonas() {
        int opcion;

        do {
            System.out.println();
            System.out.println("=== PERSONAS ===");
            System.out.println("1. Ver todas las personas");
            System.out.println("2. Registrar nueva persona");
            System.out.println("3. Editar persona");
            System.out.println("0. Volver");

            opcion = leerEntero("Seleccione una opción: ");

            try {
                switch (opcion) {
                    case 1 -> gestor.mostrarPersonas();
                    case 2 -> registrarPersona();
                    case 3 -> editarPersona();
                    case 0 -> {
                        // Regresa al menú principal.
                    }
                    default -> System.out.println(
                            "La opción ingresada no es válida."
                    );
                }
            } catch (RutInvalidoException
                     | RuntimeException e) {
                mostrarError(e);
            }

        } while (opcion != 0);
    }

    /**
     * Administra las operaciones relacionadas con reservas.
     */
    private void menuReservas() {
        int opcion;

        do {
            System.out.println();
            System.out.println("=== RESERVAS ===");
            System.out.println("1. Ver reservas");
            System.out.println("2. Crear nueva reserva");
            System.out.println("3. Editar reserva");
            System.out.println("4. Cancelar reserva");
            System.out.println("0. Volver");

            opcion = leerEntero("Seleccione una opción: ");

            try {
                switch (opcion) {
                    case 1 -> gestor.mostrarReservas();
                    case 2 -> crearReserva();
                    case 3 -> editarReserva();
                    case 4 -> cancelarReserva();
                    case 0 -> {
                        // Regresa al menú principal.
                    }
                    default -> System.out.println(
                            "La opción ingresada no es válida."
                    );
                }
            } catch (RutInvalidoException
                     | RuntimeException e) {
                mostrarError(e);
            }

        } while (opcion != 0);
    }

    /**
     * Administra los tours y sus salidas programadas.
     */
    private void menuTours() {
        int opcion;

        do {
            System.out.println();
            System.out.println("=== TOURS ===");
            System.out.println("1. Ver tours");
            System.out.println("2. Consultar tour");
            System.out.println("3. Registrar tour");
            System.out.println("4. Editar tour");
            System.out.println("5. Salidas programadas");
            System.out.println("0. Volver");

            opcion = leerEntero("Seleccione una opción: ");

            try {
                switch (opcion) {
                    case 1 -> gestor.mostrarTours();
                    case 2 -> consultarTour();
                    case 3 -> registrarTour();
                    case 4 -> editarTour();
                    case 5 -> menuSalidas();
                    case 0 -> {
                        // Regresa al menú principal.
                    }
                    default -> System.out.println(
                            "La opción ingresada no es válida."
                    );
                }
            } catch (RuntimeException e) {
                mostrarError(e);
            }

        } while (opcion != 0);
    }

    /**
     * Administra las salidas programadas de los tours.
     */
    private void menuSalidas() {
        int opcion;

        do {
            System.out.println();
            System.out.println("=== SALIDAS PROGRAMADAS ===");
            System.out.println("1. Ver salidas");
            System.out.println("2. Registrar salida");
            System.out.println("3. Editar salida");
            System.out.println("0. Volver");

            opcion = leerEntero("Seleccione una opción: ");

            try {
                switch (opcion) {
                    case 1 -> gestor.mostrarSalidas();
                    case 2 -> registrarSalida();
                    case 3 -> editarSalida();
                    case 0 -> {
                        // Regresa al menú de tours.
                    }
                    default -> System.out.println(
                            "La opción ingresada no es válida."
                    );
                }
            } catch (RuntimeException e) {
                mostrarError(e);
            }

        } while (opcion != 0);
    }

    /**
     * Administra los filtros disponibles para las entidades.
     */
    private void menuFiltros() {
        int opcion;

        do {
            System.out.println();
            System.out.println("=== BÚSQUEDA Y FILTROS ===");
            System.out.println("1. Filtrar personas");
            System.out.println("2. Filtrar tours");
            System.out.println("3. Filtrar reservas");
            System.out.println("0. Volver");

            opcion = leerEntero("Seleccione una opción: ");

            try {
                switch (opcion) {
                    case 1 -> filtrarPersonas();
                    case 2 -> filtrarTours();
                    case 3 -> filtrarReservas();
                    case 0 -> {
                        // Regresa al menú principal.
                    }
                    default -> System.out.println(
                            "La opción ingresada no es válida."
                    );
                }
            } catch (RuntimeException e) {
                mostrarError(e);
            }

        } while (opcion != 0);
    }

    /**
     * Registra un cliente, proveedor o guía turístico.
     */
    private void registrarPersona()
            throws RutInvalidoException {

        System.out.println();
        System.out.println("=== TIPO DE PERSONA ===");
        System.out.println("1. Cliente");
        System.out.println("2. Proveedor");
        System.out.println("3. Guía turístico");
        System.out.println("0. Cancelar");

        int tipo = leerEntero("Seleccione un tipo: ");

        if (tipo == 0) {
            return;
        }

        if (tipo < 1 || tipo > 3) {
            System.out.println(
                    "El tipo seleccionado no es válido."
            );
            return;
        }

        System.out.print("RUT: ");
        String rut = scanner.nextLine();

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine();

        Direccion direccion = leerDireccion();

        switch (tipo) {
            case 1 -> registrarCliente(
                    rut,
                    nombre,
                    telefono,
                    direccion
            );

            case 2 -> registrarProveedor(
                    rut,
                    nombre,
                    telefono,
                    direccion
            );

            case 3 -> registrarGuia(
                    rut,
                    nombre,
                    telefono,
                    direccion
            );
        }
    }

    private void registrarCliente(
            String rut,
            String nombre,
            String telefono,
            Direccion direccion
    ) throws RutInvalidoException {

        System.out.print("Correo: ");
        String correo = scanner.nextLine();

        Cliente cliente = gestor.registrarCliente(
                rut,
                nombre,
                telefono,
                direccion,
                correo
        );

        System.out.println(
                "Nuevo cliente registrado: "
                        + cliente.getNombre()
        );
    }

    private void registrarProveedor(
            String rut,
            String nombre,
            String telefono,
            Direccion direccion
    ) throws RutInvalidoException {

        System.out.print("Tipo de servicio: ");
        String tipoServicio = scanner.nextLine();

        Proveedor proveedor = gestor.registrarProveedor(
                rut,
                nombre,
                telefono,
                direccion,
                tipoServicio
        );

        System.out.println(
                "Nuevo proveedor registrado: "
                        + proveedor.getNombre()
        );
    }

    private void registrarGuia(
            String rut,
            String nombre,
            String telefono,
            Direccion direccion
    ) throws RutInvalidoException {

        System.out.print("Especialidad: ");
        String especialidad = scanner.nextLine();

        GuiaTuristico guia =
                gestor.registrarGuiaTuristico(
                        rut,
                        nombre,
                        telefono,
                        direccion,
                        especialidad
                );

        System.out.println(
                "Nuevo guía turístico registrado: "
                        + guia.getNombre()
        );
    }

    /**
     * Actualiza una persona conservando su RUT.
     */
    private void editarPersona() {
        System.out.print("RUT de la persona: ");
        String rut = scanner.nextLine();

        Persona persona = gestor.buscarPersona(rut);

        if (persona == null) {
            System.out.println("Persona no encontrada.");
            return;
        }

        System.out.println("Registro actual:");
        System.out.println(persona);

        System.out.print("Nuevo nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Nuevo teléfono: ");
        String telefono = scanner.nextLine();

        Direccion direccion = leerDireccion();
        String datoEspecifico;

        if (persona instanceof Cliente) {
            System.out.print("Nuevo correo: ");
        } else if (persona instanceof GuiaTuristico) {
            System.out.print("Nueva especialidad: ");
        } else {
            System.out.print("Nuevo tipo de servicio: ");
        }

        datoEspecifico = scanner.nextLine();

        gestor.editarPersona(
                rut,
                nombre,
                telefono,
                direccion,
                datoEspecifico
        );

        System.out.println("Persona actualizada.");
    }

    /**
     * Crea una reserva y registra automáticamente
     * al cliente cuando su RUT no existe.
     */
    private void crearReserva()
            throws RutInvalidoException {

        System.out.print("Código de reserva: ");
        String codigo = scanner.nextLine();

        Cliente cliente = obtenerORegistrarCliente();

        System.out.println();
        System.out.println("Salidas programadas:");
        gestor.mostrarSalidas();

        System.out.print("Código de salida: ");
        String codigoSalida = scanner.nextLine();

        int cantidad = leerEntero(
                "Cantidad de personas: "
        );

        Reserva reserva = gestor.crearReserva(
                codigo,
                cliente.getRut(),
                codigoSalida,
                cantidad
        );

        System.out.println("Reserva creada correctamente:");
        System.out.println(reserva);
    }

    /**
     * Busca un cliente mediante su RUT normalizado.
     * Si no existe, permite registrarlo sin depender
     * del formato utilizado para ingresar el RUT.
     *
     * @return cliente encontrado o registrado.
     * @throws RutInvalidoException si ocurre un error al registrar.
     */
    private Cliente obtenerORegistrarCliente()
            throws RutInvalidoException {

        while (true) {
            System.out.print("RUT del cliente: ");
            String rutIngresado = scanner.nextLine();

            String rutNormalizado;

            try {
                rutNormalizado =
                        ValidadorRut.formatear(rutIngresado);
            } catch (RutInvalidoException e) {
                System.out.println(
                        "El RUT ingresado no es válido."
                );
                continue;
            }

            Persona persona =
                    gestor.buscarPersona(rutNormalizado);

            if (persona instanceof Cliente cliente) {
                System.out.println(
                        "Cliente encontrado: "
                                + cliente.getNombre()
                );

                return cliente;
            }

            if (persona != null) {
                System.out.println(
                        "El RUT ya pertenece a un "
                                + persona.getClass().getSimpleName()
                                + "."
                );
                System.out.println(
                        "Ingrese el RUT de otro cliente."
                );
                continue;
            }

            System.out.println(
                    "El cliente no se encuentra registrado."
            );
            System.out.println(
                    "Ingrese sus datos para continuar."
            );

            System.out.print("Nombre: ");
            String nombre = scanner.nextLine();

            System.out.print("Teléfono: ");
            String telefono = scanner.nextLine();

            Direccion direccion = leerDireccion();

            System.out.print("Correo: ");
            String correo = scanner.nextLine();

            Cliente nuevoCliente =
                    gestor.registrarCliente(
                            rutNormalizado,
                            nombre,
                            telefono,
                            direccion,
                            correo
                    );

            System.out.println(
                    "Nuevo cliente registrado: "
                            + nuevoCliente.getNombre()
            );

            return nuevoCliente;
        }
    }

    /**
     * Modifica el cliente, salida o cantidad de una reserva.
     */
    private void editarReserva() {
        System.out.print("Código de reserva: ");
        String codigo = scanner.nextLine();

        Reserva reserva = gestor.buscarReserva(codigo);

        if (reserva == null) {
            System.out.println("Reserva no encontrada.");
            return;
        }

        System.out.println("Reserva actual:");
        System.out.println(reserva);

        System.out.print("RUT del cliente: ");
        String rut = scanner.nextLine();

        System.out.println("Salidas programadas:");
        gestor.mostrarSalidas();

        System.out.print("Código de salida: ");
        String codigoSalida = scanner.nextLine();

        int cantidad = leerEntero(
                "Nueva cantidad de personas: "
        );

        gestor.editarReserva(
                codigo,
                rut,
                codigoSalida,
                cantidad
        );

        System.out.println("Reserva actualizada.");
    }

    private void cancelarReserva() {
        System.out.print("Código de reserva: ");
        String codigo = scanner.nextLine();

        if (gestor.cancelarReserva(codigo)) {
            System.out.println(
                    "Reserva cancelada correctamente."
            );
        } else {
            System.out.println(
                    "La reserva no existe o ya está cancelada."
            );
        }
    }

    /**
     * Muestra el tour, su itinerario y sus salidas.
     */
    private void consultarTour() {
        System.out.print("Código del tour: ");
        String codigo = scanner.nextLine();

        Tour tour = gestor.buscarTour(codigo);

        if (tour == null) {
            System.out.println("Tour no encontrado.");
            return;
        }

        System.out.println(tour);
        tour.mostrarItinerario();

        System.out.println("Salidas programadas:");

        mostrarResultados(
                gestor.filtrarSalidasPorTour(codigo)
        );
    }

    private void registrarTour() {
        System.out.print("Código: ");
        String codigo = scanner.nextLine();

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Destino: ");
        String destino = scanner.nextLine();

        double precio = leerDouble("Precio: ");

        Tour tour = gestor.registrarTour(
                codigo,
                nombre,
                destino,
                precio,
                leerActividades()
        );

        System.out.println(
                "Tour registrado: " + tour.getNombre()
        );
    }

    private void editarTour() {
        System.out.print("Código del tour: ");
        String codigo = scanner.nextLine();

        Tour tour = gestor.buscarTour(codigo);

        if (tour == null) {
            System.out.println("Tour no encontrado.");
            return;
        }

        System.out.println("Tour actual:");
        System.out.println(tour);

        System.out.print("Nuevo nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Nuevo destino: ");
        String destino = scanner.nextLine();

        double precio = leerDouble("Nuevo precio: ");

        gestor.editarTour(
                codigo,
                nombre,
                destino,
                precio,
                leerActividades()
        );

        System.out.println("Tour actualizado.");
    }

    /**
     * Registra una nueva salida para un tour.
     */
    private void registrarSalida() {
        System.out.print("Código de salida: ");
        String codigo = scanner.nextLine();

        System.out.print("Código del tour: ");
        String codigoTour = scanner.nextLine();

        LocalDate fecha = leerFecha(
                "Fecha (dd-MM-yyyy): "
        );

        int cupos = leerEntero("Cupo máximo: ");

        System.out.print("RUT del guía: ");
        String rutGuia = scanner.nextLine();

        SalidaTour salida = gestor.registrarSalida(
                codigo,
                codigoTour,
                fecha,
                cupos,
                rutGuia
        );

        System.out.println("Salida registrada:");
        System.out.println(salida);
    }

    /**
     * Modifica una salida sin cambiar su código.
     */
    private void editarSalida() {
        System.out.print("Código de salida: ");
        String codigo = scanner.nextLine();

        SalidaTour salida = gestor.buscarSalida(codigo);

        if (salida == null) {
            System.out.println("Salida no encontrada.");
            return;
        }

        System.out.println("Salida actual:");
        System.out.println(salida);

        System.out.print("Código del tour: ");
        String codigoTour = scanner.nextLine();

        LocalDate fecha = leerFecha(
                "Nueva fecha (dd-MM-yyyy): "
        );

        int cupos = leerEntero(
                "Nuevo cupo máximo: "
        );

        System.out.print("RUT del guía: ");
        String rutGuia = scanner.nextLine();

        gestor.editarSalida(
                codigo,
                codigoTour,
                fecha,
                cupos,
                rutGuia
        );

        System.out.println("Salida actualizada.");
    }

    /**
     * Filtra personas combinando tipo y comuna.
     */
    private void filtrarPersonas() {
        System.out.println();
        System.out.println("Tipo de persona:");
        System.out.println("0. Todos");
        System.out.println("1. Clientes");
        System.out.println("2. Proveedores");
        System.out.println("3. Guías turísticos");

        int opcion = leerEntero("Seleccione un tipo: ");
        String tipo;

        switch (opcion) {
            case 0 -> tipo = "";
            case 1 -> tipo = "CLIENTE";
            case 2 -> tipo = "PROVEEDOR";
            case 3 -> tipo = "GUIA";
            default -> {
                System.out.println("Tipo no válido.");
                return;
            }
        }

        System.out.print(
                "Comuna (Enter para incluir todas): "
        );
        String comuna = scanner.nextLine();

        mostrarResultados(
                gestor.filtrarPersonas(tipo, comuna)
        );
    }

    /**
     * Filtra tours por destino y rango de precio.
     */
    private void filtrarTours() {
        System.out.print(
                "Destino (Enter para incluir todos): "
        );
        String destino = scanner.nextLine();

        Double precioMinimo = leerDoubleOpcional(
                "Precio mínimo (Enter para omitir): "
        );

        Double precioMaximo = leerDoubleOpcional(
                "Precio máximo (Enter para omitir): "
        );

        mostrarResultados(
                gestor.filtrarTours(
                        destino,
                        precioMinimo,
                        precioMaximo
                )
        );
    }

    /**
     * Filtra reservas combinando fecha, estado,
     * cliente y tour.
     */
    private void filtrarReservas() {
        LocalDate fecha = leerFechaOpcional(
                "Fecha, dd-MM-yyyy "
                        + "(Enter para incluir todas): "
        );

        System.out.println("Estado:");
        System.out.println("0. Todos");
        System.out.println("1. Activas");
        System.out.println("2. Canceladas");

        int opcionEstado = leerEntero(
                "Seleccione un estado: "
        );

        Boolean estado;

        switch (opcionEstado) {
            case 0 -> estado = null;
            case 1 -> estado = true;
            case 2 -> estado = false;
            default -> {
                System.out.println("Estado no válido.");
                return;
            }
        }

        System.out.print(
                "RUT del cliente (Enter para todos): "
        );
        String rut = scanner.nextLine();

        System.out.print(
                "Código del tour (Enter para todos): "
        );
        String codigoTour = scanner.nextLine();

        mostrarResultados(
                gestor.filtrarReservas(
                        fecha,
                        estado,
                        rut,
                        codigoTour
                )
        );
    }

    /**
     * Solicita los datos de una dirección.
     */
    private Direccion leerDireccion() {
        System.out.print("Calle: ");
        String calle = scanner.nextLine();

        System.out.print("Número: ");
        String numero = scanner.nextLine();

        System.out.print("Comuna: ");
        String comuna = scanner.nextLine();

        System.out.print("Región: ");
        String region = scanner.nextLine();

        return new Direccion(
                calle,
                numero,
                comuna,
                region
        );
    }

    /**
     * Solicita las actividades que forman el itinerario.
     */
    private List<String> leerActividades() {
        List<String> actividades = new ArrayList<>();

        int cantidad = leerEntero(
                "Cantidad de actividades: "
        );

        if (cantidad < 0) {
            throw new IllegalArgumentException(
                    "La cantidad no puede ser negativa."
            );
        }

        for (int i = 1; i <= cantidad; i++) {
            System.out.print("Actividad " + i + ": ");
            actividades.add(scanner.nextLine());
        }

        return actividades;
    }

    /**
     * Muestra una colección obtenida mediante consulta o filtro.
     */
    private void mostrarResultados(List<?> resultados) {
        if (resultados.isEmpty()) {
            System.out.println(
                    "No se encontraron coincidencias."
            );
            return;
        }

        System.out.println(
                "Resultados encontrados: " + resultados.size()
        );

        for (Object resultado : resultados) {
            System.out.println(resultado);
        }
    }

    private LocalDate leerFecha(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String entrada = scanner.nextLine();

            try {
                return LocalDate.parse(
                        entrada,
                        FORMATO_FECHA
                );
            } catch (DateTimeParseException e) {
                System.out.println(
                        "Fecha no válida. Use dd-MM-yyyy."
                );
            }
        }
    }

    private LocalDate leerFechaOpcional(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String entrada = scanner.nextLine();

            if (entrada.isBlank()) {
                return null;
            }

            try {
                return LocalDate.parse(
                        entrada,
                        FORMATO_FECHA
                );
            } catch (DateTimeParseException e) {
                System.out.println(
                        "Fecha no válida. Use dd-MM-yyyy."
                );
            }
        }
    }

    private int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);

            try {
                return Integer.parseInt(
                        scanner.nextLine()
                );
            } catch (NumberFormatException e) {
                System.out.println(
                        "Debe ingresar un número entero."
                );
            }
        }
    }

    private double leerDouble(String mensaje) {
        while (true) {
            System.out.print(mensaje);

            try {
                return Double.parseDouble(
                        scanner.nextLine()
                );
            } catch (NumberFormatException e) {
                System.out.println(
                        "Debe ingresar un número válido."
                );
            }
        }
    }

    private Double leerDoubleOpcional(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String entrada = scanner.nextLine();

            if (entrada.isBlank()) {
                return null;
            }

            try {
                return Double.parseDouble(entrada);
            } catch (NumberFormatException e) {
                System.out.println(
                        "Debe ingresar un número válido."
                );
            }
        }
    }

    /**
     * Muestra un mensaje de error uniforme.
     *
     * @param e error capturado.
     */
    private void mostrarError(Exception e) {
        System.out.println(
                "No fue posible realizar la operación."
        );
        System.out.println("Detalle: " + e.getMessage());
    }
}