package com.example.demo.persistencia;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name= "Usuario")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EntityUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer id;
    public String nombre;
    public String apellido;
    @Column(name = "fecha_inscripcion")
    //Sólo column cuando tiene nombre diferente.
    public LocalDateTime fechaInscripcion;
    @NotNull
    public Double mensualidad;

}