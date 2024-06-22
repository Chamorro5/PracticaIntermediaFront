package es.uah.clientePeliculasApp.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor // Genera el constructor sin argumentos
public class Pelicula {
    private Integer idPelicula;
    private String titulo;
    private Integer anyo;
    private Integer duracion;
    private String pais;
    private String direccion;
    private String genero;
    private String sinopsis;
    private String imagen;
    private List<Actor> actores = new ArrayList<>(); // Inicializa la lista aquí

    @JsonCreator // Constructor para la deserialización usando Jackson
    public Pelicula(
            @JsonProperty("idPelicula") Integer idPelicula,
            @JsonProperty("titulo") String titulo,
            @JsonProperty("anyo") Integer anyo,
            @JsonProperty("duracion") Integer duracion,
            @JsonProperty("pais") String pais,
            @JsonProperty("direccion") String direccion,
            @JsonProperty("genero") String genero,
            @JsonProperty("sinopsis") String sinopsis,
            @JsonProperty("imagen") String imagen
    ) {
        this.idPelicula = idPelicula;
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

    public void addActor(Actor actor) {
        this.actores.add(actor);
    }

    public void removeActor(Actor actor) {
        this.actores.remove(actor);
    }
}
