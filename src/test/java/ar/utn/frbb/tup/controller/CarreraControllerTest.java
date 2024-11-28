package ar.utn.frbb.tup.controller;

import ar.utn.frbb.tup.business.CarreraService;
import ar.utn.frbb.tup.model.Carrera;
import ar.utn.frbb.tup.model.dto.CarreraDTO;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class CarreraControllerTest {
    @InjectMocks
    private CarreraController carreraController;

    @Mock
    private CarreraService carreraService;

    @Test
    public void testCrearCarrera() throws Exception {
        CarreraDTO carreraDTO = new CarreraDTO();
        carreraDTO.setNombre("Ingenieria");
        carreraDTO.setIdCarrera(1);
        carreraDTO.setDepartamento(5);
        carreraDTO.setCantidadCuatrimestres(8);

        Carrera carreraMock = new Carrera("Ingenieria", 1, 5, 8);

        Mockito.when(carreraService.crearCarrera(carreraDTO)).thenReturn(carreraMock);

        Carrera resultado = carreraController.crearCarrera(carreraDTO);

        Assertions.assertNotNull(resultado);
        Assertions.assertEquals("Ingenieria", resultado.getNombre());
        Assertions.assertEquals(1, resultado.getIdCarrera());
    }

    @Test
    public void testEliminarCarrera() throws Exception {
        Integer idCarrera = 1;
        String mensajeMock = "Eliminado con éxito";

        Mockito.when(carreraService.eliminarCarrera(idCarrera)).thenReturn(mensajeMock);

        String resultado = carreraController.eliminarCarrera(idCarrera);

        Assertions.assertNotNull(resultado);
        Assertions.assertEquals("Eliminado con éxito", resultado);
    }

    @Test
    public void testActualizarCarrera() throws Exception {
        Integer idCarrera = 1;
        CarreraDTO carreraDTO = new CarreraDTO();
        carreraDTO.setNombre("Arquitectura");
        carreraDTO.setIdCarrera(1);
        carreraDTO.setDepartamento(6);
        carreraDTO.setCantidadCuatrimestres(10);

        Carrera carreraActualizadaMock = new Carrera("Arquitectura", 1, 6, 10);

        Mockito.when(carreraService.actualizarCarrera(idCarrera, carreraDTO)).thenReturn(carreraActualizadaMock);

        Carrera resultado = carreraController.actualizarCarrera(idCarrera, carreraDTO);

        Assertions.assertNotNull(resultado);
        Assertions.assertEquals("Arquitectura", resultado.getNombre());
        Assertions.assertEquals(6, resultado.getDepartamento());
    }
}
