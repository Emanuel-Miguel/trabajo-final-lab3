package ar.utn.frbb.tup.persistence.Implementation;

import ar.utn.frbb.tup.model.Materia;
import ar.utn.frbb.tup.persistence.MateriaDao;
import ar.utn.frbb.tup.persistence.exception.NoEncontradoException;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MateriaDaoMemoryImpl implements MateriaDao {
    private Map<Integer, Materia> repositorioMaterias = new HashMap<>();

    @Override
    public Materia saveMateria(Materia materia) {
        repositorioMaterias.put(materia.getIdMateria(), materia);
        System.out.println(repositorioMaterias);
        return materia;
    }

    @Override
    public String deleteMateria(Integer idMateria) throws NoEncontradoException {
        if (!repositorioMaterias.containsKey(idMateria)) {
            throw new NoEncontradoException("El id de la materia no existe en el repositorio");
        }
        repositorioMaterias.remove(idMateria);
        return "Eliminado con Exito";
    }

    @Override
    public Materia getMateria(Integer idMateria) {
        return repositorioMaterias.get(idMateria);
    }

    @Override
    public boolean existeMateriaPorId(Integer idMateria) {
        return repositorioMaterias.containsKey(idMateria);
    }
    @Override
    public List<Materia> findMateriasByName(String nombre) {
        List<Materia> result = new ArrayList<>();
        for (Materia materia : repositorioMaterias.values()) {
            if (materia.getNombre().contains(nombre)) {
                result.add(materia);
            }
        }
        return result;
    }

    @Override
    public List<Materia> findAllOrdered(String order) {
        List<Materia> result = new ArrayList<>(repositorioMaterias.values());
        Comparator<Materia> comparator;

        switch (order) {
            case "nombre_asc":
                comparator = new Comparator<Materia>() {
                    @Override
                    public int compare(Materia m1, Materia m2) {
                        return m1.getNombre().compareTo(m2.getNombre());
                    }
                };
                break;
            case "nombre_desc":
                comparator = new Comparator<Materia>() {
                    @Override
                    public int compare(Materia m1, Materia m2) {
                        return m2.getNombre().compareTo(m1.getNombre());
                    }
                };
                break;
            case "codigo_asc":
                comparator = new Comparator<Materia>() {
                    @Override
                    public int compare(Materia m1, Materia m2) {
                        return m1.getIdMateria().compareTo(m2.getIdMateria());
                    }
                };
                break;
            case "codigo_desc":
                comparator = new Comparator<Materia>() {
                    @Override
                    public int compare(Materia m1, Materia m2) {
                        return m2.getIdMateria().compareTo(m1.getIdMateria());
                    }
                };
                break;
            default:
                throw new IllegalArgumentException("Orden no válido: " + order);
        }

        Collections.sort(result, comparator);
        return result;
    }

}
