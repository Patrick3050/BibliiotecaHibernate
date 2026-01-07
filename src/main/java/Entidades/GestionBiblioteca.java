package Entidades;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.awt.*;

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

    }

    public void eliminarLibro() {

    }

    public void actualizarLibro() {

    }

}
