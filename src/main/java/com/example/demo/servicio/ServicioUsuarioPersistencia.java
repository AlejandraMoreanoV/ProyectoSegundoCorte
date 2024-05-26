package com.example.demo.servicio;

import com.example.demo.mapper.IUsuarioMapper;
import com.example.demo.modelo.Usuario;
import com.example.demo.persistencia.EntityUsuario;
import com.example.demo.persistencia.IRepositorioUsuario;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServicioUsuarioPersistencia implements IServicioUsuario, IUsuarioMapper {

    private final IRepositorioUsuario iRepositorioUsuario;
    //private final IUsuarioMapper iUsuarioMapper;

    @Override
    public Usuario crearUsuario(Usuario usuario) {
        return toDto(iRepositorioUsuario.save(toEntity(usuario)));
    }

    @Override
    public Usuario buscarUsuario(Usuario usuario) {
        Integer userId = toEntity(usuario).getId();
        if (userId == null) {
            return null;
        }
        Optional<EntityUsuario> userOptional = iRepositorioUsuario.findById(userId);
        if (userOptional.isEmpty()) {
            return null;
        }
        return toDto(userOptional.get());
    }

    @Override
    public Usuario buscarUsuario(int id) {
        Integer userId = Integer.valueOf(id);
        if (userId == null) {
            return null;
        }
        Optional<EntityUsuario> userOptional = iRepositorioUsuario.findById(userId);
        if (userOptional.isEmpty()) {
            return null;
        }
        return toDto(userOptional.get());
    }

    @Override
    public Usuario buscarUsuario(String nombre) {
        if (nombre.equals(' ')){
            return null;
        }
        Optional<EntityUsuario> userOptional = iRepositorioUsuario.findByNombre(nombre);
        if (userOptional.isEmpty()) {
            return null;
        }
        return toDto(userOptional.get());
    }

    @Override
    public Usuario buscarUsuario(int id, String nombre) {
        Usuario u = buscarUsuario(id);
        if (u != null) {
            if (u.getNombre().equals(nombre)) {
                return u;
            }
        }
        return null;
    }

    @Override
    public Usuario actualizarUsuario(Usuario usuario) {
        Usuario u = buscarUsuario(usuario);
        if (u != null) {
            return crearUsuario(usuario); //Actualiza si existe.
        }
        return null;
    }

    @Override
    public Usuario eliminarUsuario(int id) {
        Usuario usuario = buscarUsuario(id);
        iRepositorioUsuario.deleteById(id);
        return usuario;
    }

    @Override
    public List<Usuario> listarUsuarios() {
        List<EntityUsuario> entityList = iRepositorioUsuario.findAll();
        List<Usuario> userList = entityList.stream()
                .map(this::toDto) // Convierte a DTO
                .collect(Collectors.toList()); // Recopila en una lista
        return userList;
    }

    @Override
    public List<Usuario> listarUsuarios(String nombre) {
        List<EntityUsuario> entityList = iRepositorioUsuario.findAll();
        List<Usuario> userList = entityList.stream()
                .filter(entity -> nombre.equals(entity.getNombre())) // Filtra por nombre
                .map(this::toDto) // Convierte a DTO
                .collect(Collectors.toList()); // Recopila en una lista
        return userList;
    }

    @Override
    public Usuario toDto(EntityUsuario entityUsuario) {
        Usuario usuario = new Usuario(entityUsuario.getId(), entityUsuario.getNombre(), entityUsuario.getApellido(), entityUsuario.getFechaInscripcion(), entityUsuario.getMensualidad());
        return usuario;
    }

    @Override
    public EntityUsuario toEntity(Usuario usuario) {
        EntityUsuario entityUsuario = new EntityUsuario(usuario.getId(), usuario.getNombre(), usuario.getApellido(), usuario.getFechaInscripcion(), usuario.getMensualidad());
        return entityUsuario;
    }
}
