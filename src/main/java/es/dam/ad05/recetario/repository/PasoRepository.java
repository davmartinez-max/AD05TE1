package es.dam.ad05.recetario.repository;

import es.dam.ad05.recetario.model.Paso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.List;
import java.util.Optional;

public interface PasoRepository extends JpaRepository<Paso, Long> {

    @Query("select p from Paso p join fetch p.receta order by p.receta.nombre, p.orden")
    List<Paso> findAllWithReceta();

    @EntityGraph(attributePaths = "receta")
    Optional<Paso> findWithRecetaById(Long id);
}
