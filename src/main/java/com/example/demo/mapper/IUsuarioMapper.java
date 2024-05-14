package com.example.demo.mapper;

import com.example.demo.modelo.Usuario;
import com.example.demo.persistencia.EntityUsuario;
//import org.mapstruct.Mapper;

//@Mapper(componentModel = "spring")
public interface IUsuarioMapper {
    Usuario toDto (EntityUsuario entityUsuario);
    EntityUsuario toEntity (Usuario usuario);
}