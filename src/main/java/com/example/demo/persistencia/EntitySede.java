package com.example.demo.persistencia;

import com.example.demo.modelo.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name= "Sede")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EntitySede {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer id;
    public String ciudad;
    public String direccion;
    @Column(name = "fecha_registro")
    //Sólo column cuando tiene nombre diferente.
    public LocalDateTime fechaRegistro;
    @NotNull
    public Double m2;
    @OneToMany(mappedBy = "sede", fetch = FetchType.LAZY)
    public List<EntityUsuario> listaUsuarios;

}