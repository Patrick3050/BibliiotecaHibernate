package Entidades;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "ejemplar")
public class Ejemplar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "isbn", nullable = false)
    private Libro libroIsbn;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", columnDefinition = "ENUM('Disponible', 'Prestado', 'Dañado') DEFAULT 'Disponible'")
    private EstadoEjemplar estado = EstadoEjemplar.Disponible;

    public Ejemplar(Libro libroIsbn, EstadoEjemplar estado) {
        this.libroIsbn = libroIsbn;
        this.estado = estado;
    }

    public Ejemplar(Libro libroIsbn) {
        this.libroIsbn = libroIsbn;
    }

    public Ejemplar() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Libro getLibroIsbn() {
        return libroIsbn;
    }

    public void setLibroIsbn(Libro libroIsbn) {
        this.libroIsbn = libroIsbn;
    }

    public EstadoEjemplar getEstado() {
        return estado;
    }

    public void setEstado(EstadoEjemplar estado) {
        this.estado = estado;
    }

}