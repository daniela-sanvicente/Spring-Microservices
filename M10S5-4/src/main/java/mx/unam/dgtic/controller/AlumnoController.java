package mx.unam.dgtic.controller;

import mx.unam.dgtic.exception.ResourceNotFoundException;
import mx.unam.dgtic.model.Alumno;
import mx.unam.dgtic.model.Calificacion;
import mx.unam.dgtic.repository.AlumnoRepository;
import mx.unam.dgtic.repository.CalificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/alumnos")
public class AlumnoController {
    @Autowired
    AlumnoRepository repositorioAlumno;

    @Autowired
    CalificacionRepository repositorioCalificacion;

    @GetMapping()
    public List<Alumno> getAllAlumnos(){
        return repositorioAlumno.findAll();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Alumno> getAlumnoById(@PathVariable("id") String id){
        Optional<Alumno> alumno= repositorioAlumno.findById(id);
        if(alumno.isPresent()){
            return new ResponseEntity<>(alumno.get(), HttpStatus.OK);
        } else {
            //return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            throw new ResourceNotFoundException("Matrícula " + id + " NO localizada");
        }
    }

    @GetMapping(value = "xml", produces = MediaType.APPLICATION_XML_VALUE)
    public List<Alumno> getAllAlumnosXml(){
        return repositorioAlumno.findAll();
    }
    //produces, que nos va a dejar salida en texto o xml
    @GetMapping(value = "xml/{id}",produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<Alumno> getAlumnoByIdXml(@PathVariable("id") String id){
        Optional<Alumno> alumno= repositorioAlumno.findById(id);
        if(alumno.isPresent()){
            return new ResponseEntity<>(alumno.get(), HttpStatus.OK);
        } else {
            //return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            throw new ResourceNotFoundException("Matrícula " + id + " NO localizada");
        }
    }
    //consumes, que va a consumir
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Alumno saveAlumno(@RequestBody Alumno alumno){
        return repositorioAlumno.save(alumno);
    }

    @PostMapping(value = "xml",
            consumes = MediaType.APPLICATION_XML_VALUE,
            produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Alumno saveAlumnoXml(@RequestBody Alumno alumno){
        return repositorioAlumno.save(alumno);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Alumno> updateAlumno(@PathVariable("id") String id,
                                               @RequestBody Alumno alumno){
        Optional<Alumno> optional=repositorioAlumno.findById(id);
        if (optional.isPresent()){
            System.out.println(alumno.getCalificaciones());
            repositorioAlumno.save(alumno);
            for (Calificacion c:alumno.getCalificaciones()){
                c.setAlumno(alumno);
                repositorioCalificacion.save(c);
            }
            return new ResponseEntity<>(alumno, HttpStatus.OK);
        }else {
            throw new ResourceNotFoundException(String.format("Matricula %s no localizada", id)
            );
        }
    }

    @PutMapping(value = "xml/{id}",
    consumes = MediaType.APPLICATION_XML_VALUE,
    produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Alumno> updateAlumnoXml(@PathVariable("id") String id,
                                               @RequestBody Alumno alumno){
        Optional<Alumno> optional=repositorioAlumno.findById(id);
        if (optional.isPresent()){
            System.out.println(alumno.getCalificaciones());
            repositorioAlumno.save(alumno);
            for (Calificacion c:alumno.getCalificaciones()){
                c.setAlumno(alumno);
                repositorioCalificacion.save(c);
            }
            return new ResponseEntity<>(alumno, HttpStatus.OK);
        }else {
            throw new ResourceNotFoundException(String.format("Matricula %s no localizada", id)
            );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Alumno> deleteAlumnoById(@PathVariable String id){
        Optional<Alumno> optional = repositorioAlumno.findById(id);
        if(optional.isPresent()){
            repositorioAlumno.deleteById(id);
            return new ResponseEntity<Alumno>(HttpStatus.OK);
        }else{
            throw new ResourceNotFoundException(
                    String.format("Matricula %s no localizada", id)
            );
        }
    }

    @DeleteMapping(value = "xml/{id}", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<Alumno> deleteAlumnoByIdXml(@PathVariable String id){
        Optional<Alumno> optional=repositorioAlumno.findById(id);
        if(optional.isPresent()){
            repositorioAlumno.deleteById(id);
            return new ResponseEntity<Alumno>(HttpStatus.OK);
        }else{
            throw new ResourceNotFoundException(
                    String.format("Matricula %s no localizada", id)
            );
        }
    }

    @GetMapping(value = "/parametros")
    public ResponseEntity<?> buscarPorParametrosAlumnos(
            @RequestParam Map<String, String> parametros){
        List<Alumno> alumnos = null;
        try {
            if (!parametros.isEmpty()){
                if (parametros.containsKey("nombre") && parametros.containsKey("paterno")){
                    alumnos = repositorioAlumno.queryByNombreAndPaterno(parametros.get("nombre"), parametros.get("paterno"));
                }else {
                    if (parametros.containsKey("nombre")){
                        alumnos = repositorioAlumno.findByNombreContaining(parametros.get("nombre"));
                    }
                    if (parametros.containsKey("paterno")){
                        alumnos = repositorioAlumno.findByPaternoContaining(parametros.get("paterno"));
                    }
                    if (parametros.containsKey("estatura")){
                        alumnos = repositorioAlumno.findByEstaturaLessThanEqual(
                                Double.parseDouble(parametros.get("estatura")));
                    }
                    if (parametros.containsKey("fnac")){
                        Date fecha = new SimpleDateFormat("yyyy-MM-dd").parse(parametros.get("fnac"));
                        alumnos = repositorioAlumno.findByFnacAfter(fecha);
                    }
                    if (parametros.containsKey("estado")){
                        alumnos = repositorioAlumno.findByEstadoEstado(parametros.get("estado"));
                    }
                }
                if (alumnos.isEmpty()){
                    return ResponseEntity.noContent().build();
                    //return ResponseEntity.ok().body("No se encontraron alumnos con los parametros especificados");
                }
            }else {
                if (alumnos == null || alumnos.isEmpty()){
                    alumnos = repositorioAlumno.findAll();
                }
            }
            return ResponseEntity.ok(alumnos);
        }catch (ParseException e){
            return ResponseEntity.badRequest().body("Formato de fecha incorrecto para 'fnac'. Se espera 'yyyy-MM-dd'.");
        }catch (NumberFormatException e){
            return ResponseEntity.badRequest().body("Formato numérico incorrecto para 'estatura'. Se espera un número.");
        }
    }

    @GetMapping("parametros/{estado}")
    @ResponseBody
    public ResponseEntity<List<Alumno>> getAlumnosPorEstado(
            @PathVariable("estado") String estado
    ){
        List<Alumno> alumnos = repositorioAlumno.findByEstadoEstado(estado);
        if (alumnos.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(alumnos);
    }

}
