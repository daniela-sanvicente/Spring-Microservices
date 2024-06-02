package mx.unam.dgtic.controller;

import mx.unam.dgtic.model.Estado;
import mx.unam.dgtic.repository.AlumnoRepository;
import mx.unam.dgtic.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

//Daniela Alejandra Sanvicente Enríquez
@RestController
@RequestMapping("api/estados")
public class EstadoController {

    @Autowired
    EstadoRepository repositorioEstado;

    @Autowired
    AlumnoRepository repositorioAlumno;

    @GetMapping()
    public List<Estado> getAllEstados(){
        return repositorioEstado.findAll();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Estado> getEstadoById(@PathVariable("id") Integer id){
        Optional<Estado> estado= repositorioEstado.findById(id);
        if(estado.isPresent()){
            return new ResponseEntity<>(estado.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "xml", produces = MediaType.APPLICATION_XML_VALUE)
    public List<Estado> getAllEstadosXml(){
        return repositorioEstado.findAll();
    }
    //produces, que nos va a dejar salida en texto o xml
    @GetMapping(value = "xml/{id}",produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<Estado> getEstadoByIdXml(@PathVariable("id") Integer id){
        Optional<Estado> estado= repositorioEstado.findById(id);
        if(estado.isPresent()){
            return new ResponseEntity<>(estado.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    //consumes, que va a consumir
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Estado saveEstado(@RequestBody Estado estado){
        return repositorioEstado.save(estado);
    }

    @PostMapping(value = "xml",
            consumes = MediaType.APPLICATION_XML_VALUE,
            produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Estado saveEstadoXml(@RequestBody Estado estado){
        return repositorioEstado.save(estado);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Estado> updateEstado(@PathVariable("id") Integer id,
                                               @RequestBody Estado estado){
        Optional<Estado> optional=repositorioEstado.findById(id);
        if (optional.isPresent()){
            System.out.println(estado.getEstado());
            repositorioEstado.save(estado);
            return new ResponseEntity<>(estado, HttpStatus.OK);
        }else {
            throw new ResourceNotFoundException(String.format("Estado %s no localizado", id)
            );
        }
    }

    @PutMapping(value = "xml/{id}",
            consumes = MediaType.APPLICATION_XML_VALUE,
            produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Estado> updateEstadoXml(@PathVariable("id") Integer id,
                                                  @RequestBody Estado estado){
        Optional<Estado> optional=repositorioEstado.findById(id);
        if (optional.isPresent()){
            System.out.println(estado.getEstado());
            repositorioEstado.save(estado);
            return new ResponseEntity<>(estado, HttpStatus.OK);
        }else {
            throw new ResourceNotFoundException(String.format("Estado %s no localizado", id)
            );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Estado> deleteEstadoById(@PathVariable Integer id){
        Optional<Estado> optional = repositorioEstado.findById(id);
        if(optional.isPresent()){
            repositorioEstado.deleteById(id);
            return new ResponseEntity<Estado>(HttpStatus.OK);
        }else{
            throw new ResourceNotFoundException(
                    String.format("Estado %s no localizado", id)
            );
        }
    }

    @DeleteMapping(value = "xml/{id}", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<Estado> deleteEstadoByIdXml(@PathVariable Integer id){
        Optional<Estado> optional=repositorioEstado.findById(id);
        if(optional.isPresent()){
            repositorioEstado.deleteById(id);
            return new ResponseEntity<Estado>(HttpStatus.OK);
        }else{
            throw new ResourceNotFoundException(
                    String.format("Estado %s no localizado", id)
            );
        }
    }

    @GetMapping(value = "/parametros")
    public ResponseEntity<?> buscarPorParametrosEstados(
            @RequestParam Map<String, String> parametros){
        List<Estado> estados = null;
            if (!parametros.isEmpty()){
                    if (parametros.containsKey("estado")){
                        estados = repositorioEstado.findByEstadoContaining(parametros.get("estado"));
                    }
                    if (parametros.containsKey("abreviatura")){
                        estados = repositorioEstado.findByAbreviaturaContaining(parametros.get("abreviatura"));
                    }

                if (estados.isEmpty()){
                    return ResponseEntity.noContent().build();
                    //return ResponseEntity.ok().body("No se encontraron estados con los parametros especificados");
                }
            }else {
                if (estados == null || estados.isEmpty()){
                    estados = repositorioEstado.findAll();
                }
            }
            return ResponseEntity.ok(estados);
    }


}
