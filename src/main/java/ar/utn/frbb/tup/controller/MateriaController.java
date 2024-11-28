package ar.utn.frbb.tup.controller;

import ar.utn.frbb.tup.business.MateriaService;
import ar.utn.frbb.tup.business.exception.DatoNumericoInvalidoException;
import ar.utn.frbb.tup.business.exception.NombreInvalidoException;
import ar.utn.frbb.tup.model.Materia;
import ar.utn.frbb.tup.model.dto.MateriaDTO;
import ar.utn.frbb.tup.persistence.exception.DuplicadoException;
import ar.utn.frbb.tup.persistence.exception.NoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("materia")
public class MateriaController {
    @Autowired
    MateriaService materiaService;

    @PostMapping("/")
    public Materia crearMateria(@RequestBody MateriaDTO materiaDTO) throws NombreInvalidoException, DatoNumericoInvalidoException, DuplicadoException, NoEncontradoException {
        return materiaService.crearMateria(materiaDTO);
    }

    @DeleteMapping("/{idMateria}")
    public String eliminarMateria(@PathVariable Integer idMateria) throws NoEncontradoException {
        return materiaService.eliminarMateria(idMateria);
    }

    @PutMapping("/{idMateria}")
    public Materia actualizarMateria(@PathVariable Integer idMateria, @RequestBody MateriaDTO materiaDTO) throws NombreInvalidoException, NoEncontradoException, DatoNumericoInvalidoException, DuplicadoException {
        return materiaService.actualizarMateria(idMateria, materiaDTO);
    }

    @GetMapping
    public List<Materia> obtenerMateriasPorNombre(@RequestParam(required = false) String nombre) throws NoEncontradoException {
        return materiaService.obtenerMateriasPorNombre(nombre);
    }

    @GetMapping("/materias")
    public List<Materia> listarMateriasOrdenadas(@RequestParam String order) {
        return materiaService.listarMateriasOrdenadas(order);
    }
}
