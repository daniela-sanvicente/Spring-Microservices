package mx.unam.dgtic.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "Calificaciones")
public class Calificacion {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String materia;
	private int calificacion;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "alumnos_matricula")
	private Alumno alumno;

	public Calificacion() {
	}

	public Calificacion(int id) {
		this.id = id;
	}

	public Calificacion(String materia, int calificacion) {
		this.materia = materia;
		this.calificacion = calificacion;
	}

	public Calificacion(int id, String materia, int calificacion) {
		this.id = id;
		this.materia = materia;
		this.calificacion = calificacion;
	}

	public Calificacion(int id, String materia, int calificacion, Alumno alumno) {
		this.id = id;
		this.materia = materia;
		this.calificacion = calificacion;
		this.alumno = alumno;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public Alumno getAlumno() {
		return alumno;
	}

	public void setAlumno(Alumno alumno) {
		this.alumno = alumno;
	}

	@Override
	public String toString() {
		return "Calificacion{" +
				"id=" + id +
				", materia='" + materia + '\'' +
				", calificacion=" + calificacion +
				", alumno=" + alumno +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Calificacion that = (Calificacion) o;
		return id == that.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
