package ar.utn.frbb.tup.persistence;

import ar.utn.frbb.tup.model.Carrera;
import ar.utn.frbb.tup.model.Materia;
import ar.utn.frbb.tup.persistence.exception.NoEncontradoException;

public interface CarreraDao {
    Carrera saveCarrera(Carrera carrera);

    String deleteCarrera(Integer idCarrera) throws NoEncontradoException;

    Carrera getCarrera(Integer idCarrera);

    void agregarMateria(Materia materia, Integer idCarrera);

    boolean existeCarreraPorId(Integer idCarrera);
}
