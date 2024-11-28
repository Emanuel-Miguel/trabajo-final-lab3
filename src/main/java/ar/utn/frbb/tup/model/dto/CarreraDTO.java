package ar.utn.frbb.tup.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarreraDTO {
    private String nombre;
    private Integer idCarrera;
    private Integer departamento;
    private Integer cantidadCuatrimestres;

    public CarreraDTO() {
    }
}
