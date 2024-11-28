package ar.utn.frbb.tup.controller;

import ar.utn.frbb.tup.business.CarreraService;
import ar.utn.frbb.tup.business.exception.*;
import ar.utn.frbb.tup.model.Carrera;
import ar.utn.frbb.tup.model.dto.CarreraDTO;
import ar.utn.frbb.tup.persistence.exception.DuplicadoException;
import ar.utn.frbb.tup.persistence.exception.NoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("carrera")
public class CarreraController {
    @Autowired
    CarreraService carreraService;

    @PostMapping("/")
    public Carrera crearCarrera(@RequestBody CarreraDTO carreraDTO) throws NombreInvalidoException, CantidadCuatrimestresInvalidaException, DatoNumericoInvalidoException, DuplicadoException {
        return carreraService.crearCarrera(carreraDTO);
    }

    @DeleteMapping("/{idCarrera}")
    public String eliminarCarrera(@PathVariable Integer idCarrera) throws NoEncontradoException {
        return carreraService.eliminarCarrera(idCarrera);
    }

    @PutMapping("/{idCarrera}")
    public Carrera actualizarCarrera(@PathVariable Integer idCarrera, @RequestBody CarreraDTO carreraDTO) throws CantidadCuatrimestresInvalidaException, NombreInvalidoException, NoEncontradoException, DatoNumericoInvalidoException, DuplicadoException {
        return carreraService.actualizarCarrera(idCarrera, carreraDTO);
    }
}
