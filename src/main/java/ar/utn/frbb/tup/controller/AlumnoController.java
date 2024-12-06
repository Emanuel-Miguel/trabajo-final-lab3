package ar.utn.frbb.tup.controller;

import ar.utn.frbb.tup.business.AlumnoService;
import ar.utn.frbb.tup.business.exception.DatoInvalidoException;
import ar.utn.frbb.tup.business.exception.DatoNumericoInvalidoException;
import ar.utn.frbb.tup.business.exception.NombreInvalidoException;
import ar.utn.frbb.tup.model.Alumno;
import ar.utn.frbb.tup.model.Asignatura;
import ar.utn.frbb.tup.model.dto.AlumnoDTO;
import ar.utn.frbb.tup.model.dto.AsignaturaDTO;
import ar.utn.frbb.tup.persistence.exception.DuplicadoException;
import ar.utn.frbb.tup.persistence.exception.NoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("alumno")
public class AlumnoController {
    @Autowired
    AlumnoService alumnoService;

    @PostMapping("/")
    public Alumno crearAlumno(@RequestBody AlumnoDTO alumnoDTO) throws NombreInvalidoException, DatoNumericoInvalidoException, DuplicadoException {
        return alumnoService.crearAlumno(alumnoDTO);
    }

    @DeleteMapping("/{idAlumno}")
    public String eliminarAlumno(@PathVariable Integer idAlumno) throws NoEncontradoException {
        return alumnoService.eliminarAlumno(idAlumno);
    }

    @PutMapping("/{idAlumno}")
    public Alumno actualizarAlumno(@PathVariable Integer idAlumno, @RequestBody AlumnoDTO alumnoDTO) throws NoEncontradoException, NombreInvalidoException, DatoNumericoInvalidoException {
        return alumnoService.actualizarAlumno(idAlumno, alumnoDTO);
    }

    @PutMapping("/{idAlumno}/asignatura/{idAsignatura}")
    public Asignatura actualizarEstadoAsignatura(@PathVariable Integer idAlumno, @PathVariable Integer idAsignatura, @RequestBody AsignaturaDTO asignaturaDTO) throws NoEncontradoException, DatoInvalidoException {
        return alumnoService.actualizarEstadoAsignatura(idAlumno, idAsignatura, asignaturaDTO);
    }
}
