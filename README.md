# Evaluación Final Transversal – Desarrollo Orientado a Objetos I

## Autor del proyecto

Nombre: Paloma Larraín Pereira

## Descripción general del sistema

Este proyecto corresponde a la Evaluación Final Transversal de la asignatura Desarrollo Orientado a Objetos I. Consiste en un sistema desarrollado en Java para modelar y gestionar las operaciones de la agencia turística ficticia Llanquihue Tour.

La solución permite administrar personas, tours, salidas programadas y reservas, aplicando principios de programación orientada a objetos como encapsulamiento, composición, herencia, polimorfismo e interfaces. El proyecto aborda problemáticas asociadas a la organización de información, gestión de cupos y registro de reservas mediante una estructura modular, reutilizable y extensible.

## Estructura general del proyecto

```text
LlanquihueTourEFT
src/
├── app/
│   ├── Main.java
│   └── MenuConsola.java
├── data/
│   ├── GestorDatos.java
│   └── LectorDatos.java
├── model/
│   ├── Cliente.java
│   ├── Dirección.java
│   ├── GuiaTuristico.java
│   ├── Persona.java
│   ├── Proveedor.java
│   ├── Registrable.java
│   ├── Reserva.java
│   ├── SalidaTour.java
│   └── Tour.java 
├── util/ 
│   ├── RutInvalidoException.java
│   └── ValidadorRut.java  
resources/   
│   ├── personas.txt
│   ├── tours.txt
│   └── salidas.txt
README.md

```

## Descripción breve de los paquetes

`app`: contiene el punto de entrada y el menú por consola.

`data`: administra las colecciones y la lectura de archivos externos.

`model`: contiene las entidades, la jerarquía de clases y la interfaz del sistema.

`util`: incluye la validación del RUT y la excepción personalizada.

## Instrucciones para clonar y ejecutar el proyecto

La ejecución de la aplicación se realiza desde:
`src/app/Main.java`


1. Clona el repositorio desde GitHub:
`git clone https://github.com/lolarrain/LlanquihueTourEFT.git`

2. Abre el proyecto en IntelliJ IDEA.

3. Verifica que las clases estén correctamente ubicadas en sus paquetes correspondientes.

4. Ejecuta el archivo Main.java desde el paquete app.

5. Sigue las instrucciones en consola.

Repositorio GitHub: [https://github.com/lolarrain/LlanquihueTourEFT]  Fecha de entrega: [19/07/2026]