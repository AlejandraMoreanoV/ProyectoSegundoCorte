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
@RequestMapping(value = "/usuario")
public class ControladorUsuario {

    @Autowired
    //private IServicioSede servicioSede;
    private IServicioUsuario servicioUsuario;

    @PostMapping (path = "/{idSede}")
    public ResponseEntity<?> crearUsuario(@PathVariable int idSede, @RequestBody Usuario usuario, BindingResult result){
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }
        if (usuario.getId()<= 0
                || usuario.getNombre()==null
                || usuario.getNombre().isEmpty()
                || usuario.getApellido()==null
                || usuario.getApellido().isEmpty()
                || usuario.getFechaInscripcion()==null
                || usuario.getMensualidad()<0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Información inválida, por favor corregir.");
        } else {
            Usuario u = servicioUsuario.crearUsuario(idSede, usuario);
            if (u == null) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "El ID que intenta ingresar ya ha sido asignado.");
            } else {
                return ResponseEntity.status(HttpStatus.CREATED).body(u);
            }
        }
    }

    @GetMapping (path = "/id/{idSede}/{idUsuario}")
    public ResponseEntity<Usuario> buscarUsuarioId(@PathVariable int idSede, @PathVariable int idUsuario) {
        try {
            Usuario u = servicioUsuario.buscarUsuario(idSede, idUsuario);
            if (u == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(u);
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(u);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping (path = "/nombre/{idSede}/{nombre}")
    public ResponseEntity<Usuario> buscarUsuarioNombre(@PathVariable int idSede, @PathVariable String nombre) {
        try {
            Usuario usuario = servicioUsuario.buscarUsuario(idSede, nombre);
            if (usuario == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(usuario);
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(usuario);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping (path = "/idNombre/{idSede}/{id}/{nombre}")
    public ResponseEntity<Usuario> buscarUsuarioIdNombre(@PathVariable int idSede, @PathVariable int id, @PathVariable String nombre) {
        try {
            Usuario u = servicioUsuario.buscarUsuario(idSede, id, nombre);
            if (u == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(u);
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(u);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping (path = "/{idSede}")
    public ResponseEntity<Usuario> actualizarUsuario (@PathVariable int idSede, @RequestBody Usuario usuario, BindingResult result) {
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }
        if (usuario.getId()<= 0
                || usuario.getNombre()==null
                || usuario.getNombre().isEmpty()
                || usuario.getApellido()==null
                || usuario.getApellido().isEmpty()
                || usuario.getFechaInscripcion()==null
                || usuario.getMensualidad()<0) {
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, "Información inválida, por favor corregir.");
        } else {
            Usuario u = servicioUsuario.actualizarUsuario(idSede, usuario);
            if (u == null) {
                return new ResponseEntity("Usuario no encontrado o no fue posible actualizarlo; revise los datos ingresados.", HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.status(HttpStatus.OK).body(u);
        }
    }

    @DeleteMapping (path = "/{idSede}/{idUsuario}")
    public ResponseEntity<Usuario> eliminarUsuario (@PathVariable int idSede, @PathVariable int idUsuario) {
        try {
            Usuario u = servicioUsuario.eliminarUsuario(idSede, idUsuario);
            if (u == null) {
                return new ResponseEntity("Usuario no encontrado.", HttpStatus.NOT_FOUND);
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(u);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping (path = "/{idSede}")
    public ResponseEntity<List<Usuario>> listarUsuarios (@PathVariable int idSede) {
        return ResponseEntity.status(HttpStatus.OK).body(servicioUsuario.listarUsuarios(idSede));
        //return new ResponseEntity<>(servicioUsuario.listarUsuarios(), HttpStatus.OK);
    }

    @GetMapping(path = "/filtrar/{idSede}/{nombre}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Usuario>> listarUsuarios (@PathVariable int idSede, @PathVariable String nombre) {
        return ResponseEntity.status(HttpStatus.OK).body(servicioUsuario.listarUsuarios(idSede, nombre));
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