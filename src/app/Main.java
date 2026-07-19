package app;

import data.GestorDatos;
import data.LectorDatos;
import model.Persona;
import model.SalidaTour;
import model.Tour;

import java.io.IOException;
import java.util.List;

/**
 * Punto de entrada de Llanquihue Tour.
 */
public class Main {

    public static void main(String[] args) {
        LectorDatos lector = new LectorDatos();

        try {
            List<Persona> personas =
                    lector.leerPersonas(
                            "resources/personas.txt"
                    );

            List<Tour> tours =
                    lector.leerTours(
                            "resources/tours.txt"
                    );

            List<SalidaTour> salidas =
                    lector.leerSalidas(
                            "resources/salidas.txt",
                            tours,
                            personas
                    );

            GestorDatos gestor = new GestorDatos(
                    personas,
                    tours,
                    salidas
            );

            MenuConsola menu = new MenuConsola(gestor);
            menu.iniciar();

        } catch (IOException | RuntimeException e) {
            System.out.println(
                    "No fue posible iniciar el sistema."
            );
            System.out.println("Detalle: " + e.getMessage());
        }
    }
}