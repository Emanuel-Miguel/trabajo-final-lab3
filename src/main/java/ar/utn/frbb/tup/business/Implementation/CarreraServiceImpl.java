package ar.utn.frbb.tup.business.Implementation;

import ar.utn.frbb.tup.business.CarreraService;
import ar.utn.frbb.tup.business.exception.*;
import ar.utn.frbb.tup.model.Carrera;
import ar.utn.frbb.tup.model.Materia;
import ar.utn.frbb.tup.model.dto.CarreraDTO;
import ar.utn.frbb.tup.persistence.CarreraDao;
import ar.utn.frbb.tup.persistence.exception.NoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CarreraServiceImpl implements CarreraService {
    @Autowired
    CarreraDao carreraDao;
    @Override
    public Carrera crearCarrera(CarreraDTO carreraDTO) throws NombreInvalidoException, CantidadCuatrimestresInvalidaException, DatoNumericoInvalidoException {
        validarCarreraDTO(carreraDTO);

        Carrera carrera = new Carrera();
        carrera.setNombre(carreraDTO.getNombre());
        carrera.setCodigoCarrera(carreraDTO.getCodigoCarrera());
        carrera.setDepartamento(carreraDTO.getDepartamento());
        carrera.setCantidadCuatrimestres(carreraDTO.getCantidadCuatrimestres());
        List<Materia> lista = new ArrayList<>();
        carrera.setMateriasList(lista);

        return carreraDao.saveCarrera(carrera);
    }

    @Override
    public String eliminarCarrera(Integer codigoCarrera) throws NoEncontradoException {

        return carreraDao.deleteCarrera(codigoCarrera);
    }

    @Override
    public Carrera actualizarCarrera(Integer codigoCarrera, CarreraDTO carreraDTO) throws NombreInvalidoException, CantidadCuatrimestresInvalidaException, NoEncontradoException, DatoNumericoInvalidoException {
        validarCarreraDTO(carreraDTO);

        Carrera carreraExistente = carreraDao.getCarrera(codigoCarrera);
        if (carreraExistente == null) {
            throw new NoEncontradoException("La carrera con el código proporcionado no existe");
        }

        carreraExistente.setNombre(carreraDTO.getNombre());
        carreraExistente.setCodigoCarrera(carreraDTO.getCodigoCarrera());
        carreraExistente.setDepartamento(carreraDTO.getDepartamento());
        carreraExistente.setCantidadCuatrimestres(carreraDTO.getCantidadCuatrimestres());
        return carreraDao.saveCarrera(carreraExistente);
    }

    private void validarCarreraDTO(CarreraDTO carreraDTO) throws NombreInvalidoException, DatoNumericoInvalidoException, CantidadCuatrimestresInvalidaException {

        if (carreraDTO.getNombre() == null || carreraDTO.getNombre().isEmpty() || carreraDTO.getNombre().length() <= 5 || carreraDTO.getNombre().length() > 70) {
            throw new NombreInvalidoException("El nombre debe tener más de 5 caracteres y no exceder los 70 caracteres");
        }

        if (carreraDTO.getCodigoCarrera() == null || carreraDTO.getCodigoCarrera() <= 0) {
            throw new DatoNumericoInvalidoException("El código de la carrera no puede estar vacío y debe ser un número positivo");
        }

        if (carreraDTO.getDepartamento() == null || carreraDTO.getDepartamento() <= 0) {
            throw new DatoNumericoInvalidoException("El departamento no puede estar vacío y debe ser un número positivo");
        }

        if (carreraDTO.getCantidadCuatrimestres() <= 1 || carreraDTO.getCantidadCuatrimestres() >= 14) {
            throw new CantidadCuatrimestresInvalidaException("La cantidad de cuatrimestres debe ser mayor que 0 y hasta 14");
        }
    }

}
