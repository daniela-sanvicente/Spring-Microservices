package mx.unam.dgtic.controller;

import mx.unam.dgtic.dto.AlumnoDto;
import mx.unam.dgtic.model.Alumno;
import mx.unam.dgtic.model.Estado;
import mx.unam.dgtic.response.ApiResponse;
import mx.unam.dgtic.response.Data;
import mx.unam.dgtic.response.Links;
import mx.unam.dgtic.service.AlumnoService;
import mx.unam.dgtic.service.IAlumnoService;
import mx.unam.dgtic.service.IEstadoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/alumnos/servicio")
public class AlumnoServiceController {
    @Autowired
    private IAlumnoService alumnoService;

    @Autowired
    private IEstadoService estadoService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    @ResponseBody
    public List<AlumnoDto> getAlumnos(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir,
            @RequestParam(value = "sort", defaultValue = "nombre") String sort
    ){
        List<Alumno> alumnos = alumnoService.getAlumnosList(page, size, sortDir, sort);
        return alumnos.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getAlumno(@PathVariable("id") String id){
        Optional<Alumno> optional=alumnoService.getAlumnoById(id);
        if (optional.isPresent()){
            AlumnoDto alumnoDto=convertToDto(optional.get());
            return ResponseEntity.ok(alumnoDto);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public AlumnoDto createAlumno(@RequestBody AlumnoDto alumnoDto) throws ParseException {
        Alumno alumno = alumnoService.createAlumno(convertToEntity(alumnoDto));
        return convertToDto(alumno);
    }

    @PutMapping(value ="/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateAlumno(@PathVariable("id") String id, @RequestBody AlumnoDto alumnoDto) throws ParseException {
        if(!Objects.equals(id, alumnoDto.getMatricula())){
            throw new IllegalArgumentException("La matricula no coincide");
        }
        alumnoService.updateAlumno(convertToEntity(alumnoDto));
    }

    @DeleteMapping(value ="/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAlumno(@PathVariable("id") String id){
        alumnoService.deleteAlumno(id);
    }

    @GetMapping("/estado/{estadoNombre}")
    @ResponseBody
    public ResponseEntity<List<AlumnoDto>> getAlumnosByEstado(@PathVariable("estadoNombre") String estadoNombre){
        List<Alumno> alumnos = alumnoService.findAlumnosByEstado(estadoNombre);
        if (alumnos.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        List<AlumnoDto> alumnoDto = alumnos.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(alumnoDto);
    }

    @GetMapping (value = "/jsonapi")
    @ResponseBody
    public ResponseEntity<ApiResponse> getAlumnosJsonApi(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir,
            @RequestParam(value = "sort", defaultValue = "nombre") String sort
    ){
        String baseURL= "http://localhost:8080/api/alumnos/servicio/jsonapi";
        long totalAlumnos= alumnoService.countAlumnos();
        int totalPages =(int) Math.ceil((double) totalAlumnos/size);
        List<Alumno> alumnos = alumnoService.getAlumnosList(page, size, sortDir, sort);
        List<Data> dataList = alumnos.stream()
                .map(this::convertToDto)
                .map(dto -> {
                    Data data = new Data();
                    data.setType("alumno");
                    data.setId(safeToString(dto.getMatricula()));
                    data.setAttributes(Map.of(
                            "matricula", safeToString(dto.getMatricula()),
                            "nombre", safeToString(dto.getNombre()),
                            "paterno", safeToString(dto.getPaterno()),
                            "fnac", safeToString(dto.getFnac()),
                            "estatura", safeToString(dto.getEstatura()),
                            "estado", safeToString(dto.getEstado())
                    ));
                    return data;
                })

                .collect(Collectors.toList());
        ApiResponse response= new ApiResponse();
        response.setMeta(Map.of("totalPages", totalPages, "totalItems", totalAlumnos));
        response.setData(dataList);
        response.setLinks(new Links(
                buildLink(baseURL, page, size, sortDir, sort),
                buildLink(baseURL, 0, size, sortDir, sort),
                buildLink(baseURL, totalPages - 1, size, sortDir, sort),
                page > 0 ? buildLink(baseURL, page - 1, size, sortDir, sort): null,
                page > totalPages - 1 ? buildLink(baseURL, page + 1, size, sortDir, sort): null
                ));
        return ResponseEntity.ok(response);

    }
    private String safeToString(Object obj) {
        return obj != null ? obj.toString() : "";
    }
    private String buildLink(String baseURL, int page, int size, String sortDir, String sort) {
        return String.format("%s?page=%d&size=%d&sortDir=%s&sort=%s", baseURL, page, size, sortDir, sort);
    }

    //leer convertToDto
    //escribir convertToEntity

    private AlumnoDto convertToDto(Alumno alumno){
       AlumnoDto alumnoDto=modelMapper.map(alumno, AlumnoDto.class);
       if (alumno.getEstado() != null){
           alumnoDto.setEstado(alumno.getEstado().getEstado());
       }
       if (alumno.getFnac() != null){
           DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
           String fnacStr= dateFormat.format(alumno.getFnac());
           alumnoDto.setFnac(fnacStr);
       }

       return alumnoDto;
    }

    private Alumno convertToEntity(AlumnoDto alumnoDto) throws ParseException {
        Alumno alumno =modelMapper.map(alumnoDto, Alumno.class);
        if (alumnoDto.getFnac() != null && !alumnoDto.getFnac().isEmpty()){
            DateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd");
            Date fnacDate = dateFormat.parse(alumnoDto.getFnac());
            alumno.setFnac(fnacDate);
        }
        if(alumnoDto.getEstado() != null && !alumnoDto.getEstado().isEmpty()){
            Estado estado = estadoService.findByEstado(alumnoDto.getEstado());
            alumno.setEstado(estado);
        }
        return alumno;
    }

}
