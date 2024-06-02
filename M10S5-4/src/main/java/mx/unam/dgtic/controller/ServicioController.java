package mx.unam.dgtic.controller;

import mx.unam.dgtic.dto.RespuestaParametros;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/servicios")
public class ServicioController {
    @GetMapping
    public String getSaludo(){

        return "Hola Mundo";
    }

    @GetMapping(value = "/{nombre}") //valor que regresa en nombre
    public String getSaludoNombre(@PathVariable("nombre") String nombre){

        return "Hola " + nombre;
    }

    @GetMapping(value = "salida/{parametro1}/{parametro2}",
    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Map<String,String>>>
    servicioSalida(@PathVariable("parametro1") String parametro1,
                   @PathVariable("parametro2") String parametro2){
        Map<String,String> parametros =new HashMap<>();
        parametros.put("parametro1", parametro1);
        parametros.put("parametro2", parametro2);

        Map<String, Map<String,String>> respuesta = new HashMap<>();
        respuesta.put("parametros", parametros);

        return ResponseEntity.ok(respuesta);

    }

    @GetMapping(value = "salida/clase/{parametro1}/{parametro2}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public RespuestaParametros
    servicioSalidaClase(@PathVariable("parametro1") String parametro1,
                        @PathVariable("parametro2") int parametro2){

        return new RespuestaParametros(parametro1, String.valueOf(parametro2));

    }

    @GetMapping(value = "entrada", produces = MediaType.APPLICATION_XML_VALUE)
    public RespuestaParametros
    servicioEntrada(@RequestParam("dato") String dato,
                    @RequestParam("valor") int valor){
        return new RespuestaParametros(dato, String.valueOf(valor));
    }

    @ExceptionHandler
    public String errorParametro(MethodArgumentTypeMismatchException ex){
        String nombre = ex.getName();
        String tipo = ex.getRequiredType().getSimpleName();
        Object valor = ex.getValue();
        return String.format("El parámetro '%s' es tipo '%s' y el valor '%s' no es '%s'"
                ,nombre,tipo,valor,tipo);
    }
}
