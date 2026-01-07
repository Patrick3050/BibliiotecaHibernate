package Entidades;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

import java.util.List;

public class GestionBiblioteca {
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("biblioteca");
    private static EntityManager em = emf.createEntityManager();

    public void insertarLibro(String isbn, String titulo, String autor) {
        try {
            Libro libro = new Libro(isbn, titulo, autor);
            em.getTransaction().begin();
            em.persist(libro);
            em.getTransaction().commit();
        } catch (jakarta.validation.ConstraintViolationException e) {
            // Si la validación falla antes de ir a la BD
            System.err.println("--- ERROR DE VALIDACIÓN ---");
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
        } catch (jakarta.persistence.RollbackException e) {
            // Si el error ocurre durante el commit (que es tu caso actual)
            System.err.println("--- NO SE PUDO GUARDAR ---");
            System.out.println("Causa: " + e.getCause().getMessage());
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
        } catch (Exception e) {
            System.out.println("Error");
        }
    }

    public void mostrarLibros() {
        Query q = em.createQuery("SELECT l.isbn, l.titulo, l.autor FROM Libro l");
        System.out.println("Libros registrados");
        List<Object[]> objects = q.getResultList();
        for (Object[] o: objects) {
            System.out.println("ISBN: "+ o[0] + ", Titulo: " + o[1] + ", Autor: " + o[2]);
        }

    }

    public void eliminarLibro(String isbn) {
        try {
            em.getTransaction().begin();
            Libro libro = em.find(Libro.class, isbn);
            if (libro != null) {
                em.remove(libro);
                em.getTransaction().commit();
                System.out.println("Se ha eliminado el libro y sus ejemplares.");
            } else {
                System.err.println("El libro no existe.");
                em.getTransaction().rollback();
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            System.err.println("Error al eliminar.");
        }
    }

    public static void actualizarLibro(String isbn, String nuevoTitulo, String nuevoAutor) {
        try {
            em.getTransaction().begin();
            Libro libro = em.find(Libro.class, isbn);

            if (libro != null) {
                libro.setTitulo(nuevoTitulo); // Usamos los setters de la clase Libro
                libro.setAutor(nuevoAutor);
                // No hace falta em.merge(libro) porque ya está "managed" por el find
                em.getTransaction().commit();
                System.out.println("Datos del libro actualizado.");
            } else {
                System.out.println("No se encontró el libro con ISBN: " + isbn);
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            System.err.println("Error al actualizar.");
        }
    }

}
