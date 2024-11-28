package ar.utn.frbb.tup.model.dto;

import ar.utn.frbb.tup.model.EstadoAsignatura;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AsignaturaDTO {

    private EstadoAsignatura estado;
    private Integer nota;

}


