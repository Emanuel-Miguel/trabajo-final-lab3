package ar.utn.frbb.tup.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Carrera {
    private String nombre;
    private Integer idCarrera;
    private Integer departamento;
    private Integer cantidadCuatrimestres;

    @JsonIgnore
    private List<Materia> materiasList = new ArrayList<>();;

    public Carrera() {
    }

    public Carrera(String nombre, Integer idCarrera, Integer departamento, Integer cantidadCuatrimestres) {
        this.nombre = nombre;
        this.idCarrera = idCarrera;
        this.departamento = departamento;
        this.cantidadCuatrimestres = cantidadCuatrimestres;
        this.materiasList = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Carrera{" +
                "nombre='" + nombre + '\'' +
                ", idCarrera=" + idCarrera +
                ", departamento=" + departamento +
                ", cantidadCuatrimestres=" + cantidadCuatrimestres +
                ", materiasList=" + materiasList +
                '}';
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
