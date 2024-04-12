package com.example.demo.modelo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class Usuario {

    @Setter @Getter
    public int id;

    @Setter @Getter
    public String nombre;

    @Setter @Getter
    public String apellido;

    //@Setter @Getter
    //public LocalDateTime fechaInscripcion;

    @Setter @Getter
    public double mensualidad;

}
