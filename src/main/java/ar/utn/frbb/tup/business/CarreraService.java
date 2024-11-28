package ar.utn.frbb.tup.business;

import ar.utn.frbb.tup.business.exception.*;
import ar.utn.frbb.tup.model.Carrera;
import ar.utn.frbb.tup.model.Materia;
import ar.utn.frbb.tup.model.dto.CarreraDTO;
import ar.utn.frbb.tup.persistence.exception.DuplicadoException;
import ar.utn.frbb.tup.persistence.exception.NoEncontradoException;

public interface CarreraService {
    Carrera crearCarrera(CarreraDTO carreraDTO) throws NombreInvalidoException, CantidadCuatrimestresInvalidaException, DatoNumericoInvalidoException, DuplicadoException;

    String eliminarCarrera(Integer idCarrera) throws NoEncontradoException;

    Carrera actualizarCarrera(Integer idCarrera, CarreraDTO carreraDTO) throws NombreInvalidoException, CantidadCuatrimestresInvalidaException, NoEncontradoException, DatoNumericoInvalidoException, DuplicadoException;

    Carrera getCarrera(Integer idCarrera);

    void agregarMateria(Materia materia, Integer idCarrera);
}
