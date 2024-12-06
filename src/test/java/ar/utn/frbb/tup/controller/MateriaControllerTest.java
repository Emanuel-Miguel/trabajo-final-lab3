package ar.utn.frbb.tup.controller;

import ar.utn.frbb.tup.business.MateriaService;
import ar.utn.frbb.tup.business.exception.DatoNumericoInvalidoException;
import ar.utn.frbb.tup.business.exception.NombreInvalidoException;
import ar.utn.frbb.tup.model.Carrera;
import ar.utn.frbb.tup.model.Materia;
import ar.utn.frbb.tup.model.Profesor;
import ar.utn.frbb.tup.model.dto.MateriaDTO;
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

import java.util.Arrays;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(MateriaController.class)
@SuppressWarnings("unused")
public class MateriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MateriaService materiaService;

    private static ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testCrearMateria() throws Exception {
        MateriaDTO materiaDTO = new MateriaDTO();
        materiaDTO.setIdMateria(1);
        materiaDTO.setNombre("Matemáticas");
        materiaDTO.setAnio(2024);
        materiaDTO.setHoras(100);

        Carrera carrera = new Carrera();
        carrera.setIdCarrera(10);

        Profesor profesor = new Profesor("Juan", "Perez", "Licenciado en Matemáticas");
        materiaDTO.setProfesor(profesor);
        materiaDTO.setIdCarrera(10);

        Materia materiaMock = new Materia(1, "Matemáticas", 2024, 100, profesor, carrera);

        Mockito.when(materiaService.crearMateria(Mockito.any(MateriaDTO.class))).thenReturn(materiaMock);

        mockMvc.perform(post("/materia/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(materiaDTO)))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.idMateria").value(1))
                        .andExpect(jsonPath("$.nombre").value("Matemáticas"))
                        .andExpect(jsonPath("$.anio").value(2024))
                        .andExpect(jsonPath("$.horas").value(100));
    }

    @Test
    public void testCrearMateria_NombreInvalidoException() throws Exception {
        MateriaDTO materiaDTO = new MateriaDTO();
        materiaDTO.setIdMateria(1);
        materiaDTO.setNombre(""); // Nombre inválido
        materiaDTO.setAnio(2024);
        materiaDTO.setHoras(100);

        Mockito.when(materiaService.crearMateria(Mockito.any(MateriaDTO.class)))
                .thenThrow(new NombreInvalidoException("El nombre de la materia es inválido"));

        mockMvc.perform(post("/materia/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(materiaDTO)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.errorMessage").value("El nombre de la materia es inválido"));
    }

    @Test
    public void testCrearMateria_DuplicadoException() throws Exception {
        MateriaDTO materiaDTO = new MateriaDTO();
        materiaDTO.setIdMateria(1);
        materiaDTO.setNombre("Matemáticas");
        materiaDTO.setAnio(2024);
        materiaDTO.setHoras(100);

        Mockito.when(materiaService.crearMateria(Mockito.any(MateriaDTO.class)))
                .thenThrow(new DuplicadoException("La materia ya existe"));

        mockMvc.perform(post("/materia/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(materiaDTO)))
                        .andExpect(status().isConflict())
                        .andExpect(jsonPath("$.errorMessage").value("La materia ya existe"));
    }

    @Test
    public void testCrearMateria_DatoNumericoInvalidoException() throws Exception {
        MateriaDTO materiaDTO = new MateriaDTO();
        materiaDTO.setIdMateria(1);
        materiaDTO.setNombre("Programación");
        materiaDTO.setAnio(2024);
        materiaDTO.setHoras(-10); // Valor horas negativo para provocar la excepción

        Mockito.when(materiaService.crearMateria(Mockito.any(MateriaDTO.class)))
                .thenThrow(new DatoNumericoInvalidoException("El valor de las horas es inválido"));

        mockMvc.perform(post("/materia/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(materiaDTO)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.errorMessage").value("El valor de las horas es inválido"));
    }

    @Test
    public void testEliminarMateria() throws Exception {
        Integer idMateria = 1;

        Mockito.when(materiaService.eliminarMateria(idMateria)).thenReturn("Eliminado con Exito");

        mockMvc.perform(delete("/materia/{idMateria}", idMateria))
                        .andExpect(status().isOk())
                        .andExpect(content().string("Eliminado con Exito"));
    }

    @Test
    public void testEliminarMateria_NoEncontradoException() throws Exception {
        Integer idMateria = 999; // ID no existente

        Mockito.when(materiaService.eliminarMateria(idMateria))
                .thenThrow(new NoEncontradoException("La materia no existe"));

        mockMvc.perform(delete("/materia/{idMateria}", idMateria))
                        .andExpect(status().isNotFound())
                        .andExpect(jsonPath("$.errorMessage").value("La materia no existe"));
    }

    @Test
    public void testActualizarMateria() throws Exception {
        Integer idMateria = 1;

        MateriaDTO materiaDTO = new MateriaDTO();
        materiaDTO.setIdMateria(1);
        materiaDTO.setNombre("Fisica");
        materiaDTO.setAnio(2024);
        materiaDTO.setHoras(120);

        Carrera carrera = new Carrera();
        carrera.setIdCarrera(15);

        Profesor profesor = new Profesor("Ana", "Garcia", "Licenciada en Fisica");
        materiaDTO.setProfesor(profesor);

        Materia materiaMock = new Materia(1, "Fisica", 2024, 120, profesor, carrera);

        Mockito.when(materiaService.actualizarMateria(Mockito.eq(idMateria), Mockito.any(MateriaDTO.class))).thenReturn(materiaMock);

        mockMvc.perform(put("/materia/{idMateria}", idMateria)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(materiaDTO)))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.idMateria").value(1))
                        .andExpect(jsonPath("$.nombre").value("Fisica"))
                        .andExpect(jsonPath("$.anio").value(2024))
                        .andExpect(jsonPath("$.horas").value(120));
    }

    @Test
    public void testActualizarMateria_NombreInvalidoException() throws Exception {
        Integer idMateria = 1;
        MateriaDTO materiaDTO = new MateriaDTO();
        materiaDTO.setIdMateria(1);
        materiaDTO.setNombre(""); // Nombre inválido
        materiaDTO.setAnio(2024);
        materiaDTO.setHoras(100);

        Mockito.when(materiaService.actualizarMateria(Mockito.eq(idMateria), Mockito.any(MateriaDTO.class)))
                .thenThrow(new NombreInvalidoException("El nombre de la materia es inválido"));

        mockMvc.perform(put("/materia/{idMateria}", idMateria)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(materiaDTO)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.errorMessage").value("El nombre de la materia es inválido"));
    }

    @Test
    public void testActualizarMateria_NoEncontradoException() throws Exception {
        Integer idMateria = 999; // ID no existente
        MateriaDTO materiaDTO = new MateriaDTO();
        materiaDTO.setIdMateria(999);
        materiaDTO.setNombre("Matemáticas");
        materiaDTO.setAnio(2024);
        materiaDTO.setHoras(120);

        Mockito.when(materiaService.actualizarMateria(Mockito.eq(idMateria), Mockito.any(MateriaDTO.class)))
                .thenThrow(new NoEncontradoException("La materia no existe"));

        mockMvc.perform(put("/materia/" + idMateria)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(materiaDTO)))
                        .andExpect(status().isNotFound())
                        .andExpect(jsonPath("$.errorMessage").value("La materia no existe"));
    }

    @Test
    public void testActualizarMateria_DuplicadoException() throws Exception {
        Integer idMateria = 1; // ID existente
        MateriaDTO materiaDTO = new MateriaDTO();
        materiaDTO.setIdMateria(1);
        materiaDTO.setNombre("Matemáticas"); // Nombre duplicado
        materiaDTO.setAnio(2024);
        materiaDTO.setHoras(120);

        Mockito.when(materiaService.actualizarMateria(Mockito.eq(idMateria), Mockito.any(MateriaDTO.class)))
                .thenThrow(new DuplicadoException("El nombre de la materia ya existe"));

        mockMvc.perform(put("/materia/" + idMateria)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(materiaDTO)))
                        .andExpect(status().isConflict())
                        .andExpect(jsonPath("$.errorMessage").value("El nombre de la materia ya existe"));
    }

    @Test
    public void testObtenerMateriasPorNombre() throws Exception {
        Materia materia1 = new Materia(1, "Matemáticas", 2024, 60, null, null);

        Mockito.when(materiaService.obtenerMateriasPorNombre("Matemáticas")).thenReturn(Collections.singletonList(materia1));

        mockMvc.perform(get("/materia")
                        .param("nombre", "Matemáticas"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$[0].idMateria").value(1))
                        .andExpect(jsonPath("$[0].nombre").value("Matemáticas"));
    }

    @Test
    public void testObtenerMateriasPorNombre_NoEncontradoException() throws Exception {
        String nombreMateria = "Química";

        Mockito.when(materiaService.obtenerMateriasPorNombre(nombreMateria))
                .thenThrow(new NoEncontradoException("La materia no existe"));

        mockMvc.perform(get("/materia")
                        .param("nombre", nombreMateria))
                        .andExpect(status().isNotFound())
                        .andExpect(jsonPath("$.errorMessage").value("La materia no existe"));
    }

    @Test
    public void testListarMateriasOrdenadas() throws Exception {
        Materia materia1 = new Materia(1, "Estadística", 2024, 60, null, null);
        Materia materia2 = new Materia(2, "Programación", 2024, 50, null, null);

        // Test para nombre_asc
        Mockito.when(materiaService.listarMateriasOrdenadas("nombre_asc")).
                thenReturn(Arrays.asList(materia1, materia2));

        mockMvc.perform(get("/materia/materias")
                        .param("order", "nombre_asc"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$[0].nombre").value("Estadística"))
                        .andExpect(jsonPath("$[1].nombre").value("Programación"));

        // Test para nombre_desc
        Mockito.when(materiaService.listarMateriasOrdenadas("nombre_desc"))
                .thenReturn(Arrays.asList(materia2, materia1));

        mockMvc.perform(get("/materia/materias")
                        .param("order", "nombre_desc"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$[0].nombre").value("Programación"))
                        .andExpect(jsonPath("$[1].nombre").value("Estadística"));

        // Test para codigo_asc
        Mockito.when(materiaService.listarMateriasOrdenadas("codigo_asc"))
                .thenReturn(Arrays.asList(materia1, materia2));

        mockMvc.perform(get("/materia/materias")
                        .param("order", "codigo_asc"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$[0].idMateria").value(1))
                        .andExpect(jsonPath("$[1].idMateria").value(2));

        // Test para codigo_desc
        Mockito.when(materiaService.listarMateriasOrdenadas("codigo_desc"))
                .thenReturn(Arrays.asList(materia2, materia1));

        mockMvc.perform(get("/materia/materias")
                        .param("order", "codigo_desc"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$[0].idMateria").value(2))
                        .andExpect(jsonPath("$[1].idMateria").value(1));
    }

}
