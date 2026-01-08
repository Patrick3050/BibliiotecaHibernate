package org.example;

import Entidades.GestionBiblioteca;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

public class App
{
    private static int leerOpcion(Scanner t) {
        try {
            int num = t.nextInt();
            t.nextLine(); // Limpiamos el buffer del Enter
            return num;
        } catch (InputMismatchException e) {
            System.err.println("Error: Debes introducir un número entero.\n");
            System.err.flush();
            t.next(); // Limpiamos el token erróneo (la letra que escribió el usuario)
            return -1; // Devolvemos -1 para indicar que la entrada fue inválida
        }
    }

    public static void main( String[] args )
    {
        Scanner t = new Scanner(System.in);
        GestionBiblioteca gestion = new GestionBiblioteca();

        int opcion;
        do {
            System.out.println("""
                    --------- Menu de biblioteca --------
                    1. Gestión de Libros.
                    2. Gestión de Ejemplares.
                    3. Gestión de Usuarios.
                    4. Gestión de Préstamos.
                    5. Visualización de información.
                    0. Cerrar menu.
                    -------------------------------------
                    Elige una opcion:""");

            opcion = leerOpcion(t);

            int subOpcion;
            switch (opcion) {
                case 1 -> { // GESTIÓN DE LIBROS
                    do {
                        System.out.println("""
                                --------- Menu de Libros --------
                                1. Agregar.
                                2. Mostrar.
                                3. Actualizar.
                                4. Eliminar.
                                0. Volver.
                                ---------------------------------
                                Elige una opcion:""");
                        subOpcion = leerOpcion(t);

                        switch (subOpcion) {
                            case 1 -> {
                                System.out.println("Agregar un nuevo libro:");
                                System.out.print("ISBN: ");
                                String isbn = t.nextLine();
                                System.out.print("Título: ");
                                String titulo = t.nextLine();
                                System.out.print("Autor: ");
                                String autor = t.nextLine();
                                gestion.insertarLibro(isbn, titulo, autor);
                            }
                            case 2 -> {
                                System.out.println("Registros de libros:");
                                gestion.mostrarLibros();
                            }
                            case 3 -> {
                                System.out.println("Actualizar datos de libro:");
                                System.out.print("ISBN del libro: ");
                                String isbn = t.nextLine();
                                System.out.println("\nActualización de titulo y autor:");
                                System.out.print("Nuevo Título: ");
                                String tit = t.nextLine();
                                System.out.print("Nuevo Autor: ");
                                String aut = t.nextLine();
                                gestion.actualizarLibro(isbn, tit, aut);
                            }
                            case 4 -> {
                                System.out.println("Eliminación del libro:");
                                System.out.print("ISBN del libro: ");
                                String isbn = t.nextLine();
                                gestion.eliminarLibro(isbn);
                            }
                            case 0 -> System.out.println("Regresando al menu principal...");
                            default -> {
                                System.err.println("Opción no válida.\n");
                                System.err.flush(); // Fuerza la salida inmediata del error
                            }
                        }
                    } while (subOpcion != 0);
                }
                case 2 -> {
                    do {
                        System.out.println("""
                                ----------- Menu de ejemplares ----------
                                1. Agregar ejemplar por ISBN de libro.
                                2. Mostrar stock y listado.
                                3. Actualizar estado (Dañado o Prestado).
                                4. Eliminar ejemplar.
                                0. Volver.
                                -----------------------------------------
                                Elige una opcion:""");
                        subOpcion = t.nextInt();
                        t.nextLine(); // Limpiar buffer

                        switch (subOpcion) {
                            case 1 -> {
                                System.out.println("Para crear el ejemplar insertar el isbn del libro:");
                                System.out.print("Introduce el ISBN del libro: ");
                                String isbn = t.nextLine();
                                gestion.insertarEjemplar(isbn);
                            }
                            case 2 -> {
                                System.out.println("Registros de ejemplares.");
                                gestion.mostrarEjemplaresYstockDisponible();
                            }
                            case 3 -> {
                                System.out.println("Actualizar el estado:");
                                System.out.print("ID del ejemplar a modificar: ");
                                int id = leerOpcion(t);
                                System.out.println("""
                                ----------------------------
                                Selecciona el nuevo estado:
                                1.Disponible
                                2.Prestado
                                3.Dañado
                                ----------------------------
                                Elige una opcion:""");
                                int estado = leerOpcion(t);
                                gestion.actualizarEstadoEjemplar(id, estado);
                            }
                            case 4 -> {
                                System.out.println("Eliminación del ejemplar:");
                                System.out.println("Id del ejemplar: ");
                                int id = leerOpcion(t);
                                gestion.eliminarEjemplar(id);
                            }
                            case 0 -> System.out.println("Regresando al menu principal...");
                            default -> {
                                System.err.println("Opción no válida.\n");
                                System.err.flush(); // Fuerza la salida inmediata del error
                            }
                        }
                    } while (subOpcion != 0);
                }
                case 3 -> {
                    do {
                        System.out.println("""
                                --------- Menu de usuarios --------
                                1. Crear.
                                2. Mostrar lista.
                                3. Actualizar datos.
                                4. Eliminar usuario.
                                5. Penalizar.
                                0. Volver.
                                -----------------------------------
                                Elige una opcion:""");
                        subOpcion = leerOpcion(t);

                        switch (subOpcion) {
                            case 1 -> {
                                System.out.println("Crear un nuevo usuario:");
                                System.out.print("DNI: ");
                                String dni = t.nextLine();
                                System.out.print("Nombre: ");
                                String nombre = t.nextLine();
                                System.out.print("Email: ");
                                String email = t.nextLine();
                                System.out.print("Contraseña: ");
                                String pass = t.nextLine();
                                System.out.println("""
                                ----------------------------
                                Selecciona el nuevo estado:
                                1.Disponible
                                2.Prestado
                                3.Dañado
                                ----------------------------
                                Elige una opcion:""");
                                int tipo = leerOpcion(t);
                                gestion.insertarUsuario(dni, nombre, email, pass, tipo);
                            }
                            case 2 -> {
                                System.out.println("Registros de usuarios:");
                                gestion.mostrarUsuarios();
                            }
                            case 3 -> {
                                System.out.println("Actualización de datos del usuario:");
                                System.out.print("DNI del usuario a editar: ");
                                String dni = t.nextLine();
                                System.out.print("Nuevo nombre: ");
                                String nom = t.nextLine();
                                System.out.print("Nuevo email: ");
                                String mail = t.nextLine();
                                gestion.actualizarUsuario(dni, nom, mail);
                            }
                            case 4 -> {
                                System.out.println("Eliminar usuario:");
                                System.out.print("DNI del usuario: ");
                                gestion.eliminarUsuario(t.nextLine());
                            }
                            case 5 -> {
                                System.out.println("Penalización: ");
                                System.out.print("DNI del usuario: ");
                                String dni = t.nextLine();
                                System.out.print("Número de días de penalización: ");
                                int dias = leerOpcion(t);
                                gestion.penalizarUsuario(dni, dias);
                            }
                            case 0 -> System.out.println("Regresando al menu principal...");
                            default -> {
                                System.err.println("Opción no válida.\n");
                                System.err.flush(); // Fuerza la salida inmediata del error
                            }
                        }
                    } while (subOpcion != 0);
                }
                case 4 -> {
                    do {
                        System.out.println("""
                                --------- Menu de préstamos --------
                                1. Registrar Préstamo (Nuevo).
                                2. Registrar Devolución (Cerrar).
                                3. Ver listado de préstamos.
                                4. Eliminar registro de préstamo.
                                5. Actualizar fecha de inicio.
                                0. Volver.
                                ------------------------------------
                                Elige una opcion:""");
                        subOpcion = leerOpcion(t);

                        switch (subOpcion) {
                            case 1 -> {
                                System.out.println("Crear un préstamo:");
                                System.out.print("DNI Usuario: ");
                                String dni = t.nextLine();
                                System.out.print("ID Ejemplar: ");
                                int id = leerOpcion(t);
                                gestion.registrarPrestamo(dni, id);
                            }
                            case 2 -> {
                                System.out.print("ID del Ejemplar a devolver: ");
                                gestion.registrarDevolucion(leerOpcion(t));
                            }
                            case 3 -> {
                                System.out.println("Mostrar los préstamos:");
                                gestion.mostrarPrestamos();
                            }
                            case 4 -> {
                                System.out.println("Eliminación del préstamo:");
                                System.out.print("ID del préstamo: ");
                                gestion.eliminarPrestamo(leerOpcion(t));
                            }
                            case 5 -> {
                                System.out.println("Actualizar datos del préstamo:");
                                System.out.print("ID del Préstamo a corregir: ");
                                int id = leerOpcion(t);
                                System.out.print("Nueva fecha inicio (AAAA-MM-DD): ");
                                String fechaStr = t.nextLine();
                                try {
                                    gestion.corregirFechaPrestamo(id, LocalDate.parse(fechaStr));
                                } catch (Exception e) {
                                    System.err.println("Formato de fecha inválido.\n");
                                    System.err.flush();
                                }
                            }
                            case 0 -> System.out.println("Regresando al menu principal...");
                            default -> {
                                System.err.println("Opción no válida.\n");
                                System.err.flush(); // Fuerza la salida inmediata del error
                            }
                        }
                    } while (subOpcion != 0);
                }
                case 5 -> {
                    System.out.print("Introduce tu DNI para acceder al panel: ");
                    String dniAcceso = t.nextLine();
                    // Buscamos el usuario en la base de datos para saber quién es
                    Entidades.Usuario userActivo = gestion.buscarUsuarioPorDni(dniAcceso);

                    if (userActivo == null) {
                        System.err.println("Acceso denegado: El usuario no existe.\n");
                        System.err.flush();
                    } else {
                        do {
                            System.out.println("\n--- PANEL DE CONSULTA (" + userActivo.getTipo().toString().toUpperCase() + ") ---");
                            System.out.println("Hola, " + userActivo.getNombre());

                            // Si es administrador, le mostramos el menú de gestor
                            if (userActivo.getTipo() == Entidades.UsuarioTipo.administrador) {
                                System.out.println("""
                                        ------------------------------------------------------
                                        1. Ver todos los préstamos de la biblioteca (Global).
                                        2. Ver lista de todos los usuarios.
                                        0. Volver.
                                        ------------------------------------------------------
                                        Elige una opcion:""");
                            } else {
                                // Si es normal, le mostramos su menú personal
                                System.out.println("""
                                        -----------------------------------------------------
                                        1. Ver mis préstamos actuales.
                                        2. Consultar mi fecha de penalización.
                                        0. Volver.
                                        -----------------------------------------------------
                                        Elige una opcion:""");
                            }
                            subOpcion = leerOpcion(t);

                            // LÓGICA SEGÚN EL ROL
                            if (userActivo.getTipo() == Entidades.UsuarioTipo.administrador) {
                                switch (subOpcion) {
                                    case 1 -> gestion.mostrarPrestamos(); // El gestor ve todo
                                    case 2 -> gestion.mostrarUsuarios();
                                    case 0 -> System.out.println("Saliendo...\n");
                                }
                            } else {
                                switch (subOpcion) {
                                    case 1 -> gestion.mostrarMisPrestamos(dniAcceso); // Solo ve los suyos
                                    case 2 -> {
                                        if (userActivo.getPenalizacionHasta() != null) {
                                            System.out.println("Tu penalización termina el: " + userActivo.getPenalizacionHasta());
                                        } else {
                                            System.out.println("No tienes penalizaciones activas.");
                                        }
                                    }
                                    case 0 -> System.out.println("Saliendo...");
                                }
                            }
                        } while (subOpcion != 0);
                    }
                }
                case 0 -> System.out.println("Regresando al menu principal...");
                default -> {
                    System.err.println("Opción no válida.\n");
                    System.err.flush(); // Fuerza la salida inmediata del error
                }
            }
        } while (opcion != 0);

        System.out.println("Ha terminado el menú.");

    }
}
