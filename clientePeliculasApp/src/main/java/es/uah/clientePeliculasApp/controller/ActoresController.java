package es.uah.clientePeliculasApp.controller;

import es.uah.clientePeliculasApp.model.Actor;
import es.uah.clientePeliculasApp.model.Pelicula;
import es.uah.clientePeliculasApp.paginator.PageRender;
import es.uah.clientePeliculasApp.service.IActoresService;
import es.uah.clientePeliculasApp.service.IPeliculasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/cactores")
public class ActoresController {

    @Autowired
    IActoresService actoresService;

    @GetMapping(value = {"/", "/home", ""})
    public String home(Model model) {
        return "home";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("titInicio", "Añadir actor");
        Actor actor = new Actor();
        model.addAttribute("actor", actor);
        return "actores/formActor";
    }

    @GetMapping("/buscar")
    public String buscar(Model model) {
        return "actores/searchActor";
    }

    @GetMapping("/listado")
    public String listadoActores(Model model, @RequestParam(name="page", defaultValue="0") int page) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Actor> listado = actoresService.buscarTodos(pageable);
        PageRender<Actor> pageRender = new PageRender<Actor>("/cactores/listado", listado);
        model.addAttribute("titInicio", "Listado de todos los actores");
        model.addAttribute("listadoActores", listado);
        model.addAttribute("page", pageRender);
        return "actores/listActor";
    }

    @GetMapping("/idactor/{id}")
    public String buscarActorPorId(Model model, @PathVariable("id") Integer id) {
        Actor actor = actoresService.buscarActorPorId(id);
        model.addAttribute("actores", actor);
        return "actores/formActor";
    }

    @GetMapping("/titulo")
    public String buscarActorPorNombre(Model model,  @RequestParam("nombre") String nombre) {
        Actor actor = actoresService.buscarActorPorNombre(nombre);
        model.addAttribute("actor", actor);
        return "actores/formActor";
    }

    @PostMapping("/guardar/")
    public String guardarActor(Model model, @ModelAttribute Actor actor, @RequestParam(name = "deletedPeliculas", required = false) String deletedPeliculas, RedirectAttributes attributes) {
        if (deletedPeliculas != null && !deletedPeliculas.isEmpty()) {
            List<Integer> deletedPeliculaIds = Arrays.stream(deletedPeliculas.split(","))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());

//            for (Integer deletedPeliculaId : deletedPeliculaIds) {
//                actoresService.eliminarActorDePelicula(actor.getIdActor(), deletedPeliculaId);
//            }
            List<Pelicula> remainingPeliculas = actor.getPeliculas().stream()
                    .filter(pelicula -> pelicula.getIdPelicula() != null)
                    .filter(pelicula -> !deletedPeliculaIds.contains(pelicula.getIdPelicula()))
                    .collect(Collectors.toList());

            actor.setPeliculas(remainingPeliculas);
        }

        actoresService.guardarActor(actor);
        model.addAttribute("titInicio", "Nuevo actor");
        attributes.addFlashAttribute("msg", "Los datos de la pelicula fueron guardados!");
        return "redirect:/cactores/listado";
    }

    @GetMapping("/editar/{id}")
    public String editarPelicula(Model model, @PathVariable("id") Integer id) {
        Actor actor = actoresService.buscarActorPorId(id);
        model.addAttribute("titInicio", "Editar actor");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = actor.getFcNacimiento().format(formatter);

        model.addAttribute("actor", actor);
        model.addAttribute("formattedDate", formattedDate);
        return "actores/formActor";
    }

    @GetMapping("/borrar/{id}")
    public String eliminarCurso(Model model, @PathVariable("id") Integer id, RedirectAttributes attributes) {
        actoresService.eliminarActor(id);
        attributes.addFlashAttribute("msg", "Los datos de la pelicula fueron borrados!");
        return "redirect:/cactores/listado";
    }
}
