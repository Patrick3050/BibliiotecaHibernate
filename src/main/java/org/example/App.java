package org.example;

import Entidades.Ejemplar;
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
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("biblioteca");
        EntityManager em = emf.createEntityManager();


        Query q = em.createQuery("SELECT e.libroIsbn.isbn, e.estado FROM Ejemplar e");
        List<Object[]> objects = q.getResultList();
        for (Object[] o: objects) {
            System.out.println(o[0] + " (" + o[1] + ") ");
        }

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

            try {
                opcion = t.nextInt();

                if (opcion == 1) {
                    System.out.println("""
                    --------- Menu de Libros --------
                    1. Agregar.
                    2. Mostrar.
                    3. Actualizar.
                    4. Eliminar.
                    0. Cerrar menu.
                    ---------------------------------
                    Elige una opcion:""");

                } else if (opcion == 2) {
                    System.out.println("""
                    --------- Menu de ejemplares --------
                    1. Agregar.
                    2. Mostrar.
                    3. Actualizar.
                    4. Eliminar.
                    5. Cerrar menu.
                    -------------------------------------
                    Elige una opcion:""");

                } else if (opcion == 3) {
                    System.out.println("""
                    --------- Menu de usuarios --------
                    1. Agregar.
                    2. Mostrar.
                    3. Actualizar.
                    4. Eliminar.
                    0. Cerrar menu.
                    -----------------------------------
                    Elige una opcion:""");

                } else if (opcion == 4) {
                    System.out.println("""
                    --------- Menu de préstamos --------
                    1. Agregar.
                    2. Mostrar.
                    3. Actualizar.
                    4. Eliminar.
                    0. Cerrar menu.
                    ----------------------------------
                    Elige una opcion:""");

                } else if (opcion == 5) {
                    System.out.println("""
                    --------- Visualización de información --------
                    1. Usuario gestor.
                    2. Usuario normal.
                    0. Cerrar menu.
                    -----------------------------------------------
                    Elige una opcion:""");

                } else if (opcion < 0 || opcion > 5) {
                    System.err.println("Opcion incorrecta.\n");
                }
            } catch (InputMismatchException e) {
                System.err.println("Solo se acepta números, y no se aceptan números tan largos.\n");
                t.next();
                opcion = 0;
            }
            System.out.println();

        } while (opcion != 6);

        System.out.println("Ha terminado el menú.");

    }
}
