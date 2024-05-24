package es.uah.clientePeliculasApp.model;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Pelicula {
    private Integer id;
    private String titulo;
    private Integer anyo;
    private Integer duracion;
    private String pais;
    private String direccion;
    private String genero;
    private String sinopsis;
    private String imagen;
    private List<Actor> actores;

    public Pelicula(Integer id, String titulo, Integer anyo, Integer duracion,
                    String pais, String direccion, String genero, String sinopsis, String imagen ) {
        this.id = id;
        this.titulo = titulo;
        this.anyo = anyo;
        this.duracion = duracion;
        this.pais = pais;
        this.direccion = direccion;
        this.genero = genero;
        this.sinopsis = sinopsis;
        this.imagen = imagen;
        this.actores = new ArrayList<>();
    }

    public Pelicula () {
    }
}
