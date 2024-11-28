package ar.utn.frbb.tup.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Profesor {
    private String nombre;
    private String apellido;
    private String titulo;

    public Profesor() {
    }

    public Profesor(String nombre, String apellido, String titulo) {
        this.apellido = apellido;
        this.nombre = nombre;
        this.titulo = titulo;
    }

    @Override
    public String toString() {
        return "Profesor{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", titulo='" + titulo + '\'' +
                '}';
    }
}
