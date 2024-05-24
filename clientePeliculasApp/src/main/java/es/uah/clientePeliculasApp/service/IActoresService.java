package es.uah.clientePeliculasApp.service;

import es.uah.clientePeliculasApp.model.Actor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IActoresService {

    Page<Actor> buscarTodos(Pageable pageable);
    Actor buscarActorPorId(Integer idActor);
    Actor buscarActorPorNombre(String nombre);
    void guardarActor(Actor actor);
    void eliminarActor(Integer idActor);
    void actualizarActor(Actor actor);
    //void anyadirActorAPelicula(Integer idActor, Integer idPelicula);
}
