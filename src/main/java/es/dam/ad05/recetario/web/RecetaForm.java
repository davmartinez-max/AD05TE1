package es.dam.ad05.recetario.web;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class RecetaForm {

    private Long id;

    @NotBlank(message = "El nombre de la receta es obligatorio.")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres.")
    private String nombre;

    @NotBlank(message = "La descripción es obligatoria.")
    @Size(max = 500, message = "La descripción no puede superar los 500 caracteres.")
    private String descripcion;

    @NotNull(message = "El tiempo de preparación es obligatorio.")
    @Min(value = 1, message = "El tiempo debe ser de al menos 1 minuto.")
    private Integer minutosPreparacion;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public Integer getMinutosPreparacion() { return minutosPreparacion; }
    public void setMinutosPreparacion(Integer minutosPreparacion) { this.minutosPreparacion = minutosPreparacion; }
}
