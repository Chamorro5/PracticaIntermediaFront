package es.uah.clientePeliculasApp.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Actor {
    private Integer idActor;
    private String nombre;
    private LocalDate fcNacimiento;
    private String pais;
    private List<Pelicula> peliculas;

    @JsonCreator // Constructor para la deserialización usando Jackson
    public Actor(
            @JsonProperty("id") Integer idActor,
            @JsonProperty("nombre") String nombre,
            @JsonProperty("fcNacimiento") LocalDate fcNacimiento,
            @JsonProperty("pais") String pais
    ) {
        this.idActor = idActor;
        this.nombre = nombre;
        this.fcNacimiento = fcNacimiento;
        this.pais = pais;
        this.peliculas = new ArrayList<>();
    }

}
