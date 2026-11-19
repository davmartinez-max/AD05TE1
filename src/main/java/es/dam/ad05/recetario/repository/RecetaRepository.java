package es.dam.ad05.recetario.repository;

import es.dam.ad05.recetario.model.Receta;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecetaRepository extends JpaRepository<Receta, Long> {

    @EntityGraph(attributePaths = "pasos")
    Optional<Receta> findWithPasosById(Long id);
}
