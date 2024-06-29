package ar.utn.frbb.tup.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlumnoDTO {
    private Integer legajo;
    private String nombre;
    private String apellido;
    private Integer dni;

    public AlumnoDTO() {
    }

}
