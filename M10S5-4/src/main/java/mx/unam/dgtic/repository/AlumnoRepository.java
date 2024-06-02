package mx.unam.dgtic.repository;

import mx.unam.dgtic.dto.AlumnoCalificacionDTO;
import mx.unam.dgtic.model.Alumno;
import mx.unam.dgtic.model.Estado;
import mx.unam.dgtic.model.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface AlumnoRepository extends JpaRepository<Alumno, String> {
	//Metodos de consultas derivadas
	List<Alumno> findByNombre(String nombre);
	//List<Alumno> getByNombre(String nombre);
	//List<Alumno> searchByNombre(String nombre);
	//List<Alumno> streamByNombre(String nombre);
	//List<Alumno> readByNombre(String nombre);
	//List<Alumno> queryByNombre(String nombre);

	List<Alumno> findByNombreNot(String nombre);
	//List<Alumno> getByNombreNot(String nombre);
	//List<Alumno> searchByNombreNot(String nombre);
	//List<Alumno> streamByNombreNot(String nombre);
	//List<Alumno> readByNombreNot(String nombre);
	//List<Alumno> queryByNombreNot(String nombre);

	//Contar
	long countByNombre(String nombre);
	long countByNombreNot(String nombre);

	List<Alumno> getByPaterno(String paterno);
	List<Alumno> getByEstatura(double estatura);
	List<Alumno> getByFnac(Date fnac);

	//Usar NULL
	List<Alumno> streamByPaternoIsNull();
	List<Alumno> streamByPaternoIsNotNull();

	long countByPaternoIsNull();
	long countByPaternoIsNotNull();

	//Combinar campos con AND / OR
	List<Alumno> queryByNombreAndPaterno(String nombre, String paterno);
	List<Alumno> queryByNombreOrPaterno(String nombre, String paterno);
	List<Alumno> queryByNombreOrPaternoNull(String nombre);
	List<Alumno> queryByNombreOrPaternoAndEstatura(String nombre, String paterno, double estatura);

	long countByNombreAndPaterno(String nombre, String paterno);
	long countByNombreOrPaterno(String nombre, String paterno);
	long countByNombreOrPaternoNull(String nombre);
	long countByNombreOrPaternoAndEstatura(String nombre, String paterno, double estatura);

	boolean existsByNombreAndPaterno(String nombre, String paterno);

	//Mayor que, Menor que
	List<Alumno> findByFnacBefore(Date fecha);
	List<Alumno> findByFnacAfter(Date fecha);

	List<Alumno> findByEstaturaLessThan(double estatura);
	List<Alumno> findByEstaturaLessThanEqual(double estatura);

	List<Alumno> findByEstaturaGreaterThan(double estatura);
	List<Alumno> findByEstaturaGreaterThanEqual(double estatura);

	//Patrones
	List<Alumno> findByPaternoStartingWith(String prefijo);
	List<Alumno> findByPaternoContaining(String contiene);
	List<Alumno> findByPaternoEndingWith(String prefijo);

	List<Alumno> findByNombreStartingWith(String prefijo);
	List<Alumno> findByNombreContaining(String contiene);
	List<Alumno> findByNombreEndingWith(String prefijo);

	//Consuta derivada para listar alumnos por estado
	List<Alumno> findByEstado(Estado estado);
	long countByEstado(Estado estado);

	List<Alumno> findByEstadoEstado(String estado);

	List<Alumno> findByEstadoAbreviatura(String estado);

	@Query("SELECT g FROM Alumno a JOIN a.grupos g WHERE a.matricula = :matricula")
	List<Grupo> findGruposByMatricula(@Param("matricula") String matricula);

	List<Alumno> buscarTodosConCalificaciones();

	//DTO
	@Query("SELECT new mx.unam.dgtic.dto.AlumnoCalificacionDTO("
			+ "a.matricula, a.nombre, a.paterno, c.materia, c.calificacion) "
			+ "FROM Alumno a JOIN a.calificaciones c")
	List<AlumnoCalificacionDTO> buscarAlumnoCalificacionDto();

	//Filtrar
	@Query("SELECT new mx.unam.dgtic.dto.AlumnoCalificacionDTO("
			+ "a.matricula, a.nombre, a.paterno, c.materia, c.calificacion) "
			+ "FROM Alumno a JOIN a.calificaciones c "
			+ "WHERE c.calificacion > :umbral"
	)
	List<AlumnoCalificacionDTO> buscarAlumnoCalificacionMayorQueDto(@Param("umbral") int umbral);

	//Agrupaciones
	@Query("SELECT new mx.unam.dgtic.dto.AlumnoCalificacionDTO("
			+ "a.matricula, a.nombre, a.paterno, CAST(AVG(c.calificacion) AS int)) "
			+ "FROM Alumno a JOIN a.calificaciones c "
			+ "GROUP BY a.matricula, a.nombre, a.paterno"
	)
	List<AlumnoCalificacionDTO> buscarPromedioCalificacionPorAlumnoDto();

	@Query("SELECT new mx.unam.dgtic.dto.AlumnoCalificacionDTO("
			+ "c.materia, CAST(AVG(c.calificacion) AS int)) "
			+ "FROM Alumno a JOIN a.calificaciones c "
			+ "GROUP BY c.materia"
	)
	List<AlumnoCalificacionDTO> buscarPromedioCalificacionPorMateriaDto();

	//Queries de modificacion con @Modifying
	@Modifying
	@Query(value = "INSERT INTO Alumnos(matricula, nombre, paterno, fnac, estatura) "
			+ "VALUES(:matricula, :nombre, :paterno, :fnac, :estatura)",
			nativeQuery = true)
	void insertAlumno(@Param("matricula") String matricula, @Param("nombre") String nombre,
					  @Param("paterno") String paterno,
					  @Param("fnac") Date fnac, @Param("estatura") Double estatura);

	@Modifying
	@Query("UPDATE Alumno a SET a.estatura = :estatura WHERE a.nombre = :nombre")
	int updateAlumnoSetEstaturaByNombre(@Param("estatura") Double estatura, @Param("nombre") String nombre);

	@Modifying
	@Query("UPDATE Calificacion c SET c.calificacion = c.calificacion + :extra " +
			"WHERE c.alumno.matricula IN :matriculas " +
			"AND c.calificacion > 5 AND c.calificacion < 10")
	int updateCalificacionByAlumnoMatricula(@Param("extra") int extra, @Param("matriculas") List<String> matriculas);

}
