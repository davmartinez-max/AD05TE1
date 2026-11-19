package es.dam.ad05.recetario.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "AD05_PASOS")
public class Paso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El número de paso es obligatorio.")
    @Min(value = 1, message = "El número de paso debe ser mayor que cero.")
    @Column(nullable = false)
    private Integer orden;

    @NotBlank(message = "La instrucción es obligatoria.")
    @Size(max = 1000, message = "La instrucción no puede superar los 1000 caracteres.")
    @Column(nullable = false, length = 1000)
    private String instruccion;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "receta_id", nullable = false)
    private Receta receta;

    public Paso() {
    }

    public Long getId() { return id; }
    public Integer getOrden() { return orden; }
    public void setOrden(Integer orden) { this.orden = orden; }
    public String getInstruccion() { return instruccion; }
    public void setInstruccion(String instruccion) { this.instruccion = instruccion; }
    public Receta getReceta() { return receta; }
    public void setReceta(Receta receta) { this.receta = receta; }
}
