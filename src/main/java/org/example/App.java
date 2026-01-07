package org.example;

import Entidades.Ejemplar;
import Entidades.GestionBiblioteca;
import Entidades.Libro;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class App
{
    public static void main( String[] args )
    {
        Scanner t = new Scanner(System.in);
        GestionBiblioteca gestion = new GestionBiblioteca();
        /*EntityManagerFactory emf = Persistence.createEntityManagerFactory("biblioteca");
        EntityManager em = emf.createEntityManager();*/


        /*Query q = em.createQuery("SELECT e.libroIsbn.isbn, e.estado FROM Ejemplar e");
        List<Object[]> objects = q.getResultList();
        for (Object[] o: objects) {
            System.out.println(o[0] + " (" + o[1] + ") ");
        }*/

        // Insertar
        /*try {
            Libro libro = new Libro("789456123789", "El chocolate", "Pinos del Mar");
            em.getTransaction().begin();
            em.persist(libro);
            em.getTransaction().commit();
        } catch (jakarta.validation.ConstraintViolationException e) {
            // Si la validación falla antes de ir a la BD
            System.out.println("--- ERROR DE VALIDACIÓN ---");
            e.getConstraintViolations().forEach(v -> System.out.println("Campo: " + v.getPropertyPath() + " -> " + v.getMessage()));
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
        } catch (jakarta.persistence.RollbackException e) {
            // Si el error ocurre durante el commit (que es tu caso actual)
            System.out.println("--- NO SE PUDO GUARDAR ---");
            System.out.println("Causa: " + e.getCause().getMessage());
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
        } catch (Exception e) {
            System.out.println("Error");
        }*/

        /*
        Equipo equipo1 = new Equipo(
                "Perfumerias Avenida", "Salamanca", "West",
                "Mediter"
        );
        em.getTransaction().begin();
        em.persist(equipo1);
        em.getTransaction().commit();
        */
        //

        /*for (Object[] o: objects) {
            System.out.println(o[0] + " (" + o[1] + ") ");
        }*/

        // Borrar
        /*
        em.getTransaction().begin();
        Jugador jugador1 = em.find(Jugador.class, 613);
        em.remove(jugador1);
        em.getTransaction().commit();
        */
        //

        // Actualizar
        /*
        em.getTransaction().begin();
        Jugador jugador1 = em.find(Jugador.class, 613);
        jugador1.setNombre("pp"); // actualizar si o si
        em.merge(jugador1);
        em.getTransaction().commit();
        */
        //

        int opcion = 0;

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

            try {
                opcion = t.nextInt();
                t.nextLine();

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
                            subOpcion = t.nextInt();
                            t.nextLine(); // Limpiar buffer

                            switch (subOpcion) {
                                case 1 -> {
                                    System.out.print("ISBN: "); String isbn = t.nextLine();
                                    System.out.print("Título: "); String titulo = t.nextLine();
                                    System.out.print("Autor: "); String autor = t.nextLine();
                                    gestion.insertarLibro(isbn, titulo, autor);
                                }
                                case 2 -> gestion.mostrarLibros();
                                case 3 -> {
                                    System.out.print("ISBN del libro a actualizar: "); String isbn = t.nextLine();
                                    System.out.print("Nuevo Título: "); String tit = t.nextLine();
                                    System.out.print("Nuevo Autor: "); String aut = t.nextLine();
                                    GestionBiblioteca.actualizarLibro(isbn, tit, aut);
                                }
                                case 4 -> {
                                    System.out.print("ISBN del libro a eliminar: "); String isbn = t.nextLine();
                                    gestion.eliminarLibro(isbn);
                                }
                            }
                        } while (subOpcion != 0);
                    }
                    case 2 -> {
                        do {
                            System.out.println("""
                        --------- Menu de ejemplares --------
                        1. Agregar.
                        2. Mostrar.
                        3. Actualizar.
                        4. Eliminar.
                        0. Volver.
                        -------------------------------------
                        Elige una opcion:""");
                            subOpcion = t.nextInt();
                            t.nextLine(); // Limpiar buffer

                            switch (subOpcion) {
                                /*case 1 -> {
                                    System.out.print("ISBN: "); String isbn = t.nextLine();
                                    System.out.print("Título: "); String titulo = t.nextLine();
                                    System.out.print("Autor: "); String autor = t.nextLine();
                                    GestionBiblioteca.insertarLibro(isbn, titulo, autor);
                                }
                                case 2 -> GestionBiblioteca.mostrarLibros();
                                case 3 -> {
                                    System.out.print("ISBN del libro a actualizar: "); String isbn = t.nextLine();
                                    System.out.print("Nuevo Título: "); String tit = t.nextLine();
                                    System.out.print("Nuevo Autor: "); String aut = t.nextLine();
                                    GestionBiblioteca.actualizarLibro(isbn, tit, aut);
                                }
                                case 4 -> {
                                    System.out.print("ISBN del libro a eliminar: "); String isbn = t.nextLine();
                                    GestionBiblioteca.eliminarLibro(isbn);
                                }*/
                            }
                        } while (subOpcion != 0);
                    }
                    case 3 -> {
                        do {
                            System.out.println("""
                        --------- Menu de usuarios --------
                        1. Agregar.
                        2. Mostrar.
                        3. Actualizar.
                        4. Eliminar.
                        0. Volver.
                        -----------------------------------
                        Elige una opcion:""");
                            subOpcion = t.nextInt();
                            t.nextLine(); // Limpiar buffer

                            switch (subOpcion) {
                                /*case 1 -> {
                                    System.out.print("ISBN: "); String isbn = t.nextLine();
                                    System.out.print("Título: "); String titulo = t.nextLine();
                                    System.out.print("Autor: "); String autor = t.nextLine();
                                    GestionBiblioteca.insertarLibro(isbn, titulo, autor);
                                }
                                case 2 -> GestionBiblioteca.mostrarLibros();
                                case 3 -> {
                                    System.out.print("ISBN del libro a actualizar: "); String isbn = t.nextLine();
                                    System.out.print("Nuevo Título: "); String tit = t.nextLine();
                                    System.out.print("Nuevo Autor: "); String aut = t.nextLine();
                                    GestionBiblioteca.actualizarLibro(isbn, tit, aut);
                                }
                                case 4 -> {
                                    System.out.print("ISBN del libro a eliminar: "); String isbn = t.nextLine();
                                    GestionBiblioteca.eliminarLibro(isbn);
                                }*/
                            }
                        } while (subOpcion != 0);
                    }
                    case 4 -> {
                        do {
                            System.out.println("""
                        --------- Menu de préstamos --------
                        1. Agregar.
                        2. Mostrar.
                        3. Actualizar.
                        4. Eliminar.
                        0. Volver.
                        ------------------------------------
                        Elige una opcion:""");
                            subOpcion = t.nextInt();
                            t.nextLine(); // Limpiar buffer

                            switch (subOpcion) {
                                /*case 1 -> {
                                    System.out.print("ISBN: "); String isbn = t.nextLine();
                                    System.out.print("Título: "); String titulo = t.nextLine();
                                    System.out.print("Autor: "); String autor = t.nextLine();
                                    GestionBiblioteca.insertarLibro(isbn, titulo, autor);
                                }
                                case 2 -> GestionBiblioteca.mostrarLibros();
                                case 3 -> {
                                    System.out.print("ISBN del libro a actualizar: "); String isbn = t.nextLine();
                                    System.out.print("Nuevo Título: "); String tit = t.nextLine();
                                    System.out.print("Nuevo Autor: "); String aut = t.nextLine();
                                    GestionBiblioteca.actualizarLibro(isbn, tit, aut);
                                }
                                case 4 -> {
                                    System.out.print("ISBN del libro a eliminar: "); String isbn = t.nextLine();
                                    GestionBiblioteca.eliminarLibro(isbn);
                                }*/
                            }
                        } while (subOpcion != 0);
                    }
                    case 5 -> {
                        do {
                            System.out.println("""
                        ----- Visualización de información ----
                        1. Usuario gestor.
                        2. Usuario normal.
                        0. Cerrar menu.
                        ---------------------------------------
                        Elige una opcion:""");
                            subOpcion = t.nextInt();
                            t.nextLine(); // Limpiar buffer

                            switch (subOpcion) {
                                /*case 1 -> {
                                    System.out.print("ISBN: "); String isbn = t.nextLine();
                                    System.out.print("Título: "); String titulo = t.nextLine();
                                    System.out.print("Autor: "); String autor = t.nextLine();
                                    GestionBiblioteca.insertarLibro(isbn, titulo, autor);
                                }
                                case 2 -> GestionBiblioteca.mostrarLibros();
                                case 3 -> {
                                    System.out.print("ISBN del libro a actualizar: "); String isbn = t.nextLine();
                                    System.out.print("Nuevo Título: "); String tit = t.nextLine();
                                    System.out.print("Nuevo Autor: "); String aut = t.nextLine();
                                    GestionBiblioteca.actualizarLibro(isbn, tit, aut);
                                }
                                case 4 -> {
                                    System.out.print("ISBN del libro a eliminar: "); String isbn = t.nextLine();
                                    GestionBiblioteca.eliminarLibro(isbn);
                                }*/
                            }
                        } while (subOpcion != 0);
                    }
                    case 0 -> System.out.println("Saliendo del sistema...");
                    default -> System.err.println("Opción no válida.");
                }
            } catch (InputMismatchException e) {
                System.err.println("Error: Introduce solo números.");
                t.next(); // Limpiar el error
            }
            System.out.println();

        } while (opcion != 0);

        System.out.println("Ha terminado el menú.");

    }
}
