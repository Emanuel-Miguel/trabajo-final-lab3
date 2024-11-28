package ar.utn.frbb.tup.persistence;

import ar.utn.frbb.tup.model.Alumno;
import ar.utn.frbb.tup.persistence.exception.NoEncontradoException;

public interface AlumnoDao {

    Alumno saveAlumno(Alumno alumno);

    String deleteAlumno(Integer idAlumno) throws NoEncontradoException;

    Alumno getAlumno(Integer idAlumno);

    boolean existeAlumnoPorId(Integer idAlumno);
}
