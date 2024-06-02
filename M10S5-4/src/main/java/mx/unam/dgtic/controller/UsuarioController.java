package mx.unam.dgtic.controller;

import mx.unam.dgtic.model.Usuario;
import mx.unam.dgtic.repository.UsuarioRepository;
import mx.unam.dgtic.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    UsuarioRepository repositorioUsuario;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/{id}")
    public Usuario getUsuarioById(@PathVariable Long id){
        return repositorioUsuario.findById(id)
                .orElseThrow(()->new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuario no localizado con Id "+ id
                ));
    }

    @GetMapping
    public List<Usuario> getAllUsuarios(){
        return repositorioUsuario.findAll();
    }

    @PostMapping
    public Usuario createUsuario(@RequestBody Usuario usuario){
        if (usuario.getNombre() == null || usuario.getNombre().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre es requerido");
        }

        if (usuario.getEmail() == null || usuario.getEmail().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El email es requerido");
        }

        if (repositorioUsuario.findByEmail(usuario.getEmail()).isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email ya registrado");
        }
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword())); //usuario encriptado
        return repositorioUsuario.save(usuario);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario usuario){
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(usuario.getEmail(), usuario.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            List<String> roles =authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());
            String token = JwtUtil.generateToken(usuario.getEmail(), roles);
            return ResponseEntity.ok().body(Map.of("token", token));
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Error de Login");
        }
    }
}
