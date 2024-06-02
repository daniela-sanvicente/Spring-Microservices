package mx.unam.dgtic.service;

import mx.unam.dgtic.model.Alumno;
import mx.unam.dgtic.repository.AlumnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlumnoService implements IAlumnoService{

    @Autowired
    private AlumnoRepository repositorioAlumno;

    @Override
    public List<Alumno> getAlumnosList(int page, int size, String sortDir, String sort) {
        PageRequest pageRequest =PageRequest.of(page, size, Sort.Direction.fromString(sortDir), sort);
        Page<Alumno> alumnos = repositorioAlumno.findAll(pageRequest);
        return alumnos.getContent();
    }

    @Override
    public void updateAlumno(Alumno alumno) {
        repositorioAlumno.save(alumno);

    }

    @Override
    public Alumno createAlumno(Alumno alumno) {
        return repositorioAlumno.save(alumno);
    }

    @Override
    public void deleteAlumno(String matricula) {
        repositorioAlumno.deleteById(matricula);

    }

    @Override
    public Optional<Alumno> getAlumnoById(String matricula) {
        return repositorioAlumno.findById(matricula);
    }

    @Override
    public List<Alumno> findAlumnosByEstado(String estado) {
        return repositorioAlumno.findByEstadoEstado(estado);
    }

    @Override
    public long countAlumnos() {
        return repositorioAlumno.count();
    }
}
