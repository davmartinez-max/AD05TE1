package es.dam.ad05.recetario.service;

import es.dam.ad05.recetario.model.Paso;
import es.dam.ad05.recetario.model.Receta;
import es.dam.ad05.recetario.repository.PasoRepository;
import es.dam.ad05.recetario.repository.RecetaRepository;
import es.dam.ad05.recetario.web.PasoForm;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PasoService {

    private final PasoRepository pasoRepository;
    private final RecetaRepository recetaRepository;

    public PasoService(PasoRepository pasoRepository, RecetaRepository recetaRepository) {
        this.pasoRepository = pasoRepository;
        this.recetaRepository = recetaRepository;
    }

    @Transactional(readOnly = true)
    public List<Paso> listar() {
        return pasoRepository.findAllWithReceta();
    }

    @Transactional(readOnly = true)
    public Paso buscar(Long id) {
        return pasoRepository.findWithRecetaById(id)
                .orElseThrow(() -> new EntityNotFoundException("No existe el paso solicitado."));
    }

    public Paso guardar(PasoForm form) {
        Receta receta = recetaRepository.findById(form.getRecetaId())
                .orElseThrow(() -> new EntityNotFoundException("La receta seleccionada ya no existe."));
        Paso paso = form.getId() == null ? new Paso() : buscar(form.getId());
        paso.setOrden(form.getOrden());
        paso.setInstruccion(form.getInstruccion());
        if (paso.getReceta() == null) {
            receta.addPaso(paso);
        } else if (!paso.getReceta().getId().equals(receta.getId())) {
            paso.getReceta().removePaso(paso);
            receta.addPaso(paso);
        }
        return pasoRepository.save(paso);
    }

    public void eliminar(Long id) {
        Paso paso = buscar(id);
        paso.getReceta().removePaso(paso);
    }
}
