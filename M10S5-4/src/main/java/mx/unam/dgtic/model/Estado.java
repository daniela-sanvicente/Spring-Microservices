package mx.unam.dgtic.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Estados")
public class Estado {
	@Id
	@Column(name = "id_estado")
	private Integer idEstado;
	private String estado;
	private String abreviatura;

	@JsonManagedReference
	@OneToMany(mappedBy = "estado")
	private Set<Alumno> alumnos = new HashSet<>();

	public Estado() {
	}

	public Estado(int idEstado, String estado, String abreviatura) {
		this.idEstado = idEstado;
		this.estado = estado;
		this.abreviatura = abreviatura;
	}

	public int getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(int idEstado) {
		this.idEstado = idEstado;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getAbreviatura() {
		return abreviatura;
	}

	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}

	public Set<Alumno> getAlumnos() {
		return alumnos;
	}

	public void setAlumnos(Set<Alumno> alumnos) {
		this.alumnos = alumnos;
	}

	@Override
	public String toString() {
		return "Estado{" +
				"idEstado=" + idEstado +
				", estado='" + estado + '\'' +
				", abreviatura='" + abreviatura + '\'' +
				'}';
	}
}
