package ar.utn.frbb.tup.business;

import ar.utn.frbb.tup.business.exception.DatoInvalidoException;
import ar.utn.frbb.tup.business.exception.DatoNumericoInvalidoException;
import ar.utn.frbb.tup.business.exception.NombreInvalidoException;
import ar.utn.frbb.tup.model.Alumno;
import ar.utn.frbb.tup.model.Asignatura;
import ar.utn.frbb.tup.model.dto.AlumnoDTO;
import ar.utn.frbb.tup.model.dto.AsignaturaDTO;
import ar.utn.frbb.tup.persistence.exception.DuplicadoException;
import ar.utn.frbb.tup.persistence.exception.NoEncontradoException;

public interface AlumnoService {
    Alumno crearAlumno(AlumnoDTO alumnoDTO) throws NombreInvalidoException, DatoNumericoInvalidoException, DuplicadoException;

    String eliminarAlumno(Integer idAlumno) throws NoEncontradoException;

    Alumno actualizarAlumno(Integer idAlumno, AlumnoDTO alumnoDTO) throws NoEncontradoException, NombreInvalidoException, DatoNumericoInvalidoException;

    Asignatura actualizarEstadoAsignatura(Integer idAlumno, Integer idAsignatura, AsignaturaDTO asignaturaDTO) throws NoEncontradoException, DatoInvalidoException;
}
