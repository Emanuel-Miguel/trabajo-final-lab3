package ar.utn.frbb.tup.controller;

import ar.utn.frbb.tup.business.AlumnoService;
import ar.utn.frbb.tup.business.exception.DatoNumericoInvalidoException;
import ar.utn.frbb.tup.business.exception.NombreInvalidoException;
import ar.utn.frbb.tup.model.Alumno;
import ar.utn.frbb.tup.model.dto.AlumnoDTO;
import ar.utn.frbb.tup.persistence.exception.DuplicadoException;
import ar.utn.frbb.tup.persistence.exception.NoEncontradoException;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class AlumnoControllerTest {
    @Mock
    private AlumnoService alumnoService;

    @InjectMocks
    private AlumnoController alumnoController;

    @Test
    public void testCrearAlumno() throws NombreInvalidoException, DatoNumericoInvalidoException, DuplicadoException {
        AlumnoDTO alumnoDTO = new AlumnoDTO();
        alumnoDTO.setNombre("Juan");
        alumnoDTO.setApellido("Perez");
        alumnoDTO.setDni(12345678);

        Alumno alumnoMock = new Alumno(1, "Juan", "Perez", 12345678, null);

        Mockito.when(alumnoService.crearAlumno(alumnoDTO)).thenReturn(alumnoMock);

        Alumno resultado = alumnoController.crearAlumno(alumnoDTO);

        Assertions.assertEquals(alumnoMock, resultado);
    }

    @Test
    public void testEliminarAlumno() throws NoEncontradoException {
        Integer idAlumno = 1;
        String mensajeEsperado = "Eliminado con Exito";

        Mockito.when(alumnoService.eliminarAlumno(idAlumno)).thenReturn(mensajeEsperado);

        String resultado = alumnoController.eliminarAlumno(idAlumno);

        Assertions.assertEquals(mensajeEsperado, resultado);
    }

    @Test
    public void testActualizarAlumno() throws NoEncontradoException, NombreInvalidoException, DatoNumericoInvalidoException {
        Integer idAlumno = 1;
        AlumnoDTO alumnoDTO = new AlumnoDTO();
        alumnoDTO.setNombre("Carlos");
        alumnoDTO.setApellido("Gomez");
        alumnoDTO.setDni(87654321);

        Alumno alumnoActualizado = new Alumno(idAlumno, "Carlos", "Gomez", 87654321, null);

        Mockito.when(alumnoService.actualizarAlumno(idAlumno, alumnoDTO)).thenReturn(alumnoActualizado);

        Alumno resultado = alumnoController.actualizarAlumno(idAlumno, alumnoDTO);

        Assertions.assertEquals(alumnoActualizado, resultado);
    }

}
