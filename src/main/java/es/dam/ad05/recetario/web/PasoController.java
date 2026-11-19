package es.dam.ad05.recetario.web;

import es.dam.ad05.recetario.model.Paso;
import es.dam.ad05.recetario.model.Receta;
import es.dam.ad05.recetario.service.PasoService;
import es.dam.ad05.recetario.service.RecetaService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class PasoController {

    private final PasoService pasoService;
    private final RecetaService recetaService;

    public PasoController(PasoService pasoService, RecetaService recetaService) {
        this.pasoService = pasoService;
        this.recetaService = recetaService;
    }

    @GetMapping("/pasos")
    public String listar(Model model) {
        model.addAttribute("pasos", pasoService.listar());
        return "pasos/lista";
    }

    @GetMapping("/pasos/nuevo")
    public String nuevo(@RequestParam(required = false) Long recetaId, Model model, RedirectAttributes redirect) {
        List<Receta> recetas = recetaService.listar();
        if (recetas.isEmpty()) {
            redirect.addFlashAttribute("error", "Crea primero una receta para poder añadir pasos.");
            return "redirect:/recetas/nueva";
        }
        model.addAttribute("recetas", recetas);
        PasoForm form = new PasoForm();
        form.setRecetaId(recetaId);
        model.addAttribute("pasoForm", form);
        model.addAttribute("titulo", "Nuevo paso");
        return "pasos/formulario";
    }

    @PostMapping("/pasos/guardar")
    public String guardar(@Valid @ModelAttribute("pasoForm") PasoForm form,
                          BindingResult resultado, Model model, RedirectAttributes redirect) {
        List<Receta> recetas = recetaService.listar();
        if (form.getRecetaId() != null && recetas.stream().noneMatch(r -> r.getId().equals(form.getRecetaId()))) {
            resultado.addError(new FieldError("pasoForm", "recetaId", "La receta seleccionada no existe. Elige otra."));
        }
        if (recetas.isEmpty()) {
            redirect.addFlashAttribute("error", "Crea primero una receta para poder añadir pasos.");
            return "redirect:/recetas/nueva";
        }
        if (resultado.hasErrors()) {
            model.addAttribute("recetas", recetas);
            model.addAttribute("titulo", form.getId() == null ? "Nuevo paso" : "Editar paso");
            return "pasos/formulario";
        }
        try {
            boolean nuevo = form.getId() == null;
            pasoService.guardar(form);
            redirect.addFlashAttribute("mensaje", nuevo ? "Paso creado." : "Paso actualizado.");
            return "redirect:/pasos";
        } catch (EntityNotFoundException ex) {
            resultado.addError(new FieldError("pasoForm", "recetaId", ex.getMessage()));
            model.addAttribute("recetas", recetaService.listar());
            model.addAttribute("titulo", form.getId() == null ? "Nuevo paso" : "Editar paso");
            return "pasos/formulario";
        }
    }

    @GetMapping("/pasos/{id}/editar")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes redirect) {
        try {
            Paso paso = pasoService.buscar(id);
            PasoForm form = new PasoForm();
            form.setId(paso.getId());
            form.setOrden(paso.getOrden());
            form.setInstruccion(paso.getInstruccion());
            form.setRecetaId(paso.getReceta().getId());
            model.addAttribute("pasoForm", form);
            model.addAttribute("recetas", recetaService.listar());
            model.addAttribute("titulo", "Editar paso");
            return "pasos/formulario";
        } catch (EntityNotFoundException ex) {
            redirect.addFlashAttribute("error", ex.getMessage());
            return "redirect:/pasos";
        }
    }

    @GetMapping("/pasos/{id}/eliminar")
    public String confirmarBorrado(@PathVariable Long id, Model model, RedirectAttributes redirect) {
        try {
            model.addAttribute("paso", pasoService.buscar(id));
            return "pasos/confirmar-borrado";
        } catch (EntityNotFoundException ex) {
            redirect.addFlashAttribute("error", ex.getMessage());
            return "redirect:/pasos";
        }
    }

    @PostMapping("/pasos/{id}/eliminar")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirect) {
        try {
            pasoService.eliminar(id);
            redirect.addFlashAttribute("mensaje", "Paso eliminado. La receta sigue intacta.");
        } catch (EntityNotFoundException ex) {
            redirect.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/pasos";
    }
}
