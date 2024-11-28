package ar.utn.frbb.tup.persistence;

import ar.utn.frbb.tup.model.Carrera;
import ar.utn.frbb.tup.model.Materia;
import ar.utn.frbb.tup.model.Profesor;
import ar.utn.frbb.tup.persistence.Implementation.MateriaDaoMemoryImpl;
import ar.utn.frbb.tup.persistence.exception.NoEncontradoException;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
public class MateriaDaoTest {
    @InjectMocks
    private MateriaDaoMemoryImpl materiaDao;

    @BeforeEach
    public void setUp() {
        materiaDao = new MateriaDaoMemoryImpl();
    }

    @Test
    public void testSaveMateria() {
        Carrera carrera = new Carrera("Ingenieria", 1, 11, 8);
        Profesor profesor = new Profesor("Juan", "Perez", "Ingeniero en Sistemas");

        Materia materia = new Materia(1, "Matematica", 2023, 40, profesor, carrera);

        Materia resultado = materiaDao.saveMateria(materia);

        Assertions.assertNotNull(resultado);
        Assertions.assertEquals(materia, resultado);
        Assertions.assertEquals("Ingenieria", resultado.getCarrera().getNombre());
    }

    @Test
    public void testDeleteMateriaExistente() throws NoEncontradoException {
        Carrera carrera = new Carrera("Ingenieria", 1, 11, 8);
        Profesor profesor = new Profesor("Luis", "Fernandez", "Ingeniero en Electromecanica");

        Materia materia = new Materia(5, "Circuitos", 2021, 35, profesor, carrera);
        materiaDao.saveMateria(materia);

        String resultado = materiaDao.deleteMateria(5);
        Assertions.assertEquals("Eliminado con Exito", resultado);
        Assertions.assertNull(materiaDao.getMateria(5));
    }

    @Test
    public void testDeleteMateriaNoExistente() {
        Exception exception = Assertions.assertThrows(NoEncontradoException.class, () -> materiaDao.deleteMateria(55));
        Assertions.assertEquals("El id de la materia no existe en el repositorio", exception.getMessage());
    }

    @Test
    public void testGetMateria() {
        Carrera carrera = new Carrera("Ingenieria", 1, 11, 8);
        Profesor profesor = new Profesor("Juan", "Perez", "Ingeniero en Sistemas");

        Materia materia = new Materia(1, "Matematica", 2023, 40, profesor, carrera);
        materiaDao.saveMateria(materia);

        Materia resultado = materiaDao.getMateria(1);
        Assertions.assertNotNull(resultado);
        Assertions.assertEquals("Matematica", resultado.getNombre());
    }

    @Test
    public void testGetMateriaNoExistente() {
        Materia resultado = materiaDao.getMateria(55);
        Assertions.assertNull(resultado);
    }

    @Test
    public void testExisteMateriaPorId() {
        Carrera carrera = new Carrera("Ingenieria", 1, 11, 8);
        Profesor profesor = new Profesor("Juan", "Perez", "Ingeniero en Sistemas");

        Materia materia = new Materia(1, "Matematica", 2023, 40, profesor, carrera);
        materiaDao.saveMateria(materia);

        boolean resultado = materiaDao.existeMateriaPorId(1);
        Assertions.assertTrue(resultado);
    }

    @Test
    public void testNoExisteMateriaPorId() {
        boolean resultado = materiaDao.existeMateriaPorId(55);
        Assertions.assertFalse(resultado);
    }

    @Test
    public void testFindMateriasByName() {
        Carrera carrera = new Carrera("Ingenieria", 1, 11, 8);
        Profesor profesor = new Profesor("Juan", "Perez", "Ingeniero en Sistemas");

        Materia materia1 = new Materia(1, "Matematicas", 1, 60, profesor, carrera);
        Materia materia2 = new Materia(2, "Fisica", 1, 50, profesor, carrera);
        materiaDao.saveMateria(materia1);
        materiaDao.saveMateria(materia2);

        List<Materia> resultado = materiaDao.findMateriasByName("Matematicas");
        Assertions.assertEquals(1, resultado.size());
        Assertions.assertEquals("Matematicas", resultado.get(0).getNombre());
    }

    @Test
    public void testFindAllOrdered_NombreAsc() {
        Carrera carrera = new Carrera("Ingenieria", 1, 11, 8);
        Profesor profesor = new Profesor("Juan", "Perez", "Ingeniero en Sistemas");

        Materia materia1 = new Materia(1, "Matematicas", 1, 60, profesor, carrera);
        Materia materia2 = new Materia(2, "Fisica", 1, 50, profesor, carrera);
        materiaDao.saveMateria(materia1);
        materiaDao.saveMateria(materia2);

        List<Materia> resultado = materiaDao.findAllOrdered("nombre_asc");
        Assertions.assertEquals(2, resultado.size());
        Assertions.assertEquals("Fisica", resultado.get(0).getNombre());
        Assertions.assertEquals("Matematicas", resultado.get(1).getNombre());
    }

    @Test
    public void testFindAllOrdered_NombreDesc() {
        Carrera carrera = new Carrera("Ingenieria", 1, 11, 8);
        Profesor profesor = new Profesor("Juan", "Perez", "Ingeniero en Sistemas");

        Materia materia1 = new Materia(1, "Matematicas", 1, 60, profesor, carrera);
        Materia materia2 = new Materia(2, "Fisica", 1, 50, profesor, carrera);
        materiaDao.saveMateria(materia1);
        materiaDao.saveMateria(materia2);

        List<Materia> resultado = materiaDao.findAllOrdered("nombre_desc");
        Assertions.assertEquals(2, resultado.size());
        Assertions.assertEquals("Matematicas", resultado.get(0).getNombre());
        Assertions.assertEquals("Fisica", resultado.get(1).getNombre());
    }

    @Test
    public void testFindAllOrdered_CodigoAsc() {
        Carrera carrera = new Carrera("Ingenieria", 1, 11, 8);
        Profesor profesor = new Profesor("Juan", "Perez", "Ingeniero en Sistemas");

        Materia materia1 = new Materia(1, "Matematicas", 1, 60, profesor, carrera);
        Materia materia2 = new Materia(2, "Fisica", 1, 50, profesor, carrera);
        materiaDao.saveMateria(materia1);
        materiaDao.saveMateria(materia2);

        List<Materia> resultado = materiaDao.findAllOrdered("codigo_asc");
        Assertions.assertEquals(2, resultado.size());
        Assertions.assertEquals(1, resultado.get(0).getIdMateria());
        Assertions.assertEquals(2, resultado.get(1).getIdMateria());
    }

    @Test
    public void testFindAllOrdered_CodigoDesc() {
        Carrera carrera = new Carrera("Ingenieria", 1, 11, 8);
        Profesor profesor = new Profesor("Juan", "Perez", "Ingeniero en Sistemas");

        Materia materia1 = new Materia(1, "Matematicas", 1, 60, profesor, carrera);
        Materia materia2 = new Materia(2, "Fisica", 1, 50, profesor, carrera);
        materiaDao.saveMateria(materia1);
        materiaDao.saveMateria(materia2);

        List<Materia> resultado = materiaDao.findAllOrdered("codigo_desc");
        Assertions.assertEquals(2, resultado.size());
        Assertions.assertEquals(2, resultado.get(0).getIdMateria());
        Assertions.assertEquals(1, resultado.get(1).getIdMateria());
    }

    @Test
    public void testFindAllOrdered_InvalidOrder() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> materiaDao.findAllOrdered("invalido"));
        Assertions.assertEquals("Orden no válido: invalido", exception.getMessage());
    }
}
