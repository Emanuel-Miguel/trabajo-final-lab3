package ar.utn.frbb.tup.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class Materia {
    private Integer idMateria;
    private String nombre;
    private Integer anio;
    private Integer horas;
    private Profesor profesor;
    private Carrera carrera;

    public Materia() {
    }

    public Materia(String nombre) {
        this.nombre = nombre;
    }


    public Materia(Integer idMateria, String nombre, Integer anio, Integer horas, Profesor profesor, Carrera carrera) {
        this.idMateria = idMateria;
        this.nombre = nombre;
        this.anio = anio;
        this.horas = horas;
        this.profesor = profesor;
        this.carrera = carrera;
    }

    @Override
    public String toString() {
        return "Materia{" +
                "idMateria=" + idMateria +
                ", nombre='" + nombre + '\'' +
                ", anio=" + anio +
                ", horas=" + horas +
                ", profesor=" + profesor +
                ", carrera=" + carrera.getNombre() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Materia materia = (Materia) o;
        return Objects.equals(idMateria, materia.idMateria) && Objects.equals(nombre, materia.nombre) && Objects.equals(anio, materia.anio) && Objects.equals(horas, materia.horas) && Objects.equals(profesor, materia.profesor) && Objects.equals(carrera, materia.carrera);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idMateria, nombre, anio, horas, profesor, carrera);
    }
}