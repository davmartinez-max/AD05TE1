package es.dam.ad05.recetario.web;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PasoForm {

    private Long id;

    @NotNull(message = "El número de paso es obligatorio.")
    @Min(value = 1, message = "El número de paso debe ser mayor que cero.")
    private Integer orden;

    @NotBlank(message = "La instrucción es obligatoria.")
    @Size(max = 1000, message = "La instrucción no puede superar los 1000 caracteres.")
    private String instruccion;

    @NotNull(message = "Selecciona una receta.")
    private Long recetaId;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Integer getOrden() { return orden; }
    public void setOrden(Integer orden) { this.orden = orden; }
    public String getInstruccion() { return instruccion; }
    public void setInstruccion(String instruccion) { this.instruccion = instruccion; }
    public Long getRecetaId() { return recetaId; }
    public void setRecetaId(Long recetaId) { this.recetaId = recetaId; }
}
