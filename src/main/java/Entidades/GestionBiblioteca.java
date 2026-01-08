package Entidades;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

import java.time.LocalDate;
import java.util.List;

public class GestionBiblioteca {
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("biblioteca");
    private static EntityManager em = emf.createEntityManager();

    public static boolean esISBN13Valido(String isbn) {
        // 1. Validar que tenga exactamente 13 dígitos numéricos
        if (isbn == null || !isbn.matches("\\d{13}")) {
            return false;
        }

        // 2. Filtro de prefijo 978
        if (!isbn.startsWith("978")) return false;

        // 3. Algoritmo del Dígito de Control
        int suma = 0;
        for (int i = 0; i < 12; i++) {
            int digito = Character.getNumericValue(isbn.charAt(i));
            // Posiciones impares se multiplican por 1, pares por 3
            suma += (i % 2 == 0) ? digito : digito * 3;
        }

        int digitoControlCalculado = (10 - (suma % 10)) % 10;
        int ultimoDigitoReal = Character.getNumericValue(isbn.charAt(12));

        return digitoControlCalculado == ultimoDigitoReal;
    }

    public void insertarLibro(String isbn, String titulo, String autor) {
        if (!esISBN13Valido(isbn)) {
            System.err.println("ERROR: El ISBN debe empezar por 978 y ser un código válido de 13 dígitos.");
            System.err.flush(); // Fuerza la salida inmediata del error
            return;
        }

        try {
            Libro libro = new Libro(isbn, titulo, autor);
            em.getTransaction().begin();
            em.persist(libro);
            em.getTransaction().commit();
            System.out.println("Libro registrado exitosamente.\n");
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            System.err.println("Error al insertar.\n");
            System.err.flush(); // Fuerza la salida inmediata del error
        }
    }

    public void mostrarLibros() {
        Query q = em.createQuery("SELECT l.isbn, l.titulo, l.autor FROM Libro l");
        System.out.println("Libros registrados");
        List<Object[]> objects = q.getResultList();
        for (Object[] o: objects) {
            System.out.println("ISBN: "+ o[0] + ", Titulo: " + o[1] + ", Autor: " + o[2]);
        }
        System.out.println();
    }

