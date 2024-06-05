package ar.utn.frbb.tup.model;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Carrera {
    private String nombre;
    private Integer codigoCarrera;
    private Integer departamento;
    private Integer cantidadCuatrimestres;
    private List<Materia> materiasList;

    public Carrera() {
    }

    @Override
    public String toString() {
        return "Carrera{" +
                "nombre='" + nombre + '\'' +
                ", codigoCarrera=" + codigoCarrera +
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
