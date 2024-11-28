package ar.utn.frbb.tup.business;

import ar.utn.frbb.tup.business.Implementation.CarreraServiceImpl;
import ar.utn.frbb.tup.model.Carrera;
import ar.utn.frbb.tup.model.Materia;
import ar.utn.frbb.tup.model.dto.CarreraDTO;
import ar.utn.frbb.tup.persistence.CarreraDao;
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

@RunWith(SpringRunner.class)
public class CarreraServiceTest {

    @InjectMocks
    private CarreraServiceImpl carreraService;

    @Mock
    private CarreraDao carreraDao;

    @Test
    public void testCrearCarreraExito() throws Exception {
        CarreraDTO carreraDTO = new CarreraDTO();
        carreraDTO.setNombre("Ingenieria");
        carreraDTO.setIdCarrera(1);
        carreraDTO.setDepartamento(2);
        carreraDTO.setCantidadCuatrimestres(8);

        Carrera carrera = new Carrera("Ingenieria", 1, 2, 8);

        Mockito.when(carreraDao.existeCarreraPorId(1)).thenReturn(false);
        Mockito.when(carreraDao.saveCarrera(Mockito.any(Carrera.class))).thenReturn(carrera);

        Carrera resultado = carreraService.crearCarrera(carreraDTO);

        Assertions.assertNotNull(resultado);
        Assertions.assertEquals("Ingenieria", resultado.getNombre());
        Assertions.assertEquals(1, resultado.getIdCarrera());
        Assertions.assertEquals(2, resultado.getDepartamento());
        Assertions.assertEquals(8, resultado.getCantidadCuatrimestres());
    }

    @Test
    public void testCrearCarreraDuplicado() {
        CarreraDTO carreraDTO = new CarreraDTO();
        carreraDTO.setNombre("Ingenieria");
        carreraDTO.setIdCarrera(1);
        carreraDTO.setDepartamento(2);
        carreraDTO.setCantidadCuatrimestres(8);

        Mockito.when(carreraDao.existeCarreraPorId(1)).thenReturn(true);

        Assertions.assertThrows(DuplicadoException.class, () -> {carreraService.crearCarrera(carreraDTO);});
    }

    @Test
    public void testEliminarCarreraExito() throws Exception {
        Integer idCarrera = 1;

        Mockito.when(carreraDao.deleteCarrera(idCarrera)).thenReturn("Eliminado con Exito");

        String resultado = carreraService.eliminarCarrera(idCarrera);

        Assertions.assertEquals("Eliminado con Exito", resultado);
    }

    @Test
    public void testEliminarCarreraNoEncontrado() throws NoEncontradoException {
        Integer idCarrera = 1;

        Mockito.when(carreraDao.deleteCarrera(idCarrera)).thenThrow(new NoEncontradoException("El id no existe"));

        Assertions.assertThrows(NoEncontradoException.class, () -> { carreraService.eliminarCarrera(idCarrera); });
    }

    @Test
    public void testActualizarCarreraExito() throws Exception {
        Integer idCarrera = 1;

        CarreraDTO carreraDTO = new CarreraDTO();
        carreraDTO.setNombre("Licenciatura");
        carreraDTO.setIdCarrera(2);
        carreraDTO.setDepartamento(3);
        carreraDTO.setCantidadCuatrimestres(10);

        Carrera carreraExistente = new Carrera("Ingenieria", 1, 2, 8);
        Carrera carreraActualizada = new Carrera("Licenciatura", 2, 3, 10);

        Mockito.when(carreraDao.getCarrera(idCarrera)).thenReturn(carreraExistente);
        Mockito.when(carreraDao.existeCarreraPorId(2)).thenReturn(false);
        Mockito.when(carreraDao.saveCarrera(Mockito.any(Carrera.class))).thenReturn(carreraActualizada);

        Carrera resultado = carreraService.actualizarCarrera(idCarrera, carreraDTO);

        Assertions.assertEquals("Licenciatura", resultado.getNombre());
        Assertions.assertEquals(2, resultado.getIdCarrera());
    }

    @Test
    public void testGetCarreraExito() {
        Integer idCarrera = 1;
        Carrera carrera = new Carrera("Ingenieria", 1, 2, 8);

        Mockito.when(carreraDao.getCarrera(idCarrera)).thenReturn(carrera);

        Carrera resultado = carreraService.getCarrera(idCarrera);

        Assertions.assertEquals("Ingenieria", resultado.getNombre());
        Assertions.assertEquals(1, resultado.getIdCarrera());
    }

    @Test
    public void testGetCarreraNoEncontrado() {
        Integer idCarrera = 1;

        Mockito.when(carreraDao.getCarrera(idCarrera)).thenReturn(null);

        Assertions.assertNull(carreraService.getCarrera(idCarrera));
    }

    @Test
    public void testAgregarMateria() {
        Integer idCarrera = 1;
        Materia materia = new Materia();
        materia.setNombre("Matemáticas");

        Carrera carrera = new Carrera("Ingenieria", 1, 2, 8);
        carrera.setMateriasList(new ArrayList<>());

        Mockito.when(carreraDao.getCarrera(idCarrera)).thenReturn(carrera);

        carreraService.agregarMateria(materia, idCarrera);
    }

}
