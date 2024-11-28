package ar.utn.frbb.tup.persistence;

import ar.utn.frbb.tup.model.Alumno;
import ar.utn.frbb.tup.persistence.Implementation.AlumnoDaoMemoryImpl;
import ar.utn.frbb.tup.persistence.exception.NoEncontradoException;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

@RunWith(SpringRunner.class)
public class AlumnoDaoTest {

    @InjectMocks
    private AlumnoDaoMemoryImpl alumnoDao;
    @BeforeEach
    public void setUp() {
        alumnoDao = new AlumnoDaoMemoryImpl();
    }
    @Test
    public void testSaveAlumno() {
        Alumno alumno = new Alumno(1, "Juan", "Pérez", 12345678, new ArrayList<>());
        Alumno resultado = alumnoDao.saveAlumno(alumno);

        Assertions.assertNotNull(resultado);
        Assertions.assertEquals(1, resultado.getIdAlumno());
        Assertions.assertEquals("Juan", resultado.getNombre());
    }

    @Test
    public void testDeleteAlumnoExistente() throws NoEncontradoException {
        Alumno alumno = new Alumno(1, "Juan", "Pérez", 12345678, new ArrayList<>());
        alumnoDao.saveAlumno(alumno);

        String resultado = alumnoDao.deleteAlumno(1);

        Assertions.assertEquals("Eliminado con Exito", resultado);
        Assertions.assertNull(alumnoDao.getAlumno(1));
    }

    @Test
    public void testDeleteAlumnoNoExistente() {
        Exception exception = Assertions.assertThrows(NoEncontradoException.class, () -> alumnoDao.deleteAlumno(99)); // id inexistente
        Assertions.assertEquals("El id del alumno no existe en el repositorio", exception.getMessage());
    }
    @Test
    public void testGetAlumnoExistente() {
        Alumno alumno = new Alumno(1, "Juan", "Pérez", 12345678, new ArrayList<>());
        alumnoDao.saveAlumno(alumno);

        Alumno resultado = alumnoDao.getAlumno(1);

        Assertions.assertNotNull(resultado);
        Assertions.assertEquals("Juan", resultado.getNombre());
    }
    @Test
    public void testGetAlumnoNoExistente() {
        Alumno resultado = alumnoDao.getAlumno(99);
        Assertions.assertNull(resultado);
    }

    @Test
    public void testExisteAlumnoPorId() {
        Alumno alumno = new Alumno(1, "Juan", "Pérez", 12345678, new ArrayList<>());
        alumnoDao.saveAlumno(alumno);

        boolean resultado = alumnoDao.existeAlumnoPorId(1);

        Assertions.assertTrue(resultado);
    }

    @Test
    public void testNoExisteAlumnoPorId() {
        boolean resultado = alumnoDao.existeAlumnoPorId(99);
        Assertions.assertFalse(resultado);
    }
}
