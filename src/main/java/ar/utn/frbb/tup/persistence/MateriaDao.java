package ar.utn.frbb.tup.persistence;

import ar.utn.frbb.tup.model.Materia;
import ar.utn.frbb.tup.persistence.exception.NoEncontradoException;

import java.util.List;

public interface MateriaDao {
    Materia saveMateria(Materia materia);

    String deleteMateria(Integer idMateria) throws NoEncontradoException;

    Materia getMateria(Integer idMateria);

    List<Materia> findMateriasByName(String nombre);

    List<Materia> findAllOrdered(String order);

    boolean existeMateriaPorId(Integer idMateria);
}
