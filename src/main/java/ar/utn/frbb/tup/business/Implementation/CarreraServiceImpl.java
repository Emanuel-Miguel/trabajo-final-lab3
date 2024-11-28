package ar.utn.frbb.tup.business.Implementation;

import ar.utn.frbb.tup.business.CarreraService;
import ar.utn.frbb.tup.business.exception.*;
import ar.utn.frbb.tup.model.Carrera;
import ar.utn.frbb.tup.model.Materia;
import ar.utn.frbb.tup.model.dto.CarreraDTO;
import ar.utn.frbb.tup.persistence.CarreraDao;
import ar.utn.frbb.tup.persistence.exception.DuplicadoException;
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
    public Carrera crearCarrera(CarreraDTO carreraDTO) throws NombreInvalidoException, CantidadCuatrimestresInvalidaException, DatoNumericoInvalidoException, DuplicadoException {
        validarCarreraDTO(carreraDTO);

        if (carreraDao.existeCarreraPorId(carreraDTO.getIdCarrera())) {
            throw new DuplicadoException("Ya existe una carrera con el mismo id");
        }

        Carrera carrera = new Carrera();
        carrera.setNombre(carreraDTO.getNombre());
        carrera.setIdCarrera(carreraDTO.getIdCarrera());
        carrera.setDepartamento(carreraDTO.getDepartamento());
        carrera.setCantidadCuatrimestres(carreraDTO.getCantidadCuatrimestres());
        List<Materia> lista = new ArrayList<>();
        carrera.setMateriasList(lista);

        return carreraDao.saveCarrera(carrera);
    }

    @Override
    public String eliminarCarrera(Integer idCarrera) throws NoEncontradoException {

        return carreraDao.deleteCarrera(idCarrera);
    }

    @Override
    public Carrera actualizarCarrera(Integer idCarrera, CarreraDTO carreraDTO) throws NombreInvalidoException, CantidadCuatrimestresInvalidaException, NoEncontradoException, DatoNumericoInvalidoException, DuplicadoException {
        validarCarreraDTO(carreraDTO);

        Carrera carreraExistente = carreraDao.getCarrera(idCarrera);
        if (carreraExistente == null) {
            throw new NoEncontradoException("El id de la carrera " + idCarrera + " no existe");
        }
        if (carreraDao.existeCarreraPorId(carreraDTO.getIdCarrera())) {
            throw new DuplicadoException("Ya existe una carrera con el mismo id");
        }

        carreraExistente.setNombre(carreraDTO.getNombre());
        carreraExistente.setIdCarrera(carreraDTO.getIdCarrera());
        carreraExistente.setDepartamento(carreraDTO.getDepartamento());
        carreraExistente.setCantidadCuatrimestres(carreraDTO.getCantidadCuatrimestres());
        return carreraDao.saveCarrera(carreraExistente);
    }

    @Override
    public Carrera getCarrera(Integer idCarrera) {
        return carreraDao.getCarrera(idCarrera);
    }

    public void agregarMateria(Materia materia, Integer idCarrera) {
        carreraDao.agregarMateria(materia, idCarrera);
    }

    private void validarCarreraDTO(CarreraDTO carreraDTO) throws NombreInvalidoException, DatoNumericoInvalidoException, CantidadCuatrimestresInvalidaException {

        if (carreraDTO.getNombre() == null || carreraDTO.getNombre().isEmpty() || carreraDTO.getNombre().length() <= 5 || carreraDTO.getNombre().length() > 70) {
            throw new NombreInvalidoException("El nombre no puede estar vacio, debe tener más de 5 caracteres y no exceder los 70 caracteres");
        }

        if (!carreraDTO.getNombre().matches("[a-zA-Z ]+")) {
            throw new NombreInvalidoException("El nombre contiene caracteres inválidos");
        }

        if (carreraDTO.getIdCarrera() == null || carreraDTO.getIdCarrera() <= 0) {
            throw new DatoNumericoInvalidoException("El id de la carrera no puede estar vacío y debe ser un número positivo");
        }

        if (carreraDTO.getDepartamento() == null || carreraDTO.getDepartamento() <= 0) {
            throw new DatoNumericoInvalidoException("El departamento no puede estar vacío y debe ser un número positivo");
        }

        if (carreraDTO.getCantidadCuatrimestres() == null || carreraDTO.getCantidadCuatrimestres() <= 1 || carreraDTO.getCantidadCuatrimestres() >= 14) {
            throw new CantidadCuatrimestresInvalidaException("La cantidad de cuatrimestres no puede estar vacia, debe ser mayor que 0 y hasta 14");
        }
    }

}
