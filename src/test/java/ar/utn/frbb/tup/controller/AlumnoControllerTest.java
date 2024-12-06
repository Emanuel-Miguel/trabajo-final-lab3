package ar.utn.frbb.tup.controller;

import ar.utn.frbb.tup.business.AlumnoService;
import ar.utn.frbb.tup.business.exception.DatoInvalidoException;
import ar.utn.frbb.tup.business.exception.DatoNumericoInvalidoException;
import ar.utn.frbb.tup.business.exception.NombreInvalidoException;
import ar.utn.frbb.tup.model.*;
import ar.utn.frbb.tup.model.dto.AlumnoDTO;
import ar.utn.frbb.tup.model.dto.AsignaturaDTO;
import ar.utn.frbb.tup.persistence.exception.DuplicadoException;
import ar.utn.frbb.tup.persistence.exception.NoEncontradoException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(AlumnoController.class)
@SuppressWarnings("unused")
public class AlumnoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AlumnoService alumnoService;

    private static ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testCrearAlumno() throws Exception {
        AlumnoDTO alumnoDTO = new AlumnoDTO();
        alumnoDTO.setNombre("Juan");
        alumnoDTO.setApellido("Perez");
        alumnoDTO.setDni(12345678);

        Alumno alumnoMock = new Alumno(1, "Juan", "Perez", 12345678, null);

        Mockito.when(alumnoService.crearAlumno(Mockito.any(AlumnoDTO.class))).thenReturn(alumnoMock);

        mockMvc.perform(post("/alumno/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(alumnoDTO)))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.idAlumno").value(1))
                        .andExpect(jsonPath("$.nombre").value("Juan"))
                        .andExpect(jsonPath("$.apellido").value("Perez"));
    }

    @Test
    public void testCrearAlumno_NombreInvalidoException() throws Exception {
        AlumnoDTO alumnoDTO = new AlumnoDTO();
        alumnoDTO.setNombre("X"); // Nombre inválido
        alumnoDTO.setApellido("Perez");
        alumnoDTO.setDni(12345678);

        Mockito.when(alumnoService.crearAlumno(Mockito.any(AlumnoDTO.class)))
                .thenThrow(new NombreInvalidoException("El nombre es inválido"));

        mockMvc.perform(post("/alumno/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(alumnoDTO)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.errorMessage").value("El nombre es inválido"));
    }

    @Test
    public void testCrearAlumno_DuplicadoException() throws Exception {
        AlumnoDTO alumnoDTO = new AlumnoDTO();
        alumnoDTO.setNombre("Juan");
        alumnoDTO.setApellido("Perez");
        alumnoDTO.setDni(12345678);

        Mockito.when(alumnoService.crearAlumno(Mockito.any(AlumnoDTO.class)))
                .thenThrow(new DuplicadoException("Ya existe un alumno con el mismo id"));

        mockMvc.perform(post("/alumno/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(alumnoDTO)))
                        .andExpect(status().isConflict()) // Verifica que la respuesta sea 409 CONFLICT
                        .andExpect(jsonPath("$.errorMessage").value("Ya existe un alumno con el mismo id"));
    }

    @Test
    public void testCrearAlumno_DatoNumericoInvalidoException() throws Exception {
        AlumnoDTO alumnoDTO = new AlumnoDTO();
        alumnoDTO.setNombre("Juan");
        alumnoDTO.setApellido("Pérez");
        alumnoDTO.setDni(-12345678); // DNI negativo para provocar la excepción

        Mockito.when(alumnoService.crearAlumno(Mockito.any(AlumnoDTO.class)))
                .thenThrow(new DatoNumericoInvalidoException("El dni del alumno no puede estar vacío y debe ser un número positivo con máximo 9 dígitos"));

        mockMvc.perform(post("/alumno/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(alumnoDTO)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.errorMessage").value("El dni del alumno no puede estar vacío y debe ser un número positivo con máximo 9 dígitos"));
    }

    @Test
    public void testEliminarAlumno() throws Exception {
        Integer idAlumno = 1;

        Mockito.when(alumnoService.eliminarAlumno(idAlumno)).thenReturn("Eliminado con Exito");

        mockMvc.perform(delete("/alumno/{idAlumno}", idAlumno))
                        .andExpect(status().isOk())
                        .andExpect(content().string("Eliminado con Exito"));
    }

    @Test
    public void testEliminarAlumno_NoEncontradoException() throws Exception {
        Integer idAlumno = 999; // ID no existente

        Mockito.when(alumnoService.eliminarAlumno(idAlumno))
                .thenThrow(new NoEncontradoException("El alumno no existe"));

        mockMvc.perform(delete("/alumno/{idAlumno}", idAlumno))
                        .andExpect(status().isNotFound())
                        .andExpect(jsonPath("$.errorMessage").value("El alumno no existe"));
    }

    @Test
    public void testActualizarAlumno() throws Exception {
        Integer idAlumno = 1;

        AlumnoDTO alumnoDTO = new AlumnoDTO();
        alumnoDTO.setNombre("Carlos");
        alumnoDTO.setApellido("Gomez");
        alumnoDTO.setDni(98765432);

        Alumno alumnoMock = new Alumno(1, "Carlos", "Gomez", 98765432, null);

        Mockito.when(alumnoService.actualizarAlumno(Mockito.eq(idAlumno), Mockito.any(AlumnoDTO.class)))
                .thenReturn(alumnoMock);

        mockMvc.perform(put("/alumno/{idAlumno}", idAlumno)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(alumnoDTO)))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.idAlumno").value(1))
                        .andExpect(jsonPath("$.nombre").value("Carlos"))
                        .andExpect(jsonPath("$.apellido").value("Gomez"));
    }

    @Test
    public void testActualizarAlumno_NombreInvalidoException() throws Exception {
        Integer idAlumno = 1;
        AlumnoDTO alumnoDTO = new AlumnoDTO();
        alumnoDTO.setNombre("X"); // Nombre inválido
        alumnoDTO.setApellido("Gomez");
        alumnoDTO.setDni(87654321);

        Mockito.when(alumnoService.actualizarAlumno(Mockito.eq(idAlumno), Mockito.any(AlumnoDTO.class)))
                .thenThrow(new NombreInvalidoException("El nombre es inválido"));

        mockMvc.perform(put("/alumno/{idAlumno}", idAlumno)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(alumnoDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value("El nombre es inválido"));
    }

    @Test
    public void testActualizarAlumno_NoEncontradoException() throws Exception {
        Integer idAlumno = 999; // ID no existente
        AlumnoDTO alumnoDTO = new AlumnoDTO();
        alumnoDTO.setNombre("Carlos");
        alumnoDTO.setApellido("Gomez");
        alumnoDTO.setDni(87654321);

        Mockito.when(alumnoService.actualizarAlumno(Mockito.eq(idAlumno), Mockito.any(AlumnoDTO.class)))
                .thenThrow(new NoEncontradoException("El alumno no existe"));

        mockMvc.perform(put("/alumno/{idAlumno}", idAlumno)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(alumnoDTO)))
                        .andExpect(status().isNotFound())
                        .andExpect(jsonPath("$.errorMessage").value("El alumno no existe"));
    }

    @Test
    public void testActualizarEstadoAsignatura_Exitoso() throws Exception {
        Integer idAlumno = 1;
        Integer idAsignatura = 2;

        AsignaturaDTO asignaturaDTO = new AsignaturaDTO();
        asignaturaDTO.setEstado(EstadoAsignatura.CURSADA);

        Asignatura asignaturaActualizada = new Asignatura(
                new Materia(2, "Base de Datos", 2023, 80,
                        new Profesor("Graciela", "Fernandez", "Licenciada en Computacion"),
                        new Carrera("Tecnicatura en Programacion", 3, 4, 8)),
                idAsignatura, EstadoAsignatura.CURSADA, null);

        Mockito.when(alumnoService.actualizarEstadoAsignatura(Mockito.eq(idAlumno), Mockito.eq(idAsignatura), Mockito.any(AsignaturaDTO.class)))
                .thenReturn(asignaturaActualizada);

        mockMvc.perform(put("/alumno/{idAlumno}/asignatura/{idAsignatura}", idAlumno, idAsignatura)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(asignaturaDTO)))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.idAsignatura").value(idAsignatura))
                        .andExpect(jsonPath("$.estado").value("CURSADA"));
    }

    @Test
    public void testActualizarEstadoAsignatura_NoEncontradoAlumno() throws Exception {
        Integer idAlumno = 999; // Alumno no existente
        Integer idAsignatura = 1;

        AsignaturaDTO asignaturaDTO = new AsignaturaDTO();
        asignaturaDTO.setEstado(EstadoAsignatura.CURSADA);

        Mockito.when(alumnoService.actualizarEstadoAsignatura(Mockito.eq(idAlumno), Mockito.eq(idAsignatura), Mockito.any(AsignaturaDTO.class)))
                .thenThrow(new NoEncontradoException("El alumno con id " + idAlumno + " no existe"));

        mockMvc.perform(put("/alumno/{idAlumno}/asignatura/{idAsignatura}", idAlumno, idAsignatura)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(asignaturaDTO)))
                        .andExpect(status().isNotFound())
                        .andExpect(jsonPath("$.errorMessage").value("El alumno con id " + idAlumno + " no existe"));
    }

    @Test
    public void testActualizarEstadoAsignatura_NoEncontradoAsignatura() throws Exception {
        Integer idAlumno = 1;
        Integer idAsignatura = 999; // Asignatura no existente

        AsignaturaDTO asignaturaDTO = new AsignaturaDTO();
        asignaturaDTO.setEstado(EstadoAsignatura.CURSADA);

        Mockito.when(alumnoService.actualizarEstadoAsignatura(Mockito.eq(idAlumno), Mockito.eq(idAsignatura), Mockito.any(AsignaturaDTO.class)))
                .thenThrow(new NoEncontradoException("La asignatura con ID " + idAsignatura + " no existe para el alumno con id " + idAlumno));

        mockMvc.perform(put("/alumno/{idAlumno}/asignatura/{idAsignatura}", idAlumno, idAsignatura)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(asignaturaDTO)))
                        .andExpect(status().isNotFound())
                        .andExpect(jsonPath("$.errorMessage").value("La asignatura con ID " + idAsignatura + " no existe para el alumno con id " + idAlumno));
    }

    @Test
    public void testActualizarEstadoAsignatura_DatoInvalido() throws Exception {
        Integer idAlumno = 1;
        Integer idAsignatura = 2;

        AsignaturaDTO asignaturaDTO = new AsignaturaDTO();
        asignaturaDTO.setEstado(EstadoAsignatura.APROBADA);
        asignaturaDTO.setNota(3); // Nota no válida para aprobar

        Mockito.when(alumnoService.actualizarEstadoAsignatura(Mockito.eq(idAlumno), Mockito.eq(idAsignatura), Mockito.any(AsignaturaDTO.class)))
                .thenThrow(new DatoInvalidoException("Para aprobar una asignatura, la nota debe ser mayor o igual a 4"));

        mockMvc.perform(put("/alumno/{idAlumno}/asignatura/{idAsignatura}", idAlumno, idAsignatura)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(asignaturaDTO)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.errorMessage").value("Para aprobar una asignatura, la nota debe ser mayor o igual a 4"));
    }

}
