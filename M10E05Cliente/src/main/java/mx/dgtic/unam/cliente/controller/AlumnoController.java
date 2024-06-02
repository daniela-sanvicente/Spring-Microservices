package mx.dgtic.unam.cliente.controller;

import mx.dgtic.unam.cliente.service.AlumnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AlumnoController {
    @Autowired
    private AlumnoService alumnoService;

    @GetMapping("/alumnos")
    public String listAlumnos(Model model){
        model.addAttribute("alumnos", alumnoService.findAllAlumnos());
        return "alumnos";
    }
}
