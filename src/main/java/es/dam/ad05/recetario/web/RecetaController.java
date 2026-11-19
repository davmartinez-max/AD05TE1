package es.dam.ad05.recetario.web;

import es.dam.ad05.recetario.service.RecetaService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RecetaController {

    private final RecetaService recetaService;

    public RecetaController(RecetaService recetaService) {
        this.recetaService = recetaService;
    }

    @GetMapping("/recetas")
    public String listar(Model model) {
        model.addAttribute("recetas", recetaService.listar());
        return "recetas/lista";
    }

    @GetMapping("/recetas/nueva")
    public String nueva(Model model) {
        model.addAttribute("recetaForm", new RecetaForm());
        model.addAttribute("titulo", "Nueva receta");
        return "recetas/formulario";
    }

    @PostMapping("/recetas/guardar")
    public String guardar(@Valid @ModelAttribute("recetaForm") RecetaForm receta,
                          BindingResult resultado, Model model, RedirectAttributes redirect) {
        if (resultado.hasErrors()) {
            model.addAttribute("titulo", receta.getId() == null ? "Nueva receta" : "Editar receta");
            return "recetas/formulario";
        }
        boolean nueva = receta.getId() == null;
        try {
            recetaService.guardar(receta);
        } catch (EntityNotFoundException ex) {
            redirect.addFlashAttribute("error", ex.getMessage());
            return "redirect:/recetas";
        }
        redirect.addFlashAttribute("mensaje", nueva ? "Receta creada." : "Receta actualizada.");
        return "redirect:/recetas";
    }

    @GetMapping("/recetas/{id}/editar")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes redirect) {
        try {
            var receta = recetaService.buscar(id);
            RecetaForm form = new RecetaForm();
            form.setId(receta.getId());
            form.setNombre(receta.getNombre());
            form.setDescripcion(receta.getDescripcion());
            form.setMinutosPreparacion(receta.getMinutosPreparacion());
            model.addAttribute("recetaForm", form);
            model.addAttribute("titulo", "Editar receta");
            return "recetas/formulario";
        } catch (EntityNotFoundException ex) {
            redirect.addFlashAttribute("error", ex.getMessage());
            return "redirect:/recetas";
        }
    }

    @GetMapping("/recetas/{id}")
    public String detalle(@PathVariable Long id, Model model, RedirectAttributes redirect) {
        try {
            model.addAttribute("receta", recetaService.buscar(id));
            return "recetas/detalle";
        } catch (EntityNotFoundException ex) {
            redirect.addFlashAttribute("error", ex.getMessage());
            return "redirect:/recetas";
        }
    }

    @GetMapping("/recetas/{id}/eliminar")
    public String confirmarBorrado(@PathVariable Long id, Model model, RedirectAttributes redirect) {
        try {
            model.addAttribute("receta", recetaService.buscar(id));
            return "recetas/confirmar-borrado";
        } catch (EntityNotFoundException ex) {
            redirect.addFlashAttribute("error", ex.getMessage());
            return "redirect:/recetas";
        }
    }

    @PostMapping("/recetas/{id}/eliminar")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirect) {
        try {
            recetaService.eliminar(id);
            redirect.addFlashAttribute("mensaje", "Receta y sus pasos eliminados.");
        } catch (EntityNotFoundException ex) {
            redirect.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/recetas";
    }
}
