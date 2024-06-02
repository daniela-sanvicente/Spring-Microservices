package mx.unam.dgtic.dto;

public class AlumnoCalificacionDTO {
	private String matricula;
	private String nombre;
	private String paterno;
	private String materia;
	private int calificacion;

	public AlumnoCalificacionDTO(String matricula, String nombre, String paterno, String materia, int calificacion) {
		this.matricula = matricula;
		this.nombre = nombre;
		this.paterno = paterno;
		this.materia = materia;
		this.calificacion = calificacion;
	}

	public AlumnoCalificacionDTO(String matricula, String nombre, String paterno, int calificacion) {
		this.matricula = matricula;
		this.nombre = nombre;
		this.paterno = paterno;
		this.calificacion = calificacion;
	}

	public AlumnoCalificacionDTO(String materia, int calificacion) {
		this.materia = materia;
		this.calificacion = calificacion;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPaterno() {
		return paterno;
	}

	public void setPaterno(String paterno) {
		this.paterno = paterno;
	}

	public String getMateria() {
		return materia;
	}

	public void setMateria(String materia) {
		this.materia = materia;
	}

	public int getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(int calificacion) {
		this.calificacion = calificacion;
	}

	@Override
	public String toString() {
		return "AlumnoCalificacionDTO{" +
				"matricula='" + matricula + '\'' +
				", nombre='" + nombre + '\'' +
				", paterno='" + paterno + '\'' +
				", materia='" + materia + '\'' +
				", calificacion=" + calificacion +
				'}';
	}
}
