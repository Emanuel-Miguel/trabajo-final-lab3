package ar.utn.frbb.tup.persistence.Implementation;

import ar.utn.frbb.tup.model.Alumno;
import ar.utn.frbb.tup.persistence.AlumnoDao;
import ar.utn.frbb.tup.persistence.exception.NoEncontradoException;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class AlumnoDaoMemoryImpl implements AlumnoDao {
    private Map<Integer, Alumno> repositorioAlumnos = new HashMap<>();
    @Override
    public Alumno saveAlumno(Alumno alumno) {
        repositorioAlumnos.put(alumno.getIdAlumno(), alumno);
        return alumno;
    }

    @Override
    public String deleteAlumno(Integer idAlumno) throws NoEncontradoException {
            if (!repositorioAlumnos.containsKey(idAlumno)) {
                throw new NoEncontradoException("El id del alumno no existe en el repositorio");
            }
            repositorioAlumnos.remove(idAlumno);
            return "Eliminado con Exito";
    }

    @Override
    public Alumno getAlumno(Integer idAlumno) {
        return repositorioAlumnos.get(idAlumno);
    }

    @Override
    public boolean existeAlumnoPorId(Integer idAlumno) {
        return repositorioAlumnos.containsKey(idAlumno);
    }
}
