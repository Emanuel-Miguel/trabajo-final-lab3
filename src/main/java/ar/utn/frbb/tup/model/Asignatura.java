package ar.utn.frbb.tup.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Asignatura {

    private Materia materia;
    private EstadoAsignatura estado;
    private Integer nota;

    public Asignatura() {
    }
}
