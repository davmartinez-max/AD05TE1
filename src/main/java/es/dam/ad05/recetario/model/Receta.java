package es.dam.ad05.recetario.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "AD05_RECETAS")
public class Receta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre de la receta es obligatorio.")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres.")
    @Column(nullable = false, length = 100)
    private String nombre;

    @NotBlank(message = "La descripción es obligatoria.")
    @Size(max = 500, message = "La descripción no puede superar los 500 caracteres.")
    @Column(nullable = false, length = 500)
    private String descripcion;

    @Min(value = 1, message = "El tiempo debe ser de al menos 1 minuto.")
    @Column(name = "minutos_preparacion", nullable = false)
    private Integer minutosPreparacion;

    @OneToMany(mappedBy = "receta", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @OrderBy("orden ASC")
    private List<Paso> pasos = new ArrayList<>();

    public Receta() {
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public Integer getMinutosPreparacion() { return minutosPreparacion; }
    public void setMinutosPreparacion(Integer minutosPreparacion) { this.minutosPreparacion = minutosPreparacion; }
    public List<Paso> getPasos() { return pasos; }

    public void addPaso(Paso paso) {
        pasos.add(paso);
        paso.setReceta(this);
    }

    public void removePaso(Paso paso) {
        pasos.remove(paso);
        paso.setReceta(null);
    }
}
