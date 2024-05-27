package com.example.demo.mapper;

import com.example.demo.modelo.Usuario;
import com.example.demo.persistencia.EntityUsuario;

public interface IUsuarioMapper {
    Usuario toDto (EntityUsuario entityUsuario);
    EntityUsuario toEntity (int idSede, Usuario usuario);

}