package ar.utn.frbb.tup.business.Implementation;

import ar.utn.frbb.tup.business.CarreraService;
import ar.utn.frbb.tup.business.MateriaService;
import ar.utn.frbb.tup.business.exception.DatoNumericoInvalidoException;
import ar.utn.frbb.tup.business.exception.NombreInvalidoException;
import ar.utn.frbb.tup.model.Carrera;
import ar.utn.frbb.tup.model.Materia;
import ar.utn.frbb.tup.model.dto.MateriaDTO;
import ar.utn.frbb.tup.persistence.MateriaDao;
import ar.utn.frbb.tup.persistence.exception.DuplicadoException;
import ar.utn.frbb.tup.persistence.exception.NoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MateriaServiceImpl implements MateriaService {
    @Autowired
    MateriaDao materiaDao;

    @Autowired
    CarreraService carreraService;
    @Override
    public Materia crearMateria(MateriaDTO materiaDTO) throws NombreInvalidoException, DatoNumericoInvalidoException, DuplicadoException, NoEncontradoException {
        validarMateriaDTO(materiaDTO);

        if (materiaDao.existeMateriaPorId(materiaDTO.getIdMateria())) {
            throw new DuplicadoException("Ya existe una materia con el mismo id");
        }

        Carrera carrera = carreraService.getCarrera(materiaDTO.getIdCarrera());
        if (carrera == null) {
            throw new NoEncontradoException("La carrera con el id " + materiaDTO.getIdCarrera() + " no existe");
        }

        Materia materia = new Materia();
        materia.setIdMateria(materiaDTO.getIdMateria());
        materia.setNombre(materiaDTO.getNombre());
        materia.setAnio(materiaDTO.getAnio());
        materia.setHoras(materiaDTO.getHoras());
        materia.setProfesor(materiaDTO.getProfesor());

        materia.setCarrera(carrera);
        carreraService.agregarMateria(materia, carrera.getIdCarrera());

        return materiaDao.saveMateria(materia);
    }

    @Override
    public String eliminarMateria(Integer idMateria) throws NoEncontradoException {

        return materiaDao.deleteMateria(idMateria);
    }

    @Override
    public Materia actualizarMateria(Integer idMateria, MateriaDTO materiaDTO) throws NombreInvalidoException, DatoNumericoInvalidoException, NoEncontradoException, DuplicadoException {
        validarMateriaDTO(materiaDTO);

        Materia materiaExistente = materiaDao.getMateria(idMateria);
        if (materiaExistente == null) {
            throw new NoEncontradoException("La materia con el id " + idMateria + " no existe");
        }

        if (materiaDao.existeMateriaPorId(materiaDTO.getIdMateria())) {
            throw new DuplicadoException("Ya existe una materia con el mismo id");
        }

        materiaExistente.setIdMateria(materiaDTO.getIdMateria());
        materiaExistente.setNombre(materiaDTO.getNombre());
        materiaExistente.setAnio(materiaDTO.getAnio());
        materiaExistente.setHoras(materiaDTO.getHoras());
        materiaExistente.setProfesor(materiaDTO.getProfesor());

        return materiaDao.saveMateria(materiaExistente);
    }

    @Override
    public List<Materia> obtenerMateriasPorNombre(String nombre) throws NoEncontradoException {
        List<Materia> materias = materiaDao.findMateriasByName(nombre);
        if (materias.isEmpty()) {
            throw new NoEncontradoException("No se encontraron materias con el nombre: " + nombre);
        }
        return materias;
    }

    @Override
    public List<Materia> listarMateriasOrdenadas(String order) {
        return materiaDao.findAllOrdered(order);
    }

    private void validarMateriaDTO(MateriaDTO materiaDTO) throws NombreInvalidoException, DatoNumericoInvalidoException {

        if (materiaDTO.getIdMateria() == null || materiaDTO.getIdMateria() <= 0) {
            throw new DatoNumericoInvalidoException("El id de la materia no puede estar vacío y debe ser un número positivo");
        }

        if (materiaDTO.getNombre() == null || materiaDTO.getNombre().isEmpty() || materiaDTO.getNombre().length() <= 5 || materiaDTO.getNombre().length() > 70) {
            throw new NombreInvalidoException("El nombre de la materia no puede estar vacio, debe tener más de 5 caracteres y no exceder los 70 caracteres");
        }

        if (!materiaDTO.getNombre().matches("[a-zA-Z ]+")) {
            throw new NombreInvalidoException("El nombre contiene caracteres inválidos");
        }

        if (materiaDTO.getAnio() == null || materiaDTO.getAnio() <= 0) {
            throw new DatoNumericoInvalidoException("El anio no puede estar vacío y debe ser un número positivo");
        }

        if (materiaDTO.getHoras() == null || materiaDTO.getHoras() <= 0) {
            throw new DatoNumericoInvalidoException("La cantidad de horas no pueden estar vacías y debe ser un número positivo");
        }

        if (materiaDTO.getProfesor().getNombre() == null || materiaDTO.getProfesor().getNombre().isEmpty() ||materiaDTO.getProfesor().getNombre().length() <= 3 || materiaDTO.getProfesor().getNombre().length() > 50) {
            throw new NombreInvalidoException("El nombre del profesor no puede estar vacio, debe tener más de 3 caracteres y no exceder los 50 caracteres");
        }

        if (materiaDTO.getProfesor().getApellido() == null || materiaDTO.getProfesor().getApellido().isEmpty() ||materiaDTO.getProfesor().getApellido().length() <= 3 || materiaDTO.getProfesor().getApellido().length() > 50) {
            throw new NombreInvalidoException("El apellido del profesor no puede estar vacio, debe tener más de 3 caracteres y no exceder los 50 caracteres");
        }

        if (materiaDTO.getProfesor().getTitulo() == null || materiaDTO.getProfesor().getTitulo().isEmpty() ||materiaDTO.getProfesor().getTitulo().length() <= 6 || materiaDTO.getProfesor().getTitulo().length() > 60) {
            throw new NombreInvalidoException("El titulo del profesor no puede estar vacio, debe tener más de 6 caracteres y no exceder los 60 caracteres");
        }
    }
}
