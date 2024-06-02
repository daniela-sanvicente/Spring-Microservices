package mx.unam.dgtic.repository;

import mx.unam.dgtic.model.Alumno;
import mx.unam.dgtic.model.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EstadoRepository extends JpaRepository<Estado, Integer> {

	Estado findByEstado(String estado);

	// Consulta derivada para buscar un estado por 'like'
	List<Estado> findByEstadoContaining(String estado);
	List<Estado> findByAbreviaturaContaining(String abreviatura);

	//Ver el Estado Buscando por Nombre de Alumno LIKE
	@Query(value = "SELECT DISTINCT e.* FROM Estados e " +
			"JOIN Alumnos a ON e.id_estado = a.id_estado " +
			"WHERE a.nombre LIKE %:nombre%", nativeQuery = true)
	List<Estado> findEstadosByAlumnoNombreLike(@Param("nombre") String nombre);

	//Estados sin alumnos
	@Query("SELECT e FROM Estado e WHERE NOT EXISTS (SELECT a FROM Alumno a WHERE a.estado.idEstado = e.idEstado)")
	List<Estado> findEstadosSinAlumnos();

	//Buscar estado con al menos n alumnos
	@Query("SELECT e FROM Estado e WHERE SIZE(e.alumnos) > :minimo")
	List<Estado> findEstadosConMinimoAlumnos(@Param("minimo") int minimo);

}

