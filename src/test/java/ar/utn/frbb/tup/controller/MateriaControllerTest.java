package ar.utn.frbb.tup.controller;

import ar.utn.frbb.tup.business.MateriaService;
import ar.utn.frbb.tup.model.Carrera;
import ar.utn.frbb.tup.model.Materia;
import ar.utn.frbb.tup.model.Profesor;
import ar.utn.frbb.tup.model.dto.MateriaDTO;
import ar.utn.frbb.tup.persistence.exception.NoEncontradoException;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
public class MateriaControllerTest {
    @InjectMocks
    private MateriaController materiaController;

    @Mock
    private MateriaService materiaService;

    @Test
    public void testCrearMateria() throws Exception {
        MateriaDTO materiaDTO = new MateriaDTO();
        materiaDTO.setIdMateria(1);
        materiaDTO.setNombre("Matematicas");
        materiaDTO.setAnio(2024);
        materiaDTO.setHoras(100);

        Carrera carrera = new Carrera();
        carrera.setIdCarrera(10);

        Profesor profesor = new Profesor("Juan", "Perez", "Licenciado en Matematicas");
        materiaDTO.setProfesor(profesor);
        materiaDTO.setIdCarrera(10);

        Materia materiaMock = new Materia(1, "Matematicas", 2024, 100, profesor, carrera);

        Mockito.when(materiaService.crearMateria(materiaDTO)).thenReturn(materiaMock);

        Materia resultado = materiaController.crearMateria(materiaDTO);

        Assertions.assertNotNull(resultado);
        Assertions.assertEquals("Matematicas", resultado.getNombre());
        Assertions.assertEquals(1, resultado.getIdMateria());
    }

    @Test
    public void testEliminarMateria() throws Exception {
        Integer idMateria = 1;
        String mensajeMock = "Eliminado con éxito";

        Mockito.when(materiaService.eliminarMateria(idMateria)).thenReturn(mensajeMock);

        String resultado = materiaController.eliminarMateria(idMateria);

        Assertions.assertNotNull(resultado);
        Assertions.assertEquals("Eliminado con éxito", resultado);
    }

    @Test
    public void testActualizarMateria() throws Exception {
        Integer idMateria = 1;
        MateriaDTO materiaDTO = new MateriaDTO();
        materiaDTO.setIdMateria(1);
        materiaDTO.setNombre("Fisica");
        materiaDTO.setAnio(2025);
        materiaDTO.setHoras(120);

        Carrera carrera = new Carrera();
        carrera.setIdCarrera(10);

        Profesor profesor = new Profesor("Ana", "Garcia", "Licenciada en Fisica");
        materiaDTO.setProfesor(profesor);
        materiaDTO.setIdCarrera(15);

        Materia materiaActualizadaMock = new Materia(1, "Fisica", 2025, 120, profesor, carrera);

        Mockito.when(materiaService.actualizarMateria(idMateria, materiaDTO)).thenReturn(materiaActualizadaMock);

        Materia resultado = materiaController.actualizarMateria(idMateria, materiaDTO);

        Assertions.assertNotNull(resultado);
        Assertions.assertEquals("Fisica", resultado.getNombre());
        Assertions.assertEquals(2025, resultado.getAnio());
    }

    @Test
    public void testObtenerMateriasPorNombre() throws NoEncontradoException {
        Materia materia1 = new Materia(1, "Matemáticas", 2024, 60, null, null);
        Materia materia2 = new Materia(2, "Física", 2024, 50, null, null);

        Mockito.when(materiaService.obtenerMateriasPorNombre("Matemáticas")).thenReturn(Arrays.asList(materia1));

        List<Materia> resultado = materiaController.obtenerMateriasPorNombre("Matemáticas");

        Assertions.assertNotNull(resultado);
        Assertions.assertEquals(1, resultado.size());
        Assertions.assertEquals("Matemáticas", resultado.get(0).getNombre());
    }

    @Test
    public void testListarMateriasOrdenadas() {
        Materia materia1 = new Materia(1, "Estadística", 2024, 60, null, null);
        Materia materia2 = new Materia(2, "Programación", 2024, 50, null, null);

        Mockito.when(materiaService.listarMateriasOrdenadas("nombre_asc")).thenReturn(Arrays.asList(materia1, materia2));

        List<Materia> resultado = materiaController.listarMateriasOrdenadas("nombre_asc");

        Assertions.assertNotNull(resultado);
        Assertions.assertEquals(2, resultado.size());
        Assertions.assertEquals("Estadística", resultado.get(0).getNombre());
        Assertions.assertEquals("Programación", resultado.get(1).getNombre());
    }
}