    public void eliminarLibro(String isbn) {
        try {
            em.getTransaction().begin();
            Libro libro = em.find(Libro.class, isbn);
            if (libro != null) {
                em.remove(libro);
                em.getTransaction().commit();
                System.out.println("Se ha eliminado el libro y sus ejemplares.\n");
            } else {
                System.err.println("El libro no existe.\n");
                em.getTransaction().rollback();
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            System.err.println("Error al eliminar.\n");
            System.err.flush(); // Fuerza la salida inmediata del error
        }
    }

    public void actualizarLibro(String isbn, String nuevoTitulo, String nuevoAutor) {
        try {
            em.getTransaction().begin();
            Libro libro = em.find(Libro.class, isbn);

            if (libro != null) {
                libro.setTitulo(nuevoTitulo); // Usamos los setters de la clase Libro
                libro.setAutor(nuevoAutor);
                em.getTransaction().commit();
                System.out.println("Datos del libro actualizado.");
            } else {
                System.err.println("No se encontró el libro con ISBN: " + isbn);
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                System.err.flush(); // Fuerza la salida inmediata del error
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            System.err.println("Error al actualizar.");
            System.err.flush(); // Fuerza la salida inmediata del error
        }
    }


    public void insertarEjemplar(String isbn) {
        try {
            // 1. Buscamos si el libro existe
            Libro libro = em.find(Libro.class, isbn);

            if (libro != null) {
                em.getTransaction().begin();
                // 2. Creamos el ejemplar (el estado por defecto es Disponible)
                Ejemplar ejemplar = new Ejemplar(libro);
                em.persist(ejemplar);
                em.getTransaction().commit();
                System.out.println("Ejemplar registrado correctamente para el libro: " + libro.getTitulo() + "\n");
            } else {
                System.err.println("Error: No existe ningún libro con el ISBN " + isbn + "\n");
                System.err.flush();
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            System.err.println("Error al insertar.\n");
            System.err.flush(); // Fuerza la salida inmediata del error
        }
    }

    public void mostrarEjemplaresYstockDisponible() {
        try {
            // Query para contar ejemplares con estado 'Disponible'
            Query q = em.createQuery("SELECT COUNT(e) FROM Ejemplar e WHERE e.estado = :estado");
            q.setParameter("estado", EstadoEjemplar.Disponible);

            Long total = (Long) q.getSingleResult();
            System.out.println("-------------------------------------");
            System.out.println("STOCK TOTAL DISPONIBLE: " + total + " unidades.");
            System.out.println("-------------------------------------");

            System.out.println("\nTodos los ejemplares:");
            List<Ejemplar> lista = em.createQuery("SELECT e FROM Ejemplar e", Ejemplar.class).getResultList();
            for (Ejemplar ej : lista) {
                System.out.println("ID: " + ej.getId() + " | Libro: " + ej.getLibroIsbn().getTitulo() + " | Estado: " + ej.getEstado());
            }
            System.out.println();
        } catch (Exception e) {
            System.err.println("Error al visualizar el stock.\n");
            System.err.flush(); // Fuerza la salida inmediata del error
        }
    }

    public void eliminarEjemplar(int id) {
        try {
            em.getTransaction().begin();
            Ejemplar ejemplar = em.find(Ejemplar.class, id); // Buscamos por ID

            if (ejemplar != null) {
                em.remove(ejemplar);
                em.getTransaction().commit();
                System.out.println("Ejemplar con ID " + id + " eliminado correctamente.\n");
            } else {
                System.err.println("No se encontró el ejemplar con ID: " + id + "\n");
                em.getTransaction().rollback();
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            System.err.println("Error al eliminar el ejemplar.\n");
            System.err.flush(); // Fuerza la salida inmediata del error
        }
    }

    public void actualizarEstadoEjemplar(int id, int opcionEstado) {
        try {
            em.getTransaction().begin();
            Ejemplar ejemplar = em.find(Ejemplar.class, id);

            if (ejemplar != null) {
                // Mapeamos la opción del menú al Enum
                EstadoEjemplar nuevoEstado = switch (opcionEstado) {
                    case 1 -> EstadoEjemplar.Prestado;
                    case 2 -> EstadoEjemplar.Dañado;
                    default -> null;
                };

                if (nuevoEstado != null) {
                    ejemplar.setEstado(nuevoEstado);
                    em.getTransaction().commit();
                    System.out.println("Estado del ejemplar " + id + " actualizado a: " + nuevoEstado + "\n");
                } else {
                    System.err.println("Opción de estado no válida.\n");
                    em.getTransaction().rollback();
                    System.err.flush(); // Fuerza la salida inmediata del error
                }
            } else {
                System.err.println("No se encontró el ejemplar.\n");
                em.getTransaction().rollback();
                System.err.flush(); // Fuerza la salida inmediata del error
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            System.err.println("Error al actualizar el estado.\n");
            System.err.flush(); // Fuerza la salida inmediata del error
        }
    }


    public void insertarUsuario(String dni, String nombre, String email, String password, int tipoOp)  {
        try {
            em.getTransaction().begin();

            // Mapeo de la opción numérica al Enum UsuarioTipo
            UsuarioTipo tipo = (tipoOp == 2) ? UsuarioTipo.administrador : UsuarioTipo.normal;

            Usuario usuario = new Usuario(dni, nombre, email, password, tipo);
            em.persist(usuario);
            em.getTransaction().commit();
            System.out.println("Usuario '" + nombre + "' registrado con éxito como " + tipo + ".\n");
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            System.err.println("Error al registrar usuario.\n");
            System.err.flush();
        }
    }

    public void penalizarUsuario(String dni, int dias) {
        try {
            em.getTransaction().begin();
            // Buscamos al usuario por su DNI (asumiendo que es único o usando una Query)
            Query q = em.createQuery("SELECT u FROM Usuario u WHERE u.dni = :dni");
            q.setParameter("dni", dni);
            Usuario usuario = (Usuario) q.getSingleResult();

            if (usuario != null) {
                // Calculamos la fecha de fin de penalización (hoy + días)
                LocalDate fechaFin = LocalDate.now().plusDays(dias);
                usuario.setPenalizacionHasta(fechaFin);

                em.getTransaction().commit();
                System.out.println("Usuario " + usuario.getNombre() + " penalizado hasta: " + fechaFin + "\n");
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            System.err.println("Error al penalizar: No se encontró el usuario o error de datos.\n");
            System.err.flush();
        }
    }

    public void mostrarUsuarios() {
        List<Usuario> usuarios = em.createQuery("SELECT u FROM Usuario u", Usuario.class).getResultList();
        System.out.println("--- LISTADO DE USUARIOS ---");
        for (Usuario u : usuarios) {
            String estado = (u.getPenalizacionHasta() != null && u.getPenalizacionHasta().isAfter(LocalDate.now()))
                    ? "PENALIZADO hasta " + u.getPenalizacionHasta()
                    : "Activo";
            System.out.println("DNI: " + u.getDni() + " | Nombre: " + u.getNombre() + " | Tipo: " + u.getTipo() + " | Estado: " + estado);
        }
        System.out.println();
    }

    public void eliminarUsuario(String dni) {
        try {
            em.getTransaction().begin();
            Usuario u = em.createQuery("SELECT u FROM Usuario u WHERE u.dni = :dni", Usuario.class)
                    .setParameter("dni", dni)
                    .getSingleResult();

            em.remove(u);
            em.getTransaction().commit();
            System.out.println("Usuario eliminado.\n");
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            System.err.println("No se pudo eliminar: El usuario no existe o tiene préstamos activos.");
            System.err.flush();
        }
    }

    public void actualizarUsuario(String dni, String nuevoNombre, String nuevoEmail) {
        try {
            em.getTransaction().begin();
            // Buscamos por DNI usando Query ya que el ID es interno
            Usuario u = em.createQuery("SELECT u FROM Usuario u WHERE u.dni = :dni", Usuario.class)
                    .setParameter("dni", dni)
                    .getSingleResult();

            u.setNombre(nuevoNombre);
            u.setEmail(nuevoEmail);

            em.getTransaction().commit();
            System.out.println("Usuario actualizado correctamente.\n");
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            System.err.println("Error: No se encontró el usuario con DNI " + dni + "\n");
            System.err.flush();
        }
    }


    // REGISTRAR PRÉSTAMO CON VALIDACIONES
    public void registrarPrestamo(String dni, int ejemplarId) {
        try {
            // 1. Buscar Usuario y Ejemplar
            Usuario usuario = em.createQuery("SELECT u FROM Usuario u WHERE u.dni = :dni", Usuario.class)
                    .setParameter("dni", dni).getSingleResult();
            Ejemplar ejemplar = em.find(Ejemplar.class, ejemplarId);

            if (usuario == null || ejemplar == null) {
                System.err.println("Usuario o Ejemplar no encontrado.\n");
                System.err.flush();
                return;
            }

            // --- VALIDACIONES ---
            // A. Penalización activa
            if (usuario.getPenalizacionHasta() != null && usuario.getPenalizacionHasta().isAfter(LocalDate.now())) {
                System.err.println("Bloqueado: El usuario tiene una penalización hasta " + usuario.getPenalizacionHasta() + "\n");
                System.err.flush();
                return;
            }

            // B. Máximo 3 préstamos activos (donde fechaDevolucion es null)
            Long activos = em.createQuery("SELECT COUNT(p) FROM Prestamo p WHERE p.usuario = :u AND p.fechaDevolucion IS NULL", Long.class)
                    .setParameter("u", usuario).getSingleResult();
            if (activos >= 3) {
                System.err.println("Bloqueado: El usuario ya tiene 3 préstamos activos.\n");
                System.err.flush();
                return;
            }

            // C. Estado del ejemplar
            if (ejemplar.getEstado() != EstadoEjemplar.Disponible) {
                System.err.println("Bloqueado: El ejemplar no está disponible (Estado: " + ejemplar.getEstado() + ")\n");
                System.err.flush();
                return;
            }

            // --- PROCESAR PRÉSTAMO ---
            em.getTransaction().begin();

            Prestamo prestamo = new Prestamo();
            prestamo.setUsuario(usuario);
            prestamo.setEjemplar(ejemplar);
            prestamo.setFechaInicio(LocalDate.now());

            ejemplar.setEstado(EstadoEjemplar.Prestado); // Actualizamos estado

            em.persist(prestamo);
            em.getTransaction().commit();
            System.out.println("Préstamo registrado. Fecha límite: " + LocalDate.now().plusDays(15) + "\n");

        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            System.err.println("Error al procesar préstamo.\n");
            System.err.flush();
        }
    }

    // REGISTRAR DEVOLUCIÓN Y PENALIZAR
    public void registrarDevolucion(int ejemplarId) {
        try {
            // Buscamos el préstamo activo (sin fecha de devolución)
            Prestamo p = em.createQuery("SELECT p FROM Prestamo p WHERE p.ejemplar.id = :id AND p.fechaDevolucion IS NULL", Prestamo.class)
                    .setParameter("id", ejemplarId).getSingleResult();

            em.getTransaction().begin();
            p.setFechaDevolucion(LocalDate.now());
            p.getEjemplar().setEstado(EstadoEjemplar.Disponible); // El libro vuelve a estar libre

            // Lógica de penalización (15 días si es tarde)
            if (p.getFechaDevolucion().isAfter(p.getFechaInicio().plusDays(15))) {
                Usuario u = p.getUsuario();
                LocalDate inicioPena = (u.getPenalizacionHasta() != null && u.getPenalizacionHasta().isAfter(LocalDate.now()))
                        ? u.getPenalizacionHasta() : LocalDate.now();
                u.setPenalizacionHasta(inicioPena.plusDays(15));
            }

            em.getTransaction().commit();
            System.out.println("Devolución procesada correctamente.\n");
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            System.err.println("No se encontró un préstamo activo para ese ejemplar.\n");
            System.err.flush();
        }
    }

    // MOSTRAR (READ): Ver todos los préstamos y sus estados
    public void mostrarPrestamos() {
        try {
            List<Prestamo> lista = em.createQuery("SELECT p FROM Prestamo p", Prestamo.class).getResultList();
            System.out.println("\n--- LISTADO DE PRÉSTAMOS ---");
            if (lista.isEmpty()) System.out.println("No hay préstamos registrados.\n");

            for (Prestamo p : lista) {
                String estado = (p.getFechaDevolucion() == null) ? "ACTIVO" : "DEVUELTO (" + p.getFechaDevolucion() + ")";
                System.out.println("ID: " + p.getId() +
                        " | Usuario: " + p.getUsuario().getNombre() +
                        " | Libro: " + p.getEjemplar().getLibroIsbn().getTitulo() +
                        " | Estado: " + estado);
            }
            System.out.println();
        } catch (Exception e) {
            System.err.println("Error al obtener la lista de préstamos.\n");
            System.err.flush();
        }
    }

    // ELIMINAR (DELETE): Borrar un registro de préstamo
    public void eliminarPrestamo(int id) {
        try {
            em.getTransaction().begin();
            Prestamo p = em.find(Prestamo.class, id);

            if (p != null) {
                // SEGURIDAD: Si el préstamo estaba activo, liberamos el ejemplar antes de borrar el registro
                if (p.getFechaDevolucion() == null) {
                    p.getEjemplar().setEstado(EstadoEjemplar.Disponible);
                }
                em.remove(p);
                em.getTransaction().commit();
                System.out.println("Registro de préstamo " + id + " eliminado correctamente.\n");
            } else {
                System.err.println("No existe el préstamo con ID: " + id + "\n");
                em.getTransaction().rollback();
                System.err.flush();
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            System.err.println("Error al eliminar el préstamo.\n");
            System.err.flush();
        }
    }

    // ACTUALIZAR (UPDATE): Corregir fechas manualmente (Opcional pero útil)
    public void corregirFechaPrestamo(int id, LocalDate nuevaFechaInicio) {
        try {
            em.getTransaction().begin();
            Prestamo p = em.find(Prestamo.class, id);
            if (p != null) {
                p.setFechaInicio(nuevaFechaInicio);
                em.getTransaction().commit();
                System.out.println("Fecha de inicio corregida.\n");
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            System.err.println("Error al actualizar el préstamo.\n");
            System.err.flush();
        }
    }


    public Usuario buscarUsuarioPorDni(String dni) {
        try {
            return em.createQuery("SELECT u FROM Usuario u WHERE u.dni = :dni", Usuario.class)
                    .setParameter("dni", dni)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public void mostrarMisPrestamos(String dni) {
        List<Prestamo> lista = em.createQuery("SELECT p FROM Prestamo p WHERE p.usuario.dni = :dni", Prestamo.class)
                .setParameter("dni", dni)
                .getResultList();
        System.out.println("--- MIS PRÉSTAMOS ---");
        for (Prestamo p : lista) {
            System.out.println("Libro: " + p.getEjemplar().getLibroIsbn().getTitulo() +
                    " | Fecha Inicio: " + p.getFechaInicio());
        }
    }



}
