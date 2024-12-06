package ar.utn.frbb.tup.business;

import ar.utn.frbb.tup.business.Implementation.AlumnoServiceImpl;
import ar.utn.frbb.tup.business.exception.DatoInvalidoException;
import ar.utn.frbb.tup.model.*;
import ar.utn.frbb.tup.model.dto.AlumnoDTO;
import ar.utn.frbb.tup.model.dto.AsignaturaDTO;
import ar.utn.frbb.tup.persistence.AlumnoDao;
import ar.utn.frbb.tup.persistence.exception.DuplicadoException;
import ar.utn.frbb.tup.persistence.exception.NoEncontradoException;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;

@RunWith(SpringRunner.class)
public class AlumnoServiceTest {
    @InjectMocks
    private AlumnoServiceImpl alumnoService;

    @Mock
    private AlumnoDao alumnoDao;

    @Test
    public void testCrearAlumnoExito() throws Exception {
        AlumnoDTO alumnoDTO = new AlumnoDTO();
        alumnoDTO.setNombre("Juan");
        alumnoDTO.setApellido("Perez");
        alumnoDTO.setDni(12345678);

        Alumno alumno = new Alumno( 1, "Juan", "Perez", 12345678, new ArrayList<>());

        Mockito.when(alumnoDao.existeAlumnoPorId(1)).thenReturn(false);
        Mockito.when(alumnoDao.saveAlumno(Mockito.any(Alumno.class))).thenReturn(alumno);

        Alumno resultado = alumnoService.crearAlumno(alumnoDTO);

        Assertions.assertNotNull(resultado);
        Assertions.assertEquals("Juan", resultado.getNombre());
        Assertions.assertEquals("Perez", resultado.getApellido());
        Assertions.assertEquals(12345678, resultado.getDni());
    }
    @Test
    public void testCrearAlumnoDuplicado() {
        AlumnoDTO alumnoDTO = new AlumnoDTO();
        alumnoDTO.setIdAlumno(1);
        alumnoDTO.setNombre("Juan");
        alumnoDTO.setApellido("Perez");
        alumnoDTO.setDni(12345678);

        Mockito.when(alumnoDao.existeAlumnoPorId(1)).thenReturn(true);

        Assertions.assertThrows(DuplicadoException.class, () -> alumnoService.crearAlumno(alumnoDTO));
    }

    @Test
    public void testEliminarAlumnoExito() throws Exception {
        Integer idAlumno = 1;

        Mockito.when(alumnoDao.deleteAlumno(idAlumno)).thenReturn("Eliminado con Exito");

        String resultado = alumnoService.eliminarAlumno(idAlumno);

        Assertions.assertEquals("Eliminado con Exito", resultado);
    }

    @Test
    public void testEliminarAlumnoNoEncontrado() throws Exception {
        Integer idAlumno = 1;

        Mockito.when(alumnoDao.deleteAlumno(idAlumno)).thenThrow(new NoEncontradoException("Alumno no encontrado"));

        Assertions.assertThrows(NoEncontradoException.class, () -> {alumnoService.eliminarAlumno(idAlumno); });
    }

    @Test
    public void testActualizarAlumnoExito() throws Exception {
        AlumnoDTO alumnoDTO = new AlumnoDTO();
        alumnoDTO.setNombre("Carlos");
        alumnoDTO.setApellido("Lopez");
        alumnoDTO.setDni(87654321);

        Alumno alumnoExistente = new Alumno(1, "Juan", "Perez", 12345678, new ArrayList<>());
        Alumno alumnoActualizado = new Alumno(2, "Carlos", "Lopez", 87654321, new ArrayList<>());

        Mockito.when(alumnoDao.getAlumno(1)).thenReturn(alumnoExistente);
        Mockito.when(alumnoDao.saveAlumno(Mockito.any(Alumno.class))).thenReturn(alumnoActualizado);

        Alumno resultado = alumnoService.actualizarAlumno(1, alumnoDTO);

        Assertions.assertNotNull(resultado);
        Assertions.assertEquals("Carlos", resultado.getNombre());
        Assertions.assertEquals("Lopez", resultado.getApellido());
    }

    @Test
    public void testActualizarEstadoAsignaturaExito() throws NoEncontradoException, DatoInvalidoException {
        Integer idAlumno = 1;
        Integer idAsignatura = 101;

        Alumno alumnoMock = new Alumno();
        alumnoMock.setIdAlumno(idAlumno);
        alumnoMock.setAsignaturas(Arrays.asList(
                new Asignatura(new Materia(1, "Matemáticas", 2023, 120, null, null),
                        idAsignatura, EstadoAsignatura.CURSADA, null)
        ));

        AsignaturaDTO asignaturaDTO = new AsignaturaDTO();
        asignaturaDTO.setEstado(EstadoAsignatura.APROBADA);
        asignaturaDTO.setNota(5);

        Mockito.when(alumnoDao.getAlumno(idAlumno)).thenReturn(alumnoMock);

        Asignatura asignaturaActualizada = alumnoService.actualizarEstadoAsignatura(idAlumno, idAsignatura, asignaturaDTO);

        Assertions.assertNotNull(asignaturaActualizada);
        Assertions.assertEquals(EstadoAsignatura.APROBADA, asignaturaActualizada.getEstado());
        Assertions.assertEquals(5, asignaturaActualizada.getNota());
    }

    @Test
    public void testActualizarEstadoAsignaturaSiAlumnoNoExiste() {
        Integer idAlumno = 1;
        Integer idAsignatura = 101;
        AsignaturaDTO asignaturaDTO = new AsignaturaDTO();

        // Simular que el alumno no existe
        Mockito.when(alumnoDao.getAlumno(idAlumno)).thenReturn(null);

        Assertions.assertThrows(NoEncontradoException.class, () -> {
            alumnoService.actualizarEstadoAsignatura(idAlumno, idAsignatura, asignaturaDTO);
        });
    }

    @Test
    public void testActualizarEstadoAsignaturaSiAsignaturaNoExiste() {
        Integer idAlumno = 1;
        Integer idAsignatura = 101;
        AsignaturaDTO asignaturaDTO = new AsignaturaDTO();
        asignaturaDTO.setEstado(EstadoAsignatura.CURSADA);

        // Crear un alumno sin la asignatura buscada
        Alumno alumnoMock = new Alumno();
        alumnoMock.setIdAlumno(idAlumno);
        alumnoMock.setAsignaturas(Arrays.asList());

        Mockito.when(alumnoDao.getAlumno(idAlumno)).thenReturn(alumnoMock);

        Assertions.assertThrows(NoEncontradoException.class, () -> {alumnoService.actualizarEstadoAsignatura(idAlumno, idAsignatura, asignaturaDTO); });
    }
}
