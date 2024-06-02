package mx.dgtic.unam.cliente.service;

import mx.dgtic.unam.cliente.model.Alumno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class AlumnoService {
    @Autowired
    private RestTemplate restTemplate;

    private final String baseUrl ="http://localhost:8080/api/alumnos";
    //http://localhost:8080/api/alumnos

    public List<Alumno> findAllAlumnos(){
        ResponseEntity<List<Alumno>> response =
                restTemplate.exchange(baseUrl, HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<Alumno>>() {});
        System.out.println(response.getBody());
        return response.getBody();

    }
}
