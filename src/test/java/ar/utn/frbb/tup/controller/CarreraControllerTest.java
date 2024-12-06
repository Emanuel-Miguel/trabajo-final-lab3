package ar.utn.frbb.tup.controller;

import ar.utn.frbb.tup.business.CarreraService;
import ar.utn.frbb.tup.business.exception.CantidadCuatrimestresInvalidaException;
import ar.utn.frbb.tup.business.exception.DatoNumericoInvalidoException;
import ar.utn.frbb.tup.business.exception.NombreInvalidoException;
import ar.utn.frbb.tup.model.Carrera;
import ar.utn.frbb.tup.model.dto.CarreraDTO;
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
@WebMvcTest(CarreraController.class)
@SuppressWarnings("unused")
public class CarreraControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarreraService carreraService;

    private static ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testCrearCarrera() throws Exception {
        CarreraDTO carreraDTO = new CarreraDTO();
        carreraDTO.setNombre("Ingenieria");
        carreraDTO.setIdCarrera(1);
        carreraDTO.setDepartamento(5);
        carreraDTO.setCantidadCuatrimestres(8);

        Carrera carreraMock = new Carrera("Ingenieria", 1, 5, 8);

        Mockito.when(carreraService.crearCarrera(Mockito.any(CarreraDTO.class))).thenReturn(carreraMock);

        mockMvc.perform(post("/carrera/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(carreraDTO)))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.nombre").value("Ingenieria"))
                        .andExpect(jsonPath("$.idCarrera").value(1))
                        .andExpect(jsonPath("$.departamento").value(5))
                        .andExpect(jsonPath("$.cantidadCuatrimestres").value(8));
    }

    @Test
    public void testCrearCarrera_NombreInvalidoException() throws Exception {
        CarreraDTO carreraDTO = new CarreraDTO();
        carreraDTO.setNombre(""); // Nombre inválido
        carreraDTO.setIdCarrera(1);
        carreraDTO.setDepartamento(5);
        carreraDTO.setCantidadCuatrimestres(8);

        Mockito.when(carreraService.crearCarrera(Mockito.any(CarreraDTO.class)))
                .thenThrow(new NombreInvalidoException("El nombre de la carrera es inválido"));

        mockMvc.perform(post("/carrera/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(carreraDTO)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.errorMessage").value("El nombre de la carrera es inválido"));
    }

    @Test
    public void testCrearCarrera_DuplicadoException() throws Exception {
        CarreraDTO carreraDTO = new CarreraDTO();
        carreraDTO.setNombre("Ingeniería");
        carreraDTO.setIdCarrera(1);
        carreraDTO.setDepartamento(5);
        carreraDTO.setCantidadCuatrimestres(8);

        Mockito.when(carreraService.crearCarrera(Mockito.any(CarreraDTO.class)))
                .thenThrow(new DuplicadoException("La carrera ya existe"));

        mockMvc.perform(post("/carrera/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(carreraDTO)))
                        .andExpect(status().isConflict())
                        .andExpect(jsonPath("$.errorMessage").value("La carrera ya existe"));
    }

    @Test
    public void testCrearCarrera_DatoNumericoInvalidoException() throws Exception {
        CarreraDTO carreraDTO = new CarreraDTO();
        carreraDTO.setNombre("Ingeniería");
        carreraDTO.setIdCarrera(-1); // ID negativo para provocar la excepción
        carreraDTO.setDepartamento(5);
        carreraDTO.setCantidadCuatrimestres(8);

        Mockito.when(carreraService.crearCarrera(Mockito.any(CarreraDTO.class)))
                .thenThrow(new DatoNumericoInvalidoException("El valor numérico es inválido"));

        mockMvc.perform(post("/carrera/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(carreraDTO)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.errorMessage").value("El valor numérico es inválido"));
    }

    @Test
    public void testCrearCarrera_CantidadCuatrimestresInvalidaException() throws Exception {
        CarreraDTO carreraDTO = new CarreraDTO();
        carreraDTO.setNombre("Ingeniería");
        carreraDTO.setIdCarrera(1);
        carreraDTO.setDepartamento(5);
        carreraDTO.setCantidadCuatrimestres(-1);  // Cantidad inválida de cuatrimestres

        Mockito.when(carreraService.crearCarrera(Mockito.any(CarreraDTO.class)))
                .thenThrow(new CantidadCuatrimestresInvalidaException("La cantidad de cuatrimestres es inválida"));

        mockMvc.perform(post("/carrera/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(carreraDTO)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.errorMessage").value("La cantidad de cuatrimestres es inválida"));
    }

    @Test
    public void testEliminarCarrera() throws Exception {
        Integer idCarrera = 1;

        Mockito.when(carreraService.eliminarCarrera(idCarrera)).thenReturn("Eliminado con Exito");

        mockMvc.perform(delete("/carrera/{idCarrera}", idCarrera))
                        .andExpect(status().isOk())
                        .andExpect(content().string("Eliminado con Exito"));
    }

    @Test
    public void testEliminarCarrera_NoEncontradoException() throws Exception {
        Integer idCarrera = 999; // ID no existente

        Mockito.when(carreraService.eliminarCarrera(idCarrera))
                .thenThrow(new NoEncontradoException("La carrera no existe"));

        mockMvc.perform(delete("/carrera/{idCarrera}", idCarrera))
                        .andExpect(status().isNotFound())
                        .andExpect(jsonPath("$.errorMessage").value("La carrera no existe"));
    }

    @Test
    public void testActualizarCarrera() throws Exception {
        Integer idCarrera = 1;

        CarreraDTO carreraDTO = new CarreraDTO();
        carreraDTO.setNombre("Arquitectura");
        carreraDTO.setIdCarrera(1);
        carreraDTO.setDepartamento(6);
        carreraDTO.setCantidadCuatrimestres(10);

        Carrera carreraMock = new Carrera("Arquitectura", 1, 6, 10);

        Mockito.when(carreraService.actualizarCarrera(Mockito.eq(idCarrera), Mockito.any(CarreraDTO.class))).thenReturn(carreraMock);

        mockMvc.perform(put("/carrera/{idCarrera}", idCarrera)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(carreraDTO)))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.nombre").value("Arquitectura"))
                        .andExpect(jsonPath("$.idCarrera").value(1))
                        .andExpect(jsonPath("$.departamento").value(6))
                        .andExpect(jsonPath("$.cantidadCuatrimestres").value(10));
    }

    @Test
    public void testActualizarCarrera_NombreInvalidoException() throws Exception {
        Integer idCarrera = 1;
        CarreraDTO carreraDTO = new CarreraDTO();
        carreraDTO.setNombre(""); // Nombre inválido
        carreraDTO.setIdCarrera(1);
        carreraDTO.setDepartamento(5);
        carreraDTO.setCantidadCuatrimestres(8);

        Mockito.when(carreraService.actualizarCarrera(Mockito.eq(idCarrera), Mockito.any(CarreraDTO.class)))
                .thenThrow(new NombreInvalidoException("El nombre de la carrera es inválido"));

        mockMvc.perform(put("/carrera/{idCarrera}", idCarrera)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(carreraDTO)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.errorMessage").value("El nombre de la carrera es inválido"));
    }

    @Test
    public void testActualizarCarrera_NoEncontradoException() throws Exception {
        Integer idCarrera = 999; // ID no existente
        CarreraDTO carreraDTO = new CarreraDTO();
        carreraDTO.setNombre("Ingeniería");
        carreraDTO.setIdCarrera(1);
        carreraDTO.setDepartamento(5);
        carreraDTO.setCantidadCuatrimestres(8);

        Mockito.when(carreraService.actualizarCarrera(Mockito.eq(idCarrera), Mockito.any(CarreraDTO.class)))
                .thenThrow(new NoEncontradoException("La carrera no existe"));

        mockMvc.perform(put("/carrera/{idCarrera}", idCarrera)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(carreraDTO)))
                        .andExpect(status().isNotFound())
                        .andExpect(jsonPath("$.errorMessage").value("La carrera no existe"));
    }

    @Test
    public void testActualizarCarrera_DuplicadoException() throws Exception {
        Integer idCarrera = 1; // ID existente
        CarreraDTO carreraDTO = new CarreraDTO();
        carreraDTO.setNombre("Ingeniería");  // Nombre duplicado
        carreraDTO.setIdCarrera(1);
        carreraDTO.setDepartamento(5);
        carreraDTO.setCantidadCuatrimestres(8);

        Mockito.when(carreraService.actualizarCarrera(Mockito.eq(idCarrera), Mockito.any(CarreraDTO.class)))
                .thenThrow(new DuplicadoException("La carrera ya existe"));

        mockMvc.perform(put("/carrera/{idCarrera}", idCarrera)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(carreraDTO)))
                        .andExpect(status().isConflict())
                        .andExpect(jsonPath("$.errorMessage").value("La carrera ya existe"));
    }

    @Test
    public void testActualizarCarrera_CantidadCuatrimestresInvalidaException() throws Exception {
        Integer idCarrera = 1;
        CarreraDTO carreraDTO = new CarreraDTO();
        carreraDTO.setNombre("Arquitectura");
        carreraDTO.setIdCarrera(1);
        carreraDTO.setDepartamento(6);
        carreraDTO.setCantidadCuatrimestres(-5);  // Cantidad inválida de cuatrimestres

        Mockito.when(carreraService.actualizarCarrera(Mockito.eq(idCarrera), Mockito.any(CarreraDTO.class)))
                .thenThrow(new CantidadCuatrimestresInvalidaException("La cantidad de cuatrimestres es inválida"));

        mockMvc.perform(put("/carrera/{idCarrera}", idCarrera)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(carreraDTO)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.errorMessage").value("La cantidad de cuatrimestres es inválida"));
    }

}
