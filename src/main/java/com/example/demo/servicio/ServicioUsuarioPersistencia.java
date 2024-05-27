package com.example.demo.servicio;

import com.example.demo.mapper.IUsuarioMapper;
import com.example.demo.modelo.Sede;
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
    public Usuario crearUsuario(int idSede, Usuario usuario) {
        return toDto(iRepositorioUsuario.save(toEntity(idSede, usuario)));
    }

    @Override
    public Usuario buscarUsuario(int idSede, Usuario usuario) {
        Integer userId = toEntity(idSede, usuario).getId();
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
    public Usuario buscarUsuario(int idSede, int id) {
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
    public Usuario buscarUsuario(int idSede, String nombre) {
        if (nombre.equals(' ')){
            return null;
        }
        Optional<EntityUsuario> userOptional = iRepositorioUsuario.findByNombre(idSede, nombre);
        if (userOptional.isEmpty()) {
            return null;
        }
        return toDto(userOptional.get());
    }

    @Override
    public Usuario buscarUsuario(int idSede, int id, String nombre) {
        Usuario u = buscarUsuario(idSede, id);
        if (u != null) {
            if (u.getNombre().equals(nombre)) {
                return u;
            }
        }
        return null;
    }

    @Override
    public Usuario actualizarUsuario(int idSede, Usuario usuario) {
        Usuario u = buscarUsuario(idSede, usuario);
        if (u != null) {
            return crearUsuario(idSede, usuario); //Actualiza si existe.
        }
        return null;
    }

    @Override
    public Usuario eliminarUsuario(int idSede, int id) {
        Usuario usuario = buscarUsuario(idSede, id);
        iRepositorioUsuario.deleteById(id);
        return usuario;
    }

    @Override
    public List<Usuario> listarUsuarios(int idSede) {
        List<EntityUsuario> entityList = iRepositorioUsuario.findAll();
        List<Usuario> userList = entityList.stream()
                .map(this::toDto) // Convierte a DTO
                .collect(Collectors.toList()); // Recopila en una lista
        return userList;
    }

    @Override
    public List<Usuario> listarUsuarios(int idSede, String nombre) {
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
    public EntityUsuario toEntity(int idSede, Usuario usuario) {
        EntityUsuario entityUsuario = new EntityUsuario(usuario.getId(), usuario.getNombre(), usuario.getApellido(), usuario.getFechaInscripcion(), usuario.getMensualidad(), idSede, null);
        return entityUsuario;
    }
}
