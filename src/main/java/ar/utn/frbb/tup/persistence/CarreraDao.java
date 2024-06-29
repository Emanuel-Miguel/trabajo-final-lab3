package ar.utn.frbb.tup.persistence;

import ar.utn.frbb.tup.model.Carrera;
import ar.utn.frbb.tup.persistence.exception.NoEncontradoException;

public interface CarreraDao {
    Carrera saveCarrera(Carrera carrera);

    String deleteCarrera(Integer codigoCarrera) throws NoEncontradoException;

    Carrera getCarrera(Integer codigoCarrera);
}
