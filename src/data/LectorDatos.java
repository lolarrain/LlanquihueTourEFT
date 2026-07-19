package data;

import model.Cliente;
import model.Direccion;
import model.GuiaTuristico;
import model.Persona;
import model.Proveedor;
import model.SalidaTour;
import model.Tour;
import util.RutInvalidoException;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Lee los archivos externos y convierte sus registros
 * en objetos del modelo.
 */
public class LectorDatos {

    private static final DateTimeFormatter FORMATO_FECHA =
            DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public List<Persona> leerPersonas(String ruta)
            throws IOException {

        List<Persona> personas = new ArrayList<>();

        try (BufferedReader lector = Files.newBufferedReader(
                Path.of(ruta),
                StandardCharsets.UTF_8
        )) {
            String linea;
            int numeroLinea = 0;

            while ((linea = lector.readLine()) != null) {
                numeroLinea++;

                if (linea.isBlank()) {
                    continue;
                }

                try {
                    personas.add(procesarPersona(linea));
                } catch (RutInvalidoException
                         | IllegalArgumentException e) {

                    throw new IllegalArgumentException(
                            "Error en personas.txt, línea "
                                    + numeroLinea + ": "
                                    + e.getMessage(),
                            e
                    );
                }
            }
        }

        return personas;
    }

    public List<Tour> leerTours(String ruta)
            throws IOException {

        List<Tour> tours = new ArrayList<>();

        try (BufferedReader lector = Files.newBufferedReader(
                Path.of(ruta),
                StandardCharsets.UTF_8
        )) {
            String linea;
            int numeroLinea = 0;

            while ((linea = lector.readLine()) != null) {
                numeroLinea++;

                if (linea.isBlank()) {
                    continue;
                }

                try {
                    tours.add(procesarTour(linea));
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException(
                            "Error en tours.txt, línea "
                                    + numeroLinea + ": "
                                    + e.getMessage(),
                            e
                    );
                }
            }
        }

        return tours;
    }

    public List<SalidaTour> leerSalidas(
            String ruta,
            List<Tour> tours,
            List<Persona> personas
    ) throws IOException {

        List<SalidaTour> salidas = new ArrayList<>();

        try (BufferedReader lector = Files.newBufferedReader(
                Path.of(ruta),
                StandardCharsets.UTF_8
        )) {
            String linea;
            int numeroLinea = 0;

            while ((linea = lector.readLine()) != null) {
                numeroLinea++;

                if (linea.isBlank()) {
                    continue;
                }

                try {
                    salidas.add(
                            procesarSalida(
                                    linea,
                                    tours,
                                    personas
                            )
                    );
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException(
                            "Error en salidas.txt, línea "
                                    + numeroLinea + ": "
                                    + e.getMessage(),
                            e
                    );
                }
            }
        }

        return salidas;
    }

    private Persona procesarPersona(String linea)
            throws RutInvalidoException {

        String[] campos = linea.split(";", -1);

        if (campos.length != 9) {
            throw new IllegalArgumentException(
                    "El registro debe contener 9 campos."
            );
        }

        Direccion direccion = new Direccion(
                campos[4],
                campos[5],
                campos[6],
                campos[7]
        );

        return switch (campos[0].trim().toUpperCase()) {
            case "CLIENTE" -> new Cliente(
                    campos[1],
                    campos[2],
                    campos[3],
                    direccion,
                    campos[8]
            );

            case "GUIA" -> new GuiaTuristico(
                    campos[1],
                    campos[2],
                    campos[3],
                    direccion,
                    campos[8]
            );

            case "PROVEEDOR" -> new Proveedor(
                    campos[1],
                    campos[2],
                    campos[3],
                    direccion,
                    campos[8]
            );

            default -> throw new IllegalArgumentException(
                    "Tipo de persona desconocido."
            );
        };
    }

    private Tour procesarTour(String linea) {
        String[] campos = linea.split(";", -1);

        if (campos.length != 5) {
            throw new IllegalArgumentException(
                    "El registro debe contener 5 campos."
            );
        }

        Tour tour = new Tour(
                campos[0],
                campos[1],
                campos[2],
                Double.parseDouble(campos[3].trim())
        );

        if (!campos[4].isBlank()) {
            String[] actividades = campos[4].split("\\|");

            for (String actividad : actividades) {
                tour.agregarActividad(actividad);
            }
        }

        return tour;
    }

    private SalidaTour procesarSalida(
            String linea,
            List<Tour> tours,
            List<Persona> personas
    ) {
        String[] campos = linea.split(";", -1);

        if (campos.length != 5) {
            throw new IllegalArgumentException(
                    "El registro debe contener 5 campos."
            );
        }

        Tour tour = buscarTour(tours, campos[1]);
        GuiaTuristico guia =
                buscarGuia(personas, campos[4]);

        return new SalidaTour(
                campos[0],
                tour,
                LocalDate.parse(
                        campos[2].trim(),
                        FORMATO_FECHA
                ),
                Integer.parseInt(campos[3].trim()),
                guia
        );
    }

    private Tour buscarTour(
            List<Tour> tours,
            String codigo
    ) {
        for (Tour tour : tours) {
            if (tour.getCodigo()
                    .equalsIgnoreCase(codigo.trim())) {
                return tour;
            }
        }

        throw new IllegalArgumentException(
                "No existe el tour " + codigo
        );
    }

    private GuiaTuristico buscarGuia(
            List<Persona> personas,
            String rut
    ) {
        for (Persona persona : personas) {
            if (persona instanceof GuiaTuristico guia
                    && guia.getRut()
                    .equalsIgnoreCase(rut.trim())) {
                return guia;
            }
        }

        throw new IllegalArgumentException(
                "No existe un guía con el RUT " + rut
        );
    }
}
