package es.uah.clientePeliculasApp;

import es.uah.clientePeliculasApp.model.Pelicula;
import es.uah.clientePeliculasApp.paginator.PageRender;
import es.uah.clientePeliculasApp.service.IPeliculasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cpeliculas")
public class PeliculasController {

    @Autowired
    IPeliculasService peliculasService;

    @GetMapping(value = {"/", "/home", ""})
    public String home(Model model) {
        return "home";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("titulo", "Nueva pelicula");
        Pelicula pelicula = new Pelicula();
        model.addAttribute("curso", pelicula);
        return "peliculas/formPelicula";
    }

    @GetMapping("/buscar")
    public String buscar(Model model) {
        return "peliculas/searchPelicula";
    }

    @GetMapping("/listado")
    public String listadoCursos(Model model, @RequestParam(name="page", defaultValue="0") int page) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Pelicula> listado = peliculasService.buscarTodos(pageable);
        PageRender<Pelicula> pageRender = new PageRender<Pelicula>("/cpeliculas/listado", listado);
        model.addAttribute("titulo", "Listado de todos las peliculas");
        model.addAttribute("listadoPeliculas", listado);
        model.addAttribute("page", pageRender);
        return "peliculas/listPelicula";
    }

    @GetMapping("/idpelicula/{id}")
    public String buscarCursoPorId(Model model, @PathVariable("id") Integer id) {
        Pelicula pelicula = peliculasService.buscarCursoPorId(id);
        model.addAttribute("peliculas", pelicula);
        return "peliculas/formPelicula";
    }

    @GetMapping("/nombre")
    public String buscarCursosPorNombre(Model model, @RequestParam(name="page", defaultValue="0") int page, @RequestParam("nombre") String nombre) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Curso> listado;
        if (nombre.equals("")) {
            listado = cursosService.buscarTodos(pageable);
        } else {
            listado = cursosService.buscarCursosPorNombre(nombre, pageable);
        }
        PageRender<Curso> pageRender = new PageRender<Curso>("/listado", listado);
        model.addAttribute("titulo", "Listado de cursos por nombre");
        model.addAttribute("listadoCursos", listado);
        model.addAttribute("page", pageRender);
        return "cursos/listCurso";
    }

    @GetMapping("/categoria")
    public String buscarCursosPorCategoria(Model model, @RequestParam(name="page", defaultValue="0") int page, @RequestParam("categoria") String categoria) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Curso> listado = cursosService.buscarCursosPorCategoria(categoria, pageable);
        PageRender<Curso> pageRender = new PageRender<Curso>("/listado", listado);
        model.addAttribute("titulo", "Listado de cursos por categoria");
        model.addAttribute("listadoCursos", listado);
        model.addAttribute("page", pageRender);
        return "cursos/listCurso";
    }

    @GetMapping("/profesor")
    public String buscarCursosPorProfesor(Model model, @RequestParam(name="page", defaultValue="0") int page, @RequestParam("profesor") String profesor) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Curso> listado;
        if (profesor.equals("")) {
            listado = cursosService.buscarTodos(pageable);
        } else {
            listado = cursosService.buscarCursosPorProfesor(profesor, pageable);
        }
        PageRender<Curso> pageRender = new PageRender<Curso>("/listado", listado);
        model.addAttribute("titulo", "Listado de cursos por profesor");
        model.addAttribute("listadoCursos", listado);
        model.addAttribute("page", pageRender);
        return "cursos/listCurso";
    }

    @PostMapping("/guardar/")
    public String guardarCurso(Model model, Curso curso, RedirectAttributes attributes) {
//    	if(curso != null) {	System.out.println(curso.getNombre()); }
        cursosService.guardarCurso(curso);
        model.addAttribute("titulo", "Nuevo curso");
        attributes.addFlashAttribute("msg", "Los datos del curso fueron guardados!");
        return "redirect:/ccursos/listado";
    }

    @GetMapping("/editar/{id}")
    public String editarCurso(Model model, @PathVariable("id") Integer id) {
        Curso curso = cursosService.buscarCursoPorId(id);
        model.addAttribute("titulo", "Editar curso");
        model.addAttribute("curso", curso);
        return "cursos/formCurso";
    }

    @GetMapping("/borrar/{id}")
    public String eliminarCurso(Model model, @PathVariable("id") Integer id, RedirectAttributes attributes) {
        cursosService.eliminarCurso(id);
        attributes.addFlashAttribute("msg", "Los datos del curso fueron borrados!");
        return "redirect:/ccursos/listado";
    }
}
