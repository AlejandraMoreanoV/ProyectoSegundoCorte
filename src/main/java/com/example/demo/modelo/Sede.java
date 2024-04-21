package com.example.demo.modelo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
public class Sede {

    @Setter @Getter
    public int id;

    @Setter @Getter
    public String ciudad;

    @Setter @Getter
    public String direccion;

    @Setter @Getter
    public LocalDateTime fechaRegistro;

    @Setter @Getter
    public double m2;

    @Setter @Getter
    private List<Usuario> listaUsuarios;

}