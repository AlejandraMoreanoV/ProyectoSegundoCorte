package com.example.demo.controlador;

import com.example.demo.modelo.Sede;
import com.example.demo.servicio.ErrorMessage;
import com.example.demo.servicio.IServicioSede;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/sede")
public class ControladorSede {

    @Autowired
    public IServicioSede servicioSede;

    @PostMapping
    public ResponseEntity<?> crearSede (@RequestBody Sede sede, BindingResult result){
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }
        if (sede.getId()<= 0
                || sede.getDireccion()==null
                || sede.getDireccion().isEmpty()
                || sede.getCiudad()==null
                || sede.getCiudad().isEmpty()
                || sede.getFechaRegistro()==null
                || sede.getM2()<0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Información inválida, por favor corregir.");
        } else {
            Sede s = servicioSede.crearSede(sede);
            if (s==null) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "El ID que intenta ingresar ya ha sido asignado.");
                //return ResponseEntity.status(HttpStatus.CONFLICT).body("El ID o el usuario ya existe.");
            } else {
                return ResponseEntity.status(HttpStatus.CREATED).body(s);
            }
        }
    }

    @GetMapping (path = "/id/{id}")
    public ResponseEntity<Sede> buscarSede (@PathVariable int id) {
        try {
            Sede s = servicioSede.buscarSede(id);
            if (s == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(s);
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(s);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping (path = "/ciudad/{ciudad}")
    public ResponseEntity<Sede> buscarSede (@PathVariable String ciudad) {
        try {
            Sede s = servicioSede.buscarSede(ciudad);
            if (s == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(s);
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(s);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping (path = "/idCiudad/{id}/{ciudad}")
    public ResponseEntity<Sede> buscarSede (@PathVariable int id, @PathVariable String ciudad) {
        try {
            Sede s = servicioSede.buscarSede(id, ciudad);
            if (s == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(s);
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(s);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<Sede> actualizarSede (@RequestBody Sede sede, BindingResult result) {
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }
        if (sede.getId()<= 0
                || sede.getDireccion()==null
                || sede.getDireccion().isEmpty()
                || sede.getCiudad()==null
                || sede.getCiudad().isEmpty()
                || sede.getFechaRegistro()==null
                || sede.getM2()<0) {
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, "Información inválida, por favor corregir.");
        } else {
            Sede u = servicioSede.actualizarSede(sede);
            if (u == null) {
                return new ResponseEntity("Sede no encontrada o no fue posible actualizarla; revise los datos ingresados.", HttpStatus.NOT_FOUND);
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(u);
            }

        }
    }

    @DeleteMapping (path = "/{id}")
    public ResponseEntity<Sede> eliminarSede (@PathVariable int id) {
        Sede u = servicioSede.eliminarSede(id);
        if (u == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(u);
            //return new ResponseEntity("Sede no encontrada.", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.status(HttpStatus.OK).body(u);
    }

    @GetMapping
    public ResponseEntity<List<Sede>> listarSedes () {
        return ResponseEntity.status(HttpStatus.OK).body(servicioSede.listarSedes());
    }

    @GetMapping (path = "/filtrar/{ciudad}")
    public ResponseEntity<List<Sede>> listarSedes (@PathVariable String ciudad) {
        return ResponseEntity.status(HttpStatus.OK).body(servicioSede.listarSedes(ciudad));
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