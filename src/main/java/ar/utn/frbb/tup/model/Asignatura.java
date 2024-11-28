package ar.utn.frbb.tup.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Asignatura {

    private Materia materia;
    private Integer idAsignatura;
    private EstadoAsignatura estado;
    private Integer nota;

    public Asignatura(Materia materia, Integer idAsignatura, EstadoAsignatura estado, Integer nota) {
        this.materia = materia;
        this.idAsignatura = idAsignatura;
        this.estado = estado;
        this.nota = nota;
    }
    public Asignatura() {
    }
}
