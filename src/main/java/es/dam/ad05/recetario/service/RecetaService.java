package es.dam.ad05.recetario.service;

import es.dam.ad05.recetario.model.Receta;
import es.dam.ad05.recetario.repository.RecetaRepository;
import es.dam.ad05.recetario.web.RecetaForm;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RecetaService {

    private final RecetaRepository recetaRepository;

    public RecetaService(RecetaRepository recetaRepository) {
        this.recetaRepository = recetaRepository;
    }

    @Transactional(readOnly = true)
    public List<Receta> listar() {
        return recetaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Receta buscar(Long id) {
        return recetaRepository.findWithPasosById(id)
                .orElseThrow(() -> new EntityNotFoundException("No existe la receta solicitada."));
    }

    public Receta guardar(RecetaForm form) {
        Receta receta = form.getId() == null ? new Receta() : buscar(form.getId());
        receta.setNombre(form.getNombre());
        receta.setDescripcion(form.getDescripcion());
        receta.setMinutosPreparacion(form.getMinutosPreparacion());
        return recetaRepository.save(receta);
    }

    public void eliminar(Long id) {
        Receta receta = buscar(id);
        recetaRepository.delete(receta);
    }
}
