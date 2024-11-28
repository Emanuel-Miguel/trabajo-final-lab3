package ar.utn.frbb.tup.business;

import ar.utn.frbb.tup.business.exception.DatoNumericoInvalidoException;
import ar.utn.frbb.tup.business.exception.NombreInvalidoException;
import ar.utn.frbb.tup.model.Materia;
import ar.utn.frbb.tup.model.dto.MateriaDTO;
import ar.utn.frbb.tup.persistence.exception.DuplicadoException;
import ar.utn.frbb.tup.persistence.exception.NoEncontradoException;

import java.util.List;

public interface MateriaService {
    Materia crearMateria(MateriaDTO materiaDTO) throws NombreInvalidoException, DatoNumericoInvalidoException, DuplicadoException, NoEncontradoException;

    String eliminarMateria(Integer idMateria) throws NoEncontradoException;

    Materia actualizarMateria(Integer idMateria, MateriaDTO materiaDTO) throws NombreInvalidoException, DatoNumericoInvalidoException, NoEncontradoException, DuplicadoException;

    List<Materia> obtenerMateriasPorNombre(String nombre) throws NoEncontradoException;

    List<Materia> listarMateriasOrdenadas(String order);
}
