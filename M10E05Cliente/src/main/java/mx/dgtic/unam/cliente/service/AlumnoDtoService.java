package mx.dgtic.unam.cliente.service;


import mx.dgtic.unam.cliente.model.AlumnoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.List;

@Service
public class AlumnoDtoService {
    @Autowired
    private RestTemplate restTemplate;

    private final String baseUrl = "http://localhost:8080/api/alumnos/servicio";

    public List<AlumnoDto> findAll(int page, int size, String sortDir, String sort){
        String url = baseUrl + "?page=" +page +"&size="+ size+"&sortDir="+sortDir+"&sort="+sort;
        System.out.println("Request url: "+ url);
        ResponseEntity<List<AlumnoDto>> response = restTemplate.exchange(
                url, HttpMethod.GET, null, new ParameterizedTypeReference<List<AlumnoDto>>() {}
                );
        return response.getBody();

    }

    public AlumnoDto findByMatricula(String matricula){
        return  restTemplate.getForObject(baseUrl+"/"+matricula, AlumnoDto.class);
    }

    public void deleteByMatricula(String matricula){
        restTemplate.delete(baseUrl+"/"+matricula);
    }

    public void saveOrUpdate(AlumnoDto alumnoDto){
        if (alumnoDto.getMatricula() !=null){
            restTemplate.put(baseUrl+"/"+alumnoDto.getMatricula(), alumnoDto);
        }else {
            restTemplate.postForLocation(baseUrl, alumnoDto);

        }
    }

}
