package mx.dgtic.unam.cliente.controller;

import mx.dgtic.unam.cliente.model.AlumnoDto;
import mx.dgtic.unam.cliente.service.AlumnoDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/alumnos/servicio")
public class AlumnoDtoController {
    @Autowired
    private AlumnoDtoService alumnoService;

    @GetMapping()
    public String listAlumnos(Model model,
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "10") int size,
                              @RequestParam(defaultValue = "asc") String sortDir,
                              @RequestParam(defaultValue = "nombre") String sort
    ){
        List<AlumnoDto> alumnos = alumnoService.findAll(page, size, sortDir, sort);
        model.addAttribute("alumnos", alumnos);
        return "alumnoservicio";
    }

    @GetMapping("/new")
    public String newAlumnoForm(Model model){
        AlumnoDto alumno = new AlumnoDto();
        model.addAttribute("alumno",alumno);
        return "alumnoform";
    }

    @GetMapping("/edit/{matricula}")
    public String editAlumnoForm(@PathVariable("matricula") String matricula,Model model ){
        AlumnoDto alumno = alumnoService.findByMatricula(matricula);
        model.addAttribute("alumno",alumno);
        return "alumnoform";
    }

    @PostMapping("/save")
    public String saveAlumno(@ModelAttribute("alumno") AlumnoDto alumno){
        alumnoService.saveOrUpdate(alumno);
        return "redirect:/alumnos/servicio";
    }

    @GetMapping("/delete/{matricula}")
    public String deleteAlumno(@PathVariable("matricula") String matricula){
        alumnoService.deleteByMatricula(matricula);
        return "redirect:/alumnos/servicio";
    }
}
