package ar.utn.frbb.tup.business;

import ar.utn.frbb.tup.business.Implementation.MateriaServiceImpl;
import ar.utn.frbb.tup.model.Carrera;
import ar.utn.frbb.tup.model.Materia;
import ar.utn.frbb.tup.model.Profesor;
import ar.utn.frbb.tup.model.dto.MateriaDTO;
import ar.utn.frbb.tup.persistence.MateriaDao;
import ar.utn.frbb.tup.persistence.exception.DuplicadoException;
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
public class MateriaServiceTest {
    @InjectMocks
    private MateriaServiceImpl materiaService;

    @Mock
    private MateriaDao materiaDao;

    @Mock
    private CarreraService carreraDao;

    @Test
    public void testCrearMateriaExito() throws Exception {
        Profesor profesor = new Profesor();
        profesor.setNombre("Juan");
        profesor.setApellido("Perez");
        profesor.setTitulo("Licenciado en Matematica");

        Carrera carrera = new Carrera();
        carrera.setIdCarrera(10);

        MateriaDTO materiaDTO = new MateriaDTO();
        materiaDTO.setIdMateria(1);
        materiaDTO.setNombre("Matematicas");
        materiaDTO.setAnio(3);
        materiaDTO.setHoras(120);
        materiaDTO.setProfesor(profesor);
        materiaDTO.setIdCarrera(10);

        Materia materia = new Materia(1,"Matematicas",3, 120, profesor, carrera);

        Mockito.when(materiaDao.existeMateriaPorId(1)).thenReturn(false);
        Mockito.when(materiaDao.saveMateria(Mockito.any(Materia.class))).thenReturn(materia);
        Mockito.when(carreraDao.getCarrera(10)).thenReturn(carrera);

        Materia resultado = materiaService.crearMateria(materiaDTO);

        Assertions.assertNotNull(resultado);
        Assertions.assertEquals("Matematicas", resultado.getNombre());
        Assertions.assertEquals(1, resultado.getIdMateria());
        Assertions.assertEquals(120, resultado.getHoras());
    }

    @Test
    public void testCrearMateriaDuplicado() {
        Profesor profesor = new Profesor();
        profesor.setNombre("Juan");
        profesor.setApellido("Perez");
        profesor.setTitulo("Licenciado en Matematica");

        Carrera carrera = new Carrera();
        carrera.setIdCarrera(10);

        MateriaDTO materiaDTO = new MateriaDTO();
        materiaDTO.setIdMateria(1);
        materiaDTO.setNombre("Matematicas");
        materiaDTO.setAnio(3);
        materiaDTO.setHoras(120);
        materiaDTO.setProfesor(profesor);
        materiaDTO.setIdCarrera(10);

        Mockito.when(materiaDao.existeMateriaPorId(1)).thenReturn(true);

        Assertions.assertThrows(DuplicadoException.class, () -> {materiaService.crearMateria(materiaDTO);});
    }

    @Test
    public void testEliminarMateriaExito() throws Exception {
        Integer idMateria = 1;

        Mockito.when(materiaDao.deleteMateria(idMateria)).thenReturn("Eliminado con Exito");

        String resultado = materiaService.eliminarMateria(idMateria);

        Assertions.assertEquals("Eliminado con Exito", resultado);
    }

    @Test
    public void testEliminarMateriaNoEncontrada() throws NoEncontradoException {
        Integer idMateria = 1;

        Mockito.when(materiaDao.deleteMateria(idMateria)).thenThrow(new NoEncontradoException("El id no existe"));

        Assertions.assertThrows(NoEncontradoException.class, () -> {materiaService.eliminarMateria(idMateria); });
    }

    @Test
    public void testActualizarMateriaExito() throws Exception {
        Integer idMateria = 1;

        Profesor profesor = new Profesor();
        profesor.setNombre("Juan");
        profesor.setApellido("Perez");
        profesor.setTitulo("Licenciado en Matematica");

        Carrera carrera = new Carrera();
        carrera.setIdCarrera(10);

        MateriaDTO materiaDTO = new MateriaDTO();
        materiaDTO.setIdMateria(1);
        materiaDTO.setNombre("Fisica");
        materiaDTO.setAnio(2);
        materiaDTO.setHoras(100);
        materiaDTO.setProfesor(profesor);
        materiaDTO.setIdCarrera(10);

        Materia materiaExistente = new Materia(1, "Matematicas", 3, 120, profesor, carrera);
        Materia materiaActualizada = new Materia(1, "Fisica", 2, 100, profesor, carrera);

        Mockito.when(materiaDao.getMateria(idMateria)).thenReturn(materiaExistente);
        Mockito.when(carreraDao.getCarrera(10)).thenReturn(carrera);
        Mockito.when(materiaDao.saveMateria(Mockito.any(Materia.class))).thenReturn(materiaActualizada);

        Materia resultado = materiaService.actualizarMateria(idMateria, materiaDTO);

        Assertions.assertEquals("Fisica", resultado.getNombre());
        Assertions.assertEquals(2, resultado.getAnio());
        Assertions.assertEquals(100, resultado.getHoras());
    }

    @Test
    public void testObtenerMateriasPorNombre() throws NoEncontradoException {
        Materia materia1 = new Materia();
        materia1.setNombre("Matematicas");
        Materia materia2 = new Materia();
        materia2.setNombre("Fisica");

        Mockito.when(materiaDao.findMateriasByName("Matematicas")).thenReturn(Arrays.asList(materia1, materia2));

        List<Materia> resultado = materiaService.obtenerMateriasPorNombre("Matematicas");

        Assertions.assertEquals(2, resultado.size());
        Assertions.assertTrue(resultado.stream().anyMatch(m -> m.getNombre().equals("Matematicas")));
    }

    @Test
    public void testListarMateriasOrdenadas() {
        Materia materia1 = new Materia();
        materia1.setNombre("Estadistica");
        Materia materia2 = new Materia();
        materia2.setNombre("Programacion");

        Mockito.when(materiaDao.findAllOrdered("nombre_asc")).thenReturn(Arrays.asList(materia1, materia2));

        List<Materia> resultado = materiaService.listarMateriasOrdenadas("nombre_asc");

        Assertions.assertEquals(2, resultado.size());
        Assertions.assertEquals("Estadistica", resultado.get(0).getNombre());
    }
}
