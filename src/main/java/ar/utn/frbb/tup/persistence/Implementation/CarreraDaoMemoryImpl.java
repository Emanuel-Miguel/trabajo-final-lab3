package ar.utn.frbb.tup.persistence.Implementation;

import ar.utn.frbb.tup.model.Carrera;
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
        repositorioCarreras.put(carrera.getCodigoCarrera(), carrera);
        System.out.println(repositorioCarreras);
        return carrera;
    }

    @Override
    public String deleteCarrera(Integer codigoCarrera) throws NoEncontradoException {
        if (!repositorioCarreras.containsKey(codigoCarrera)) {
            throw new NoEncontradoException("El código de carrera no existe en el repositorio");
        }
        repositorioCarreras.remove(codigoCarrera);
        return "Eliminado con Exito";
    }

    @Override
    public Carrera getCarrera(Integer codigoCarrera) {
        return repositorioCarreras.get(codigoCarrera);
    }

}
