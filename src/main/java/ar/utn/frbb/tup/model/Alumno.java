package ar.utn.frbb.tup.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Alumno {
    private Integer id;
    private String nombre;
    private String apellido;
    private Integer dni;

    private List<Asignatura> asignaturas;
    public Alumno () {
    }

    public Alumno(Integer id, String nombre, String apellido, Integer dni) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
    }
}
