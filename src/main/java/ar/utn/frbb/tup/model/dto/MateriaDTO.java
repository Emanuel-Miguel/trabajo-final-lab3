package ar.utn.frbb.tup.model.dto;

import ar.utn.frbb.tup.model.Profesor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MateriaDTO {
    private Integer idMateria;
    private String nombre;
    private Integer anio;
    private Integer horas;
    private Profesor profesor;
    private Integer idCarrera;

    public MateriaDTO() {
    }
}
