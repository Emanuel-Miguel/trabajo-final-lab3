package ar.utn.frbb.tup.business.Implementation;

import ar.utn.frbb.tup.business.AlumnoService;
import ar.utn.frbb.tup.business.exception.*;
import ar.utn.frbb.tup.model.*;
import ar.utn.frbb.tup.model.dto.AlumnoDTO;
import ar.utn.frbb.tup.model.dto.AsignaturaDTO;
import ar.utn.frbb.tup.persistence.AlumnoDao;
import ar.utn.frbb.tup.persistence.exception.DuplicadoException;
import ar.utn.frbb.tup.persistence.exception.NoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class AlumnoServiceImpl implements AlumnoService {
    @Autowired
    AlumnoDao alumnoDao;

    private int contadorIdAlumno = 1;
    @Override
    public Alumno crearAlumno(AlumnoDTO alumnoDTO) throws NombreInvalidoException, DatoNumericoInvalidoException, DuplicadoException {
        validarAlumnoDTO(alumnoDTO);

        if (alumnoDao.existeAlumnoPorId(alumnoDTO.getIdAlumno())) {
            throw new DuplicadoException("Ya existe un alumno con el mismo id");
        }

        Alumno alumno = new Alumno();
        alumno.setNombre(alumnoDTO.getNombre());
        alumno.setApellido(alumnoDTO.getApellido());
        alumno.setDni(alumnoDTO.getDni());
        alumno.setIdAlumno(contadorIdAlumno++);

        // Asignaturas hardcodeadas
        List<Asignatura> asignaturas = Arrays.asList(
                new Asignatura(new Materia(1, "Matemáticas", 2023, 120,
                        new Profesor("Alberto", "Sanchez", "Profesorado en Matematicas"),
                        new Carrera("Tecnicatura en Programacion", 3, 4, 8)),
                        1, EstadoAsignatura.CURSADA, null),

                new Asignatura(new Materia(2, "Base de Datos", 2023, 80,
                        new Profesor("Graciela", "Fernandez", "Licenciada en Computacion"),
                        new Carrera("Tecnicatura en Programacion", 3, 4, 8)),
                        2, EstadoAsignatura.NO_CURSADA, null),

                new Asignatura(new Materia(3, "Física", 2023, 75,
                        new Profesor("Mauricio", "Blanco", "Profesorado en Física"),
                        new Carrera("Tecnicatura en Programacion", 3, 4, 8)),
                        3, EstadoAsignatura.APROBADA, 8)
        );
        alumno.setAsignaturas(asignaturas);

        return alumnoDao.saveAlumno(alumno);
    }

    @Override
    public String eliminarAlumno(Integer idAlumno) throws NoEncontradoException {
        return alumnoDao.deleteAlumno(idAlumno);
    }

    @Override
    public Alumno actualizarAlumno(Integer idAlumno, AlumnoDTO alumnoDTO) throws NoEncontradoException, NombreInvalidoException, DatoNumericoInvalidoException {
        validarAlumnoDTO(alumnoDTO);
        Alumno alumno = alumnoDao.getAlumno(idAlumno);
        if (alumno == null) {
            throw new NoEncontradoException("El alumno con id " + idAlumno + " no existe");
        }

        alumno.setNombre(alumnoDTO.getNombre());
        alumno.setApellido(alumnoDTO.getApellido());
        alumno.setDni(alumnoDTO.getDni());
        return alumnoDao.saveAlumno(alumno);
    }

    @Override
    public Asignatura actualizarEstadoAsignatura(Integer idAlumno, Integer idAsignatura, AsignaturaDTO asignaturaDTO) throws NoEncontradoException, DatoInvalidoException {
        Alumno alumno = alumnoDao.getAlumno(idAlumno);
        if (alumno == null) {
            throw new NoEncontradoException("El alumno con id " + idAlumno + " no existe");
        }

        Asignatura asignatura = null;
        for (Asignatura a : alumno.getAsignaturas()) {
            if (a.getIdAsignatura().equals(idAsignatura)) {
                asignatura = a;
                break;
            }
        }

        if (asignatura == null) {
            throw new NoEncontradoException("La asignatura con ID " + idAsignatura + " no existe para el alumno con id " + idAlumno);
        }

        EstadoAsignatura nuevoEstado = asignaturaDTO.getEstado();
        Integer nota = asignaturaDTO.getNota();

        switch (nuevoEstado) {
            case CURSADA:
                if (nota != null) {
                    throw new DatoInvalidoException("Si una asignatura está CURSADA no debe tener nota.");
                }
                asignatura.setEstado(nuevoEstado);
                asignatura.setNota(null);
                break;

            case NO_CURSADA:
                if (nota != null) {
                    throw new DatoInvalidoException("Si una asignatura está NO_CURSADA no debe tener nota.");
                }
                asignatura.setEstado(nuevoEstado);
                asignatura.setNota(null);
                break;

            case APROBADA:
                if (asignatura.getEstado() == EstadoAsignatura.APROBADA) {
                    if (asignatura.getNota() != null && asignatura.getNota() >= 4) {

                        throw new DatoInvalidoException("La asignatura ya está APROBADA. No es necesario realizar cambios en el estado.");
                    }
                }else if (asignatura.getEstado() == EstadoAsignatura.CURSADA) {
                    if (nota == null || nota < 4) {
                        throw new DatoInvalidoException("Para aprobar una asignatura, la nota debe ser mayor o igual a 4.");
                    }
                    asignatura.setEstado(nuevoEstado);
                    asignatura.setNota(nota);
                } else {
                    throw new DatoInvalidoException("Solo se puede cambiar a APROBADA si está CURSADA.");
                }
                break;

            default:
                throw new DatoInvalidoException("Estado no válido para la asignatura.");
        }

        alumnoDao.saveAlumno(alumno);

        return asignatura;
    }

    private void validarAlumnoDTO(AlumnoDTO alumnoDTO) throws NombreInvalidoException, DatoNumericoInvalidoException {

        if (alumnoDTO.getNombre() == null || alumnoDTO.getNombre().isEmpty() || alumnoDTO.getNombre().length() <= 3 || alumnoDTO.getNombre().length() > 35) {
            throw new NombreInvalidoException("El nombre no puede estar vacio, debe tener más de 3 caracteres y no exceder los 35 caracteres");
        }

        if (!alumnoDTO.getNombre().matches("[a-zA-Z ]+")) {
            throw new NombreInvalidoException("El nombre contiene caracteres inválidos");
        }

        if (alumnoDTO.getApellido() == null || alumnoDTO.getApellido().isEmpty() || alumnoDTO.getApellido().length() <= 3 || alumnoDTO.getApellido().length() > 35) {
            throw new NombreInvalidoException("El apellido no puede estar vacio, debe tener más de 3 caracteres y no exceder los 35 caracteres");
        }

        if (!alumnoDTO.getApellido().matches("[a-zA-Z ]+")) {
            throw new NombreInvalidoException("El apellido contiene caracteres inválidos");
        }

        if (alumnoDTO.getDni() == null || alumnoDTO.getDni() <= 0 || String.valueOf(alumnoDTO.getDni()).length() > 9) {
            throw new DatoNumericoInvalidoException("El dni del alumno no puede estar vacío y debe ser un número positivo con máximo 9 dígitos");
        }
    }

}
