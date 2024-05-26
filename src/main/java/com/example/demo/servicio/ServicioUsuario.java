package com.example.demo.servicio;

import com.example.demo.modelo.Sede;
import com.example.demo.modelo.Usuario;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
//public class ServicioUsuario implements IServicioUsuario {
public class ServicioUsuario {

    //public List<Usuario> listaUsuarios = new ArrayList<>();

    @Autowired
    public IServicioSede servicioSede;

    public Sede buscarSede(int id) {
        return servicioSede.buscarSede(id);
    }

    //@Override
    public Usuario crearUsuario(int idSede, Usuario usuario) {
        Sede sede = buscarSede(idSede);
        if (sede == null) {
            return null;
        } else {
            if (usuario.getId() >= 0
                    && usuario.getNombre() != null
                    && !usuario.getNombre().isEmpty()
                    && usuario.getApellido() != null
                    && !usuario.getApellido().isEmpty()
                    && usuario.getFechaInscripcion() != null
                    && usuario.getMensualidad() > 0
                    && buscarUsuario(idSede, usuario.getId()) == null) {
                System.out.println("A");
                sede.getListaUsuarios().add(usuario);
                return usuario;
            }
        }
        return null;
    }

    //@Override
    public Usuario buscarUsuario(int idSede, Usuario usuario) {
        Sede sede = buscarSede(idSede);
        if (sede == null) {
            return null;
        } else {
            for (Usuario u : sede.getListaUsuarios()) {
                if (u.getNombre().equalsIgnoreCase(usuario.getNombre())
                        && u.getApellido().equalsIgnoreCase(usuario.getApellido())
                        && u.getFechaInscripcion() == usuario.getFechaInscripcion()
                        && u.getMensualidad() == usuario.getMensualidad()) {
                    return u;
                }
            }
            return null;
        }
    }

    //@Override
    public Usuario buscarUsuario(int idSede, int idUsuario) {
        Sede sede = buscarSede(idSede);
        if (sede == null) {
            return null;
        } else {
            //TO DO --> Mayúsculas, minúsculas y vacios.
            for (Usuario usuario : sede.getListaUsuarios()) {
                if (usuario.getId()==idUsuario) {
                    return usuario;
                }
            }
            return null;
        }
    }

    //@Override
    public Usuario buscarUsuario(int idSede, String nombre) {
        Sede sede = buscarSede(idSede);
        if (sede == null) {
            return null;
        } else {
            //TO DO --> Mayúsculas, minúsculas y vacios.
            for (Usuario usuario : sede.getListaUsuarios()) {
                if (usuario.getNombre().equalsIgnoreCase(nombre.trim())) {
                    return usuario;
                }
            }
            return null;
        }
    }

    //@Override
    public Usuario buscarUsuario(int idSede, int idUsuario, String nombre) {
        Sede sede = buscarSede(idSede);
        if (sede == null) {
            return null;
        } else {
            //TO DO --> Mayúsculas, minúsculas y vacios.
            for (Usuario usuario : sede.getListaUsuarios()) {
                if (usuario.getId() == idUsuario && usuario.getNombre().equalsIgnoreCase(nombre.trim())) {
                    return usuario;
                }
            }
            return null;
        }
    }

    //@Override
    public Usuario actualizarUsuario(int idSede, Usuario usuario) {
        Sede sede = buscarSede(idSede);
        if (sede == null) {
            return null;
        } else {
            Usuario u = buscarUsuario(idSede, usuario.getId());
            if (u == null) {
                return null;
            } else if (usuario.getId() >= 0
                    && usuario.getNombre() != null
                    && !usuario.getNombre().isEmpty()
                    && usuario.getApellido() != null
                    && !usuario.getApellido().isEmpty()
                    && usuario.getFechaInscripcion() != null
                    && usuario.getMensualidad() > 0) {
                u.setNombre(usuario.getNombre());
                u.setApellido(usuario.getApellido());
                //usuario.setFechaInscripcion(u.getFechaInscripcion());
                u.setMensualidad(usuario.getMensualidad());
                //eliminarUsuario(idSede, usuario.getId());
                //crearUsuario(idSede, usuario);
                return usuario;
            } else {
                return null;
            }
        }
    }

    //@Override
    public Usuario eliminarUsuario(int idSede, int idUsuario) {
        Sede sede = buscarSede(idSede);
        if (sede == null) {
            return null;
        } else {
            Usuario usuario = buscarUsuario(idSede, idUsuario);
            if (usuario == null) {
                return null;
            } else {
                //System.out.println("A");
                sede.getListaUsuarios().remove(usuario);
                return usuario;
            }
        }
    }

    //@Override
    public List<Usuario> listarUsuarios(int idSede) {
        Sede sede = buscarSede(idSede);
        if (sede == null) {
            return null;
        } else {
            return sede.getListaUsuarios();
        }
    }

    //@Override
    public List<Usuario> listarUsuarios(int idSede, String nombre) {
        Sede sede = buscarSede(idSede);
        if (sede == null) {
            return null;
        } else {
            List<Usuario> listaEncontrados = new ArrayList<>();
            for (Usuario usuario : sede.getListaUsuarios()) {
                if (usuario.getNombre().equalsIgnoreCase(nombre)) {
                    listaEncontrados.add(usuario);
                }
            }
            return listaEncontrados;
        }
    }

}