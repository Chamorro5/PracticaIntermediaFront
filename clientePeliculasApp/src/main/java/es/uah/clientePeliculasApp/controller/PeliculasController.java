package es.uah.clientePeliculasApp.controller;

import es.uah.clientePeliculasApp.model.*;
import es.uah.clientePeliculasApp.paginator.*;
import es.uah.clientePeliculasApp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/cpeliculas")
public class PeliculasController {

    @Autowired
    IPeliculasService peliculasService;

    @Autowired
    IActoresService actoresService;

    @GetMapping(value = {"/", "/home", ""})
    public String home(Model model) {
        return "home";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("titInicio", "Añadir pelicula");
        Pelicula pelicula = new Pelicula();
        model.addAttribute("pelicula", pelicula);
        return "peliculas/formPelicula";
    }

    @GetMapping("/buscar")
    public String buscar(Model model) {
        return "peliculas/searchPelicula";
    }

    @GetMapping("/listado")
    public String listadoPeliculas(Model model, @RequestParam(name="page", defaultValue="0") int page) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Pelicula> listado = peliculasService.buscarTodos(pageable);
        PageRender<Pelicula> pageRender = new PageRender<Pelicula>("/cpeliculas/listado", listado);
        model.addAttribute("titInicio", "Listado de todos las peliculas");
        model.addAttribute("listadoPeliculas", listado);
        model.addAttribute("page", pageRender);
        return "peliculas/listPelicula";
    }

    @GetMapping("/idpelicula/{id}")
    public String buscarPeliculaPorId(Model model, @PathVariable("id") Integer id) {
        Pelicula pelicula = peliculasService.buscarPeliculaPorId(id);
        model.addAttribute("peliculas", pelicula);
        return "peliculas/formPelicula";
    }

    @GetMapping("/titulo")
    public String buscarPeliculaPorTitulo(Model model, @RequestParam(name="page", defaultValue="0") int page, @RequestParam("titulo") String titulo) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Pelicula> listado;
        if (titulo.equals("")) {
            listado = peliculasService.buscarTodos(pageable);
        } else {
            listado = peliculasService.buscarPeliculasPorTitulo(titulo, pageable);
        }
        PageRender<Pelicula> pageRender = new PageRender<Pelicula>("/listado", listado);
        model.addAttribute("titInicio", "Listado de peliculas por titulo");
        model.addAttribute("listadoPeliculas", listado);
        model.addAttribute("page", pageRender);
        return "peliculas/listPelicula";
    }

    @GetMapping("/genero")
    public String buscarCursosPorGenero(Model model, @RequestParam(name="page", defaultValue="0") int page, @RequestParam("genero") String genero) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Pelicula> listado = peliculasService.buscarPeliculasPorGenero(genero, pageable);
        PageRender<Pelicula> pageRender = new PageRender<Pelicula>("/listado", listado);
        model.addAttribute("titInicio", "Listado de peliculas por titulo");
        model.addAttribute("listadoPeliculas", listado);
        model.addAttribute("page", pageRender);
        return "peliculas/listPelicula";
    }

    @GetMapping("/direccion")
    public String buscarPeliculasPorDireccion(Model model, @RequestParam(name="page", defaultValue="0") int page, @RequestParam("direccion") String direccion) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Pelicula> listado;
        if (direccion.equals("")) {
            listado = peliculasService.buscarTodos(pageable);
        } else {
            listado = peliculasService.buscarPeliculasPorDireccion(direccion, pageable);
        }
        PageRender<Pelicula> pageRender = new PageRender<Pelicula>("/listado", listado);
        model.addAttribute("titInicio", "Listado de peliculas por titulo");
        model.addAttribute("listadoPeliculas", listado);
        model.addAttribute("page", pageRender);
        return "peliculas/listPelicula";
    }

    @PostMapping("/guardar/")
    public String guardarPelicula(Model model, @ModelAttribute Pelicula pelicula,  @RequestParam(name = "deletedActors", required = false) String deletedActors, RedirectAttributes attributes) {
        if (deletedActors != null && !deletedActors.isEmpty()) {
            List<Integer> deletedActorIds = Arrays.stream(deletedActors.split(","))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());

            for (Integer deletedActorId : deletedActorIds) {
                actoresService.eliminarActorDePelicula(deletedActorId, pelicula.getIdPelicula());
            }
        }
        peliculasService.guardarPelicula(pelicula);
        attributes.addFlashAttribute("msg", "Los datos de la pelicula fueron guardados!");
        return "redirect:/cpeliculas/listado";
    }

    @GetMapping("/editar/{id}")
    public String editarPelicula(Model model, @PathVariable("id") Integer id) {
        Pelicula pelicula = peliculasService.buscarPeliculaPorId(id);
        model.addAttribute("titInicio", "Editar pelicula");
        model.addAttribute("pelicula", pelicula);
        return "peliculas/formPelicula";
    }

    @GetMapping("/borrar/{id}")
    public String eliminarCurso(Model model, @PathVariable("id") Integer id, RedirectAttributes attributes) {
        peliculasService.eliminarPelicula(id);
        attributes.addFlashAttribute("msg", "Los datos de la pelicula fueron borrados!");
        return "redirect:/cpeliculas/listado";
    }
}
