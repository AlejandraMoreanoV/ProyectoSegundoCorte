package com.example.demo.servicio;

import com.example.demo.mapper.ISedeMapper;
import com.example.demo.modelo.Sede;
import com.example.demo.modelo.Usuario;
import com.example.demo.persistencia.EntitySede;
import com.example.demo.persistencia.EntityUsuario;
import com.example.demo.persistencia.IRepositorioSede;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServicioSedePersistencia implements IServicioSede, ISedeMapper {

    private final IRepositorioSede iRepositorioSede;
    //private final IUsuarioMapper iUsuarioMapper;

    @Override
    public Sede crearSede(Sede sede) {
        return toDto(iRepositorioSede.save(toEntity(sede)));
    }

    @Override
    public Sede buscarSede(int id) {
        Integer sedeId = Integer.valueOf(id);
        if (sedeId == null) {
            return null;
        }
        Optional<EntitySede> sedeOptional = iRepositorioSede.findById(sedeId);
        if (sedeOptional.isEmpty()) {
            return null;
        }
        return toDto(sedeOptional.get());
    }

    @Override
    public Sede buscarSede(String ciudad) {
        if (ciudad.equals(' ')){
            return null;
        }
        Optional<EntitySede> sedeOptional = iRepositorioSede.findByCiudad(ciudad);
        if (sedeOptional.isEmpty()) {
            return null;
        }
        return toDto(sedeOptional.get());
    }

    @Override
    public Sede buscarSede(int id, String ciudad) {
        Sede s = buscarSede(id);
        if (s != null) {
            if (s.getCiudad().equals(ciudad)) {
                return s;
            }
        }
        return null;
    }

    @Override
    public Sede actualizarSede(Sede sede) {
        Sede s = buscarSede(sede.getId());
        if (s != null) {
            return crearSede(sede); //Actualiza si existe.
        }
        return null;
    }

    @Override
    public Sede eliminarSede(int id) {
        Sede sede = buscarSede(id);
        iRepositorioSede.deleteById(id);
        return sede;
    }

    @Override
    public List<Sede> listarSedes() {
        List<EntitySede> entityList = iRepositorioSede.findAll();
        List<Sede> sedeList = entityList.stream()
                .map(this::toDto) // Convierte a DTO
                .collect(Collectors.toList()); // Recopila en una lista
        return sedeList;
    }

    @Override
    public List<Sede> listarSedes(String ciudad) {
        List<EntitySede> entityList = iRepositorioSede.findAll();
        List<Sede> sedeList = entityList.stream()
                .filter(entity -> ciudad.equals(entity.getCiudad())) // Filtra por nombre
                .map(this::toDto) // Convierte a DTO
                .collect(Collectors.toList()); // Recopila en una lista
        return sedeList;
    }

    public List<Usuario> toListUsuario (List<EntityUsuario> listEntityUsuarios) {
        List<Usuario> listUsuarios = new ArrayList<>();
        if (listEntityUsuarios != null) {
            for (EntityUsuario entityUsuario : listEntityUsuarios) {
                Usuario u = toDtoUsuario(entityUsuario);
                listUsuarios.add(u);
            }
        }
        return listUsuarios;
    }

    public Usuario toDtoUsuario(EntityUsuario entityUsuario) {
        Usuario usuario = new Usuario(entityUsuario.getId(), entityUsuario.getNombre(), entityUsuario.getApellido(), entityUsuario.getFechaInscripcion(), entityUsuario.getMensualidad());
        return usuario;
    }

    @Override
    public Sede toDto(EntitySede entitySede) {
        Sede sede = new Sede(entitySede.getId(), entitySede.getCiudad(), entitySede.getDireccion(), entitySede.getFechaRegistro(), entitySede.getM2(), toListUsuario(entitySede.getListaUsuarios()));
        return sede;
    }

    public List<EntityUsuario> toListEntityUsuario (int idSede, List<Usuario> listUsuarios) {
        List<EntityUsuario> listEntityUsuarios = new ArrayList<>();
        if (listUsuarios != null) {
            for (Usuario usuario : listUsuarios) {
                EntityUsuario entityUsuario = toDtoEntityUsuario(idSede, usuario);
                listEntityUsuarios.add(entityUsuario);
            }
        }
        return listEntityUsuarios;
    }

    public EntityUsuario toDtoEntityUsuario(int idSede, Usuario usuario) {
        EntityUsuario entityUsuario = new EntityUsuario(usuario.getId(), usuario.getNombre(), usuario.getApellido(), usuario.getFechaInscripcion(), usuario.getMensualidad(), idSede, null);
        return entityUsuario;
    }

    @Override
    public EntitySede toEntity(Sede sede) {
        EntitySede entitySede = new EntitySede(sede.getId(), sede.getCiudad(), sede.direccion, sede.getFechaRegistro(), sede.getM2(), toListEntityUsuario(sede.getId(), sede.getListaUsuarios()));
        return entitySede;
    }
}
