package ar.utn.frbb.tup.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("carrera")
public class CarreraController {

    @PostMapping("/")
    public String crearCarrera () {
        return "safa";
    }

}
