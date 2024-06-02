package mx.unam.dgtic.config;

import mx.unam.dgtic.security.CustomUserDetailsService;
import mx.unam.dgtic.security.JwtTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

//    //Permitir todos los endpoints sin autorización
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
//        http
//                .authorizeHttpRequests(authz -> authz
//                      .anyRequest().permitAll()
//                )
//                .csrf(csrf ->csrf.disable())
//                .httpBasic(withDefaults());
//        return http.build(); //nivel configuración
//    }

//    //Permitir todos los endpoint de una ruta(controlador)
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
//        http
//                .authorizeHttpRequests(authz -> authz
//                        .requestMatchers("/api/servicios/**").permitAll()
//                        .anyRequest().authenticated()
//
//                )
//                .csrf(csrf ->csrf.disable())
//                .httpBasic(withDefaults());
//        return http.build(); //nivel configuración
//    }

    //Permitir todos los endpoint de una ruta(controlador)
    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig (CustomUserDetailsService customUserDetailsService){
      this.customUserDetailsService=customUserDetailsService;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth, PasswordEncoder passwordEncoder)
        throws Exception{
        auth.userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
        throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public JwtTokenFilter jwtTokenFilter(){
        return new JwtTokenFilter();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/api/servicios/**").permitAll()
                        .requestMatchers("/api/alumnos/servicio/**").permitAll()
                        .requestMatchers("/api/alumnos/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/usuarios").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/usuarios/login").permitAll()
                        .anyRequest().authenticated() //solo permitimos el post en usuarios

                )
                .csrf(csrf ->csrf.disable())
                .httpBasic(withDefaults())
                .addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build(); //nivel configuración
    }
}
