package ar.utn.frbb.tup.persistence;

import ar.utn.frbb.tup.model.Carrera;
import ar.utn.frbb.tup.model.Materia;
import ar.utn.frbb.tup.persistence.Implementation.CarreraDaoMemoryImpl;
import ar.utn.frbb.tup.persistence.exception.NoEncontradoException;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

@RunWith(SpringRunner.class)
public class CarreraDaoTest {

    @InjectMocks
    private CarreraDaoMemoryImpl carreraDao;

    @BeforeEach
    public void setUp() {
        carreraDao = new CarreraDaoMemoryImpl();
    }

    @Test
    public void testSaveCarrera() {
        Carrera carrera = new Carrera("Ingeniería", 1, 11, 8);
        Carrera resultado = carreraDao.saveCarrera(carrera);

        Assertions.assertNotNull(resultado);
        Assertions.assertEquals("Ingeniería", resultado.getNombre());
        Assertions.assertEquals(1, resultado.getIdCarrera());
    }

    @Test
    public void testDeleteCarreraExistente() throws NoEncontradoException {
        Carrera carrera = new Carrera("Ingeniería", 2, 2, 8);
        carreraDao.saveCarrera(carrera);

        String resultado = carreraDao.deleteCarrera(2);

        Assertions.assertEquals("Eliminado con Exito", resultado);
        Assertions.assertNull(carreraDao.getCarrera(2));
    }

    @Test
    public void testDeleteCarreraNoExistente() {
        Exception exception = Assertions.assertThrows(NoEncontradoException.class, () -> carreraDao.deleteCarrera(123)); // id inexistente
        Assertions.assertEquals("El id de carrera no existe en el repositorio", exception.getMessage());
    }

    @Test
    public void testGetCarrera() {
        Carrera carrera = new Carrera("Ingeniería", 3, 33, 8);
        carreraDao.saveCarrera(carrera);

        Carrera resultado = carreraDao.getCarrera(3);

        Assertions.assertNotNull(resultado);
        Assertions.assertEquals("Ingeniería", resultado.getNombre());
    }

    @Test
    public void testGetCarreraNoExistente() {
        Carrera resultado = carreraDao.getCarrera(82);
        Assertions.assertNull(resultado);
    }
    @Test
    public void testExisteCarreraPorId() {
        Carrera carrera = new Carrera("Ingeniería", 1, 2, 8);
        carreraDao.saveCarrera(carrera);

        boolean resultado = carreraDao.existeCarreraPorId(1);

        Assertions.assertTrue(resultado);
    }

    @Test
    public void testNoExisteCarreraPorId() {
        boolean resultado = carreraDao.existeCarreraPorId(123);
        Assertions.assertFalse(resultado);
    }

    @Test
    public void testAgregarMateria() {
        Carrera carrera = new Carrera("Ingeniería", 5, 55, 8);
        carrera.setMateriasList(new ArrayList<>());
        carreraDao.saveCarrera(carrera);

        Materia materia = new Materia(1, "Matemática", 1, 4, null, carrera);

        carreraDao.agregarMateria(materia, carrera.getIdCarrera());

        Carrera resultado = carreraDao.getCarrera(carrera.getIdCarrera());

        Assertions.assertNotNull(resultado.getMateriasList());
        Assertions.assertEquals(1, resultado.getMateriasList().size());
        Assertions.assertEquals("Matemática", resultado.getMateriasList().get(0).getNombre());
    }

}
