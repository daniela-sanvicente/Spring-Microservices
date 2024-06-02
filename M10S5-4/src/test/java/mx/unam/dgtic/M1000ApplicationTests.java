package mx.unam.dgtic;

import mx.unam.dgtic.model.Alumno;
import mx.unam.dgtic.repository.AlumnoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@SpringBootTest
		//(exclude = {SecurityAutoConfiguration.class})
class M1000ApplicationTests {

	final String ALUMNO = "OMAR MENDOZA GONZALEZ";
	private static final int PAGE_SIZE = 10;

	@Autowired
	AlumnoRepository repositorioAlumno;

	@Test
	void buscarTodosPaginaTest(){
		System.out.println(ALUMNO);
		System.out.println("Buscar todos los Alumnos Paginado ");

		Pageable pagina = PageRequest.of(0, PAGE_SIZE, Sort.by("nombre").descending());

		Page<Alumno> alumnoPage = repositorioAlumno.findAll(pagina);
		System.out.println("Pagina 1 de " + alumnoPage.getTotalPages());
		alumnoPage.forEach(System.out::println);

		pagina = PageRequest.of(1, PAGE_SIZE, Sort.by("nombre").descending());

		alumnoPage = repositorioAlumno.findAll(pagina);
		System.out.println("Pagina 2 de " + alumnoPage.getTotalPages());
		alumnoPage.forEach(System.out::println);

	}

}
