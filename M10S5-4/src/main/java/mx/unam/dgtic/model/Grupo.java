package mx.unam.dgtic.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collection;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Table(name = "Grupos")
public class Grupo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_grupo")
	protected int id;
	protected String grupo;

	@ManyToMany(mappedBy = "grupos")
	@JsonIgnoreProperties(value="grupos") //no hago caso a la salida
	private Collection<Alumno> alumnos;


	public Grupo() {
		alumnos = new ArrayList<Alumno>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public Collection<Alumno> getAlumnos() {
		return alumnos;
	}

	public void addAlumno(Alumno alumno) {
		if(!getAlumnos().contains(alumno)){
			getAlumnos().add(alumno);
		}
		if(!alumno.getGrupos().contains(this)){
			alumno.getGrupos().add(this);
		}
	}

	public void removeAlumno(Alumno alumno) {
		if(alumnos.contains(alumno)){
			alumnos.remove(alumno);
			alumno.getGrupos().remove(this);
		}
	}

	@Override
	public String toString() {
		return "Grupo{" +
				"id=" + id +
				", grupo='" + grupo + '\'' +
				", con " + getAlumnos().size() + " alumnos " +
				'}';
	}
}
