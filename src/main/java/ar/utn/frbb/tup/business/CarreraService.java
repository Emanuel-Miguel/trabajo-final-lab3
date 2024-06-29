package ar.utn.frbb.tup.business;

import ar.utn.frbb.tup.business.exception.*;
import ar.utn.frbb.tup.model.Carrera;
import ar.utn.frbb.tup.model.dto.CarreraDTO;
import ar.utn.frbb.tup.persistence.exception.NoEncontradoException;

public interface CarreraService {
    Carrera crearCarrera(CarreraDTO carreraDTO) throws NombreInvalidoException, CantidadCuatrimestresInvalidaException, DatoNumericoInvalidoException;

    String eliminarCarrera(Integer codigoCarrera) throws NoEncontradoException;

    Carrera actualizarCarrera(Integer codigoCarrera, CarreraDTO carreraDTO) throws NombreInvalidoException, CantidadCuatrimestresInvalidaException, NoEncontradoException, DatoNumericoInvalidoException;
}
