package es.uah.clientePeliculasApp.model;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Actor {
    private Integer id;
    private String nombre;
    private LocalDate fcNacimiento;
    private String pais;
    private List<Pelicula> peliculas;

    public Actor(Integer id, String nombre, LocalDate fcNacimiento, String pais) {
        this.id = id;
        this.nombre = nombre;
        this.fcNacimiento = fcNacimiento;
        this.pais = pais;
        this.peliculas = new ArrayList<>();
    }

}
