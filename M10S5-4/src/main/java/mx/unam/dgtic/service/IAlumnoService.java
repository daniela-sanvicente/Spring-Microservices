package mx.unam.dgtic.service;

import mx.unam.dgtic.model.Alumno;

import java.util.List;
import java.util.Optional;

public interface IAlumnoService {
    List<Alumno> getAlumnosList(int page, int size, String sortDir, String sort);
    void updateAlumno(Alumno alumno);
    Alumno createAlumno(Alumno alumno);
    void deleteAlumno(String matricula);
    Optional<Alumno> getAlumnoById(String matricula);
    List<Alumno> findAlumnosByEstado(String estado);
    long countAlumnos();

}
