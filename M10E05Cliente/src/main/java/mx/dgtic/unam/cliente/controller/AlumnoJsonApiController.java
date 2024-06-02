package mx.dgtic.unam.cliente.controller;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/alumnos/jsonapi")
public class AlumnoJsonApiController {
    private final RestTemplate restTemplate;

    @Autowired
    public AlumnoJsonApiController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping()
    public String viewAlumnos(Model model,
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "3") int size,
                              @RequestParam(defaultValue = "asc") String sortDir,
                              @RequestParam(defaultValue = "nombre") String sort
    ){
        String url = "http://localhost:8080/api/alumnos/servicio/jsonapi?size=" + size + "&page=" + page + "&sortDir=" + sortDir + "&sort=" + sort;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        try {
            JSONObject jsonResponse = new JSONObject(response.getBody());
            JSONArray jsonData = jsonResponse.getJSONArray("data");
            List<Map<String, Object>> alumnos = new ArrayList<>();
            for (int i = 0; i < jsonData.length(); i++) {
                JSONObject jsonItem = jsonData.getJSONObject(i).getJSONObject("attributes");
                Map<String, Object> alumno = new HashMap<>();
                alumno.put("estatura", jsonItem.getString("estatura"));
                alumno.put("matricula", jsonItem.getString("matricula"));
                alumno.put("nombre", jsonItem.getString("nombre"));
                alumno.put("estado", jsonItem.getString("estado"));
                alumno.put("fnac", jsonItem.getString("fnac"));
                alumno.put("paterno", jsonItem.getString("paterno"));
                alumnos.add(alumno);
            }

            JSONObject jsonLinks = jsonResponse.getJSONObject("links");
            model.addAttribute("alumnos", alumnos);
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", jsonResponse.getJSONObject("meta").getInt("totalPages"));
            model.addAttribute("totalItems", jsonResponse.getJSONObject("meta").getInt("totalItems"));
            model.addAttribute("firstPage", jsonLinks.getString("first"));
            model.addAttribute("lastPage", jsonLinks.getString("last"));
            model.addAttribute("nextPage", jsonLinks.optString("next", "#"));
            model.addAttribute("prevPage", jsonLinks.optString("prev", "#"));
            model.addAttribute("selfPage", jsonLinks.getString("self"));

            return "alumnosjsonapi";
        } catch (JSONException ex) {
            model.addAttribute("errorMessage", "Error parsing JSON response: " + ex.getMessage());
            return "error";
        }
    }
}
