package com.example.demo.servicio;

import com.example.demo.modelo.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ServicioUsuario implements IServicioUsuario {

    public List<Usuario> listaUsuarios = new ArrayList<>();

    @Override
    public Usuario crearUsuario(Usuario usuario) {
        //TO DO --> Verificar los atributos.
        if (buscarUsuario(usuario.getId()) == null && buscarUsuario(usuario) == null) {
            listaUsuarios.add(usuario);
            return usuario;
        }
        return null;
    }

    @Override
    public Usuario buscarUsuario(Usuario u) {
        for (Usuario usuario:listaUsuarios) {
            if (usuario.getId()==u.getId()
                    && usuario.getNombre().equalsIgnoreCase(u.getNombre())
                    && usuario.getApellido().equalsIgnoreCase(u.getApellido())
                    && usuario.getFechaInscripcion()==u.getFechaInscripcion()
                    && usuario.getMensualidad()==u.getMensualidad()) {
                return usuario;
            }
        }
        return null;
    }

    @Override
    public Usuario buscarUsuario(int id) {
        for (Usuario usuario:listaUsuarios) {
            if (usuario.getId()==id) {
                return usuario;
            }
        }
        return null;
    }

    @Override
    public Usuario buscarUsuario(String nombre) {
        //TO DO --> Mayúsculas, minúsculas y vacios.
        for (Usuario usuario:listaUsuarios) {
            if (usuario.getNombre().equalsIgnoreCase(nombre.trim())) {
                return usuario;
            }
        }
        return null;
    }

    @Override
    public Usuario buscarUsuario(int id, String nombre) {
        //TO DO --> Mayúsculas, minúsculas y vacios.
        for (Usuario usuario:listaUsuarios) {
            if (usuario.getId()==id && usuario.getNombre().equalsIgnoreCase(nombre.trim())) {
                return usuario;
            }
        }
        return null;
    }

    @Override
    public Usuario actualizarUsuario(Usuario u) {
        Usuario usuario = buscarUsuario(u.getId());
        if (usuario == null) {
            return null;
        } else if (!u.getNombre().trim().isEmpty()
                && !u.getApellido().trim().isEmpty()
                && u.getFechaInscripcion()!=null
                && u.getFechaInscripcion().isAfter(LocalDateTime.of(2024, 4, 11, 23, 59))
                && u.getMensualidad()>0
            ){
            usuario.setNombre(u.getNombre());
            usuario.setApellido(u.getApellido());
            usuario.setFechaInscripcion(u.getFechaInscripcion());
            usuario.setMensualidad(u.getMensualidad());
            return usuario;
        } else {
            return null;
        }
    }

    @Override
    public Usuario eliminarUsuario(int id) {
        Usuario usuario = buscarUsuario(id);
        if (usuario == null) {
            return null;
        } else {
            System.out.println("A");
            listaUsuarios.remove(buscarUsuario(id));
            return usuario;
        }
    }

    @Override
    public List<Usuario> listarUsuarios() {
        return listaUsuarios;
    }

    @Override
    public List<Usuario> listarUsuarios(String nombre) {
        List<Usuario> listaEncontrados = new ArrayList<>();
        for (Usuario usuario:listaUsuarios) {
            if (usuario.getNombre().equalsIgnoreCase(nombre)) {
                listaEncontrados.add(usuario);
            }
        }
        return listaEncontrados;
    }

}