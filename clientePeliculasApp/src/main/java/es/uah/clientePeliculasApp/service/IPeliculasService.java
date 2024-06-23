package es.uah.clientePeliculasApp.service;

import es.uah.clientePeliculasApp.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IPeliculasService {
    Page<Pelicula> buscarTodos(Pageable pageable);
    Pelicula buscarPeliculaPorId(Integer idPelicula);
    Page<Pelicula> buscarPeliculasPorTitulo(String titulo, Pageable pageable);
    Page<Pelicula> buscarPeliculasPorGenero(String genero, Pageable pageable);
    Page<Pelicula> buscarPeliculasPorDireccion(String direccion, Pageable pageable);
    void guardarPelicula(Pelicula pelicula);
    void eliminarPelicula(Integer idPelicula);
    void actualizarPelicula(Pelicula pelicula);
}
