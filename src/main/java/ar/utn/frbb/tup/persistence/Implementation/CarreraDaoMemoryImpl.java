package ar.utn.frbb.tup.persistence.Implementation;

import ar.utn.frbb.tup.model.Carrera;
import ar.utn.frbb.tup.model.Materia;
import ar.utn.frbb.tup.persistence.CarreraDao;
import ar.utn.frbb.tup.persistence.exception.NoEncontradoException;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class CarreraDaoMemoryImpl implements CarreraDao {
    private Map<Integer, Carrera> repositorioCarreras = new HashMap<>();

    @Override
    public Carrera saveCarrera(Carrera carrera) {
        repositorioCarreras.put(carrera.getIdCarrera(), carrera);
        System.out.println(repositorioCarreras);
        return carrera;
    }

    @Override
    public String deleteCarrera(Integer idCarrera) throws NoEncontradoException {
        if (!repositorioCarreras.containsKey(idCarrera)) {
            throw new NoEncontradoException("El id de carrera no existe en el repositorio");
        }
        repositorioCarreras.remove(idCarrera);
        return "Eliminado con Exito";
    }

    @Override
    public Carrera getCarrera(Integer idCarrera) {
        return repositorioCarreras.get(idCarrera);
    }

    @Override
    public void agregarMateria(Materia materia, Integer idCarrera) {
        repositorioCarreras.get(idCarrera).getMateriasList().add(materia);
    }

    @Override
    public boolean existeCarreraPorId(Integer idCarrera) {
        return repositorioCarreras.containsKey(idCarrera);
    }

}
