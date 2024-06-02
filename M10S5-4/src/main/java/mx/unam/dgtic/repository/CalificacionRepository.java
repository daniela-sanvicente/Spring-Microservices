package mx.unam.dgtic.repository;

import mx.unam.dgtic.model.Calificacion;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CalificacionRepository extends CrudRepository<Calificacion, Integer> {
	List<Calificacion> findByMateria(String materia);
	List<Calificacion> findByCalificacion(int calificacion);

	List<Calificacion> findByAlumnoNombre(String nombre);
	List<Calificacion> findByAlumnoPaterno(String paterno);

}
