package com.example.demo.controlador;

import com.example.demo.modelo.Usuario;
import com.example.demo.servicio.ErrorMessage;
import com.example.demo.servicio.IServicioUsuario;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/controladorUsuario")
public class ControladorUsuario {

    @Autowired
    private IServicioUsuario servicioUsuario;

    @PostMapping
    public ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario, BindingResult result){
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }
        Usuario u = servicioUsuario.crearUsuario(usuario);
        if (u==null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El ID o el usuario ya existe.");
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(u);
        }
    }

    @GetMapping (path = "/buscarUsuarioId/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Usuario> buscarUsuario(@PathVariable int id) {
        try {
            Usuario u = servicioUsuario.buscarUsuario(id);
            if (u == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(u);
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(u);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping (path = "/buscarUsuarioNombre/{nombre}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Usuario> buscarUsuario(@PathVariable String nombre) {
        try {
            Usuario u = servicioUsuario.buscarUsuario(nombre);
            if (u == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(u);
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(u);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping (path = "/buscarUsuarioIdNombre/{id}/{nombre}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Usuario> buscarUsuario(@PathVariable int id, @PathVariable String nombre) {
        try {
            Usuario u = servicioUsuario.buscarUsuario(id, nombre);
            if (u == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(u);
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(u);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path = "/actualizarUsuario", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Usuario> actualizarUsuario (@RequestBody Usuario usuario, BindingResult result) {
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }
        Usuario u = servicioUsuario.actualizarUsuario(usuario);
        if (usuario == null) {
            return new ResponseEntity("Usuario no encontrado.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @DeleteMapping(path = "/eliminarUsuario", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Usuario> eliminarUsuario (@RequestParam int id) {
        Usuario u = servicioUsuario.eliminarUsuario(id);
        if (u == null) {
            return new ResponseEntity("Usuario no encontrado.", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.status(HttpStatus.OK).body(u);
    }

    @GetMapping(path = "/listarUsuarios", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Usuario>> listarUsuarios () {
        return ResponseEntity.status(HttpStatus.OK).body(servicioUsuario.listarUsuarios());
        //return new ResponseEntity<>(servicioUsuario.listarUsuarios(), HttpStatus.OK);
    }

    @GetMapping(path = "/listarUsuarios/{nombre}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Usuario>> listarUsuarios (@PathVariable String nombre) {
        return ResponseEntity.status(HttpStatus.OK).body(servicioUsuario.listarUsuarios(nombre));
        //return new ResponseEntity<>(servicioUsuario.listarUsuarios(), HttpStatus.OK);
    }

    private String formatMessage(BindingResult result){
        List<Map<String,String>> errores = result.getFieldErrors().stream()
                .map(err -> {
                    Map<String,String> error = new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;
                }).collect(Collectors.toList());

        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("01")
                .mensajes(errores)
                .build();

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";
        try {
            jsonString = mapper.writeValueAsString(errorMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

}