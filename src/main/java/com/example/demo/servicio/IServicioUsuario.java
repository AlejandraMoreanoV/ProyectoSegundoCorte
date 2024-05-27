package com.example.demo.servicio;

import com.example.demo.modelo.Sede;
import com.example.demo.modelo.Usuario;

import java.util.List;

public interface IServicioUsuario {

    public Usuario crearUsuario(int idSede, Usuario usuario);
    public Usuario buscarUsuario(int idSede, Usuario usuario);
    public Usuario buscarUsuario(int idSede, int idUsuario);
    public Usuario buscarUsuario(int idSede, String nombre);
    public Usuario buscarUsuario(int idSede, int idUsuario, String nombre);
    public Usuario actualizarUsuario(int idSede, Usuario usuario);
    public Usuario eliminarUsuario(int idSede, int idUsuario);
    public List<Usuario> listarUsuarios(int idSede);
    public List<Usuario> listarUsuarios(int idSede, String nombre);

    /**
    public Infraccion getInfraccion();
    public Infraccion setInfraccion(Infraccion infraccion);
    public Infraccion actualizarInfraccion(int numero, String tipo);
    **/

}